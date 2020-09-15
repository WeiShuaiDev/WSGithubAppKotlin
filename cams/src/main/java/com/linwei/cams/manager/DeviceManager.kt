package com.linwei.cams.manager

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.text.format.Formatter
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.linwei.cams.ext.ctx
import com.linwei.cams.ext.pref
import com.linwei.cams.ext.string
import java.io.File
import java.io.FileFilter
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/11
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 获取手机手机设备信息，获取参数：设备Id[getDeviceId]、设备ICCID[getSim]、设备品牌[getBrands]、手机型号[getMobil]、
 * CPU型号[getCpuModel]、系统版本[getSystemVersion]、屏幕分辨率[getResolution]、wifi名称[getWifiName]、wifi mac地址[getWifiMac]
 * wifi信号强度[getWifiState]、蓝牙标识[getBluetoothAddress]、电池电量[getBatteryLevel]、系统时间[getCurrentTime]、RAM[getRAMInfo]
 * ROM[getRomInfo]、CPU核数[getCpuCores]、是否开启Root[isSuEnableRoot]
 *-----------------------------------------------------------------------
 */
@SuppressLint("HardwareIds")
class DeviceManager() {
    private var mTelephonyManager: TelephonyManager? = null
    private var mUUID: UUID? = null
    private var mIMEI: String? = ""

    /**
     * 设备Id信息
     */
    private var deviceIdStorage: String by pref("")

    /**
     * 设备 IMEI 信息
     */
    private var deviceIMEIStorage: String by pref("")

    /**
     * 设备 ICCID 信息
     */
    private var deviceICCIDStorage: String by pref("")

    companion object {

        private var INSTANCE: DeviceManager? = null

        @JvmStatic
        fun getInstance(): DeviceManager {
            return INSTANCE
                ?: DeviceManager().apply {
                    INSTANCE = this
                }
        }
    }

    init {
        mTelephonyManager =
            ctx.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager?
    }

    /**
     *获取UUID
     * @retrun [UUID]
     */
    @Suppress("DEPRECATION")
    @SuppressLint("ObsoleteSdkInt")
    fun getUUID(): UUID? {
        val id: String? = deviceIdStorage
        if (!id.isNullOrEmpty()) {
            mUUID = UUID.fromString(id)
        } else {
            val androidId =
                Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID)
            var tmDevice: String? = ""
            var tmSerial: String? = ""
            try {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    if (checkPermission(ctx, permission.READ_PHONE_STATE)) {
                        tmDevice = mTelephonyManager?.deviceId
                        tmSerial = mTelephonyManager?.simSerialNumber
                    }
                }

                if (TextUtils.isEmpty(tmDevice) || tmDevice == "unknown"
                    || TextUtils.isEmpty(tmSerial) || tmDevice == "000000000000000"
                ) {
                    if (!TextUtils.isEmpty(androidId) && "9774d56d682e549c" != androidId) {
                        mUUID = UUID.nameUUIDFromBytes(androidId.toByteArray())
                    } else {
                        mUUID = UUID.randomUUID()
                    }
                } else {
                    mUUID = UUID(
                        androidId.hashCode().toLong(),
                        tmDevice.hashCode().toLong() shl 32 or tmSerial.hashCode().toLong()
                    )
                }
                mUUID?.let {
                    deviceIdStorage = it.string()
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }

        return mUUID
    }


    /**
     *获取IMEI
     * @return [String]
     */
    @Suppress("DEPRECATION")
    fun getDeviceId(): String {
        mIMEI = deviceIMEIStorage
        if (mIMEI?.isNotEmpty() == true) {
            return mIMEI ?: ""
        }

        mIMEI = getUUID().toString()
        mIMEI?.let {
            deviceIMEIStorage = it
        }

        return mIMEI ?: ""
    }

    /**
     * 获取设备ICCID(sim)
     * @return [String]
     */
    fun getSim(): String? {
        var iccId: String? = deviceICCIDStorage
        if (iccId?.isNotEmpty() == true) {
            return iccId
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (checkPermission(ctx, permission.READ_PHONE_STATE)) {
                iccId = mTelephonyManager?.simSerialNumber
            }
        } else {
            iccId = getUUID().toString()
        }

        iccId?.let {
            deviceICCIDStorage = it
        }
        return iccId
    }

    /**
     * 获取设备品牌
     * @return [String]
     */
    fun getBrands(): String {
        return android.os.Build.BRAND
    }

    /**
     * 获取手机型号
     * @return [String]
     */
    fun getMobil(): String {
        return android.os.Build.MODEL
    }

    /**
     * CPU型号
     * @return [String]
     */
    @Suppress("DEPRECATION")
    fun getCpuModel(): String {
        return Build.CPU_ABI
    }


    /**
     * 获取当前手机系统版本号
     * @return [String]
     */
    fun getSystemVersion(): String {
        return android.os.Build.VERSION.RELEASE
    }

    /**
     * 屏幕分辨率
     * @return [String]
     */
    fun getResolution(): String {
        val metric = DisplayMetrics()
        val windowManager: WindowManager =
            ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowManager.defaultDisplay.getRealMetrics(metric)
        val width: Int = metric.widthPixels
        val height: Int = metric.heightPixels;
        val densityDpi: Int = metric.densityDpi;
        return "$width×$height -$densityDpi"
    }

    /**
     * 获取wifi名称
     * @return [String]
     */
    @SuppressLint("WifiManagerPotentialLeak")
    @Deprecated("WifiName")
    fun getWifiName(): String {
        val wifiMgr: WifiManager = ctx.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wifiMgr.connectionInfo

        val networkId: Int = info.networkId
        val configuredNetworks: MutableList<WifiConfiguration> = wifiMgr.configuredNetworks
        var ssid = ""
        run breaking@{
            configuredNetworks.forEach {
                if (it.networkId == networkId) {
                    ssid = it.SSID
                    return@breaking
                }
            }
        }
        if (ssid.contains("\"")) {
            ssid = ssid.replace("\"", "")
        }
        return ssid
    }

    /**
     * 获取wifi名称
     * @return [String]
     */
    @Suppress("DEPRECATION")
    fun getNewWifiName(): String {
        val ssid = "unknown id"

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val wifiMgr: WifiManager =
                ctx.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info: WifiInfo = wifiMgr.connectionInfo
            return info.ssid.replace("\"", "")
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) {
            val connManager: ConnectivityManager = ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo: NetworkInfo? = connManager.activeNetworkInfo
            if (networkInfo?.isConnected == true) {
                if (networkInfo.extraInfo != null) {
                    return networkInfo.extraInfo.replace("\"", "")
                }
            }
        }
        return ssid
    }

    /**
     * 获取wifi mac 地址
     * @return [String]
     */
    fun getWifiMac(): String {
        val networkInterfaces: Enumeration<NetworkInterface> =
            NetworkInterface.getNetworkInterfaces()
        while (networkInterfaces.hasMoreElements()) {
            val element: NetworkInterface? = networkInterfaces.nextElement()
            val address: ByteArray = element?.hardwareAddress ?: continue
            if (element.name == "wlan0") {
                val builder = StringBuilder()
                address.forEach {
                    builder.append(String.format("%02X:", it))
                }
                if (builder.isNotEmpty()) {
                    builder.deleteCharAt(builder.length - 1)
                }
                return builder.toString()
            }
        }
        return ""
    }

    /**
     * 获取wifi信号强度 state
     * @return [String]
     */
    @SuppressLint("WifiManagerPotentialLeak")
    fun getWifiState(): String {
        val wifiMgr: WifiManager = ctx.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info: WifiInfo = wifiMgr.connectionInfo
        if (info.ssid != null) {
            val strength: Int = WifiManager.calculateSignalLevel(info.rssi, 5)
            return strength.toString()
        }
        return ""
    }

    /**
     * 获取蓝牙标识
     * @return [String]
     */
    fun getBluetoothAddress(): String {
        val bluetooth: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return bluetooth.address
    }

    /**
     * 获取电量
     * @return [Int] 电量
     */
    fun getBatteryLevel(): Int? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager: BatteryManager? =
                ctx.getSystemService(BATTERY_SERVICE) as BatteryManager
            return batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } else {
            val intent: Intent? =
                ContextWrapper(ctx).registerReceiver(
                    null,
                    IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                )
            return intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)!! * 100 /
                    intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        }
    }

    /**
     * 获取当前时间
     * @param format [String] 格式数据
     * @return [String]
     */
    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return SimpleDateFormat(format).format(GregorianCalendar().time)
    }

    /**
     * 获取RAM
     * @return [String]
     */
    fun getRAMInfo(): String {
        val manager: ActivityManager =
            ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo: ActivityManager.MemoryInfo = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(memoryInfo)
        val totalSize: Long = memoryInfo.totalMem
        val availableSize: Long = memoryInfo.availMem
        return (Formatter.formatFileSize(ctx, availableSize) + "/"
                + Formatter.formatFileSize(ctx, totalSize))
    }


    /**
     * 获取Rom
     * @return [String]
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Suppress("DEPRECATION")
    fun getRomInfo(): String {
        val file: File = Environment.getExternalStorageDirectory()
        val statFs = StatFs(file.path)
        val blockSizeLong = statFs.blockSizeLong
        val blockCountLong = statFs.blockCountLong
        val availableBlocksLong = statFs.availableBlocksLong
        return Formatter.formatFileSize(ctx, availableBlocksLong * blockSizeLong) + "/" +
                Formatter.formatFileSize(ctx, blockCountLong * blockSizeLong)
    }

    /**
     * CPU核数
     * @return [String]
     */
    fun getCpuCores(): String {
        val count = getNumberOfCPUCores()
        return count.toString() + ""
    }

    private fun getNumberOfCPUCores(): Int {
        return try {
            File("/sys/devices/system/cpu/").listFiles(CPU_FILTER)!!.size
        } catch (e: SecurityException) {
            0
        }
    }

    private val CPU_FILTER = FileFilter { pathname ->
        val path = pathname.name
        if (path.startsWith("cpu")) {
            for (i in 3 until path.length) {
                if (path[i] < '0' || path[i] > '9') {
                    return@FileFilter false
                }
            }
            return@FileFilter true
        }
        false
    }

    /**
     * 是否root或是否实体
     * 1是root机和虚拟机
     * 0是非root机和实体机
     * @return [Int]
     */
    fun isSuEnableRoot(): Int {
        var file: File?
        val paths: Array<String> = arrayOf(
            "/system/bin/",
            "/system/xbin/",
            "/system/sbin/",
            "/sbin/",
            "/vendor/bin/",
            "/su/bin/"
        )
        try {
            for (path: String in paths) {
                file = File(path + "su")
                if (file.exists() && file.canExecute()) {
                    return 1
                }
            }
        } catch (x: Exception) {
            x.printStackTrace()
        }
        return 0
    }

    var virtualMachine: Int? = null;
    fun checkDeviceInfo(): Boolean {
        var result: Boolean = (Build.FINGERPRINT.startsWith("generic") // 唯一识别码
                || Build.MODEL.contains("google_sdk") // 版本 用户最终可以见的名称
                || Build.MODEL.toLowerCase(Locale.ROOT).contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion") // 硬件制造商
                || Build.HARDWARE == "goldfish" || Build.HARDWARE == "vbox86" || Build.PRODUCT == "sdk" || Build.PRODUCT == "google_sdk" || Build.PRODUCT == "sdk_x86" || Build.PRODUCT == "vbox86p" || Build.BOARD.toLowerCase()
            .contains("nox") // 主板
                || Build.BOOTLOADER.toLowerCase(Locale.ROOT).contains("nox") // 系统启动程序版本号
                || Build.HARDWARE.toLowerCase(Locale.ROOT).contains("nox")
                || Build.PRODUCT.toLowerCase(Locale.ROOT).contains("nox")
                || Build.SERIAL.toLowerCase(Locale.ROOT).contains("nox")) // 硬件序列号
        if (result) return true
        result =
            result or (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith(
                "generic"
            ))
        if (result) return true
        result = result or ("google_sdk" == Build.PRODUCT)
        return result
    }

    /**
     * 检查权限
     * @param context [Context]
     * @param permName [permission]
     */
    private fun checkPermission(context: Context, permName: permission): Boolean {
        val perm: Int = context.checkCallingOrSelfPermission("android.permission.$permName")
        return perm == PackageManager.PERMISSION_GRANTED
    }

    private enum class permission {
        READ_PHONE_STATE,
        WRITE_EXTERNAL_STORAGE
    }
}