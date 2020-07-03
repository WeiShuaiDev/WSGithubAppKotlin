package com.linwei.cams.ext

import android.app.ActivityManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.linwei.cams.R
import com.linwei.cams.base.BaseApplication
import com.linwei.cams.utils.ToastUtils
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.Base64
import com.linwei.cams.base.App
import com.linwei.cams.di.component.AppComponent
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 提供 AppCompat 扩展方法
 *-----------------------------------------------------------------------
 */
val ctx: Context = BaseApplication.mContext

/**
 * 获取 [AppComponent]
 */
fun obtainAppComponent(): AppComponent {
    return (ctx as App).getAppComponent()
}

/**
 * 获取 [Application]
 */
fun obtainApplication(): Application {
    return (ctx as App).getApplication()
}

/**
 *短时间 [Toast]
 */
fun Any.showShort(vararg args: String) {
    toastBuild().showShort(this.string(), args)
}

/**
 * 长时间 [Toast]
 */
fun Any.showLong(vararg args: String) {
    toastBuild().showLong(this.string(), args)
}

/**
 * 安全模式短时间 [Toast]
 */
fun Any.showShortSafe(vararg args: String) {
    toastBuild().showShortSafe(this.string(), args)
}

/**
 * 安全模式长时间 [Toast]
 */
fun Any.showLongSafe(vararg args: String) {
    toastBuild().showLongSafe(this.string(), args)
}

fun Int.color(): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        ctx.resources.getColor(this, null)
    } else {
        ctx.resources.getColor(this)
    }
}

/**
 * [ResId] 转换为字符串 [String]
 * @param [ResId]
 * @return [String]
 */
fun Any.string(): String {
    return if (this is Int) {
        ctx.resources.getString(this)
    } else {
        this.toString()
    }
}

/**
 * [ResId] 转换为字符串 [String]
 * @param [ResId] 字符串Id, [args] 字符串格式化参数
 * @return [String]
 */
fun Any.string(vararg args: String): String {
    return if (this is Int) {
        ctx.resources.getString(this, *args)
    } else {
        this.toString()
    }
}


/**
 * 获取ToastUtils
 */
fun toastBuild(): ToastUtils {
    return ToastUtils.Builder(ctx)
        .setBgResource(R.drawable.shape_toast_background)
        .setMessageColor(R.color.colorGlobalBlack)
        .build()
}


/**
 * 判断是否为空
 */
fun isEmptyParameter(vararg params: String?): Boolean {
    for (p in params)
        if (p.isNullOrEmpty() || p == "null" || p == "NULL") {
            return true
        }
    return false
}

/**
 * 序列化对象
 */
fun Any.fromBean(): String {
    try {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(byteArrayOutputStream)
        objectOutputStream.writeObject(this)
        return base64(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 反序列化对象
 */
fun String.toBean(): Any? {
    try {
        val bytes = Base64.decode(this, Base64.NO_WRAP)
        return ObjectInputStream(ByteArrayInputStream(bytes)).readObject()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/**
 * 转换为Base64
 */
fun base64(bytes: ByteArray, flag: Int): String {
    if (bytes.isNotEmpty()) {
        return Base64.encodeToString(bytes, flag)
    } else {
        return ""
    }
}

/**
 * 时间格式化
 */
@SuppressLint("SimpleDateFormat")
fun getCurrentDate(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    return SimpleDateFormat(format).format(GregorianCalendar().time)
}

/**
 * 时间格式化
 */
@SuppressLint("SimpleDateFormat")
fun formatTime(date: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String {
    return SimpleDateFormat(format).format(date).toString()
}

/**
 * 毫秒时间格式化
 */
fun getTime(date: Long): String {
    if (date < 10) {
        return "00:0$date"
    }
    if (date < 60) {
        return "00:$date"
    }
    if (date < 3600) {
        val minute = date / 60
        val second = date - minute * 60
        if (minute < 10) {
            return if (second < 10) {
                "0$minute:0$second"
            } else "0$minute:$second"
        }
        return if (second < 10) {
            "$minute:0$second"
        } else "$minute:$second"
    }

    val hour = date / 3600
    val minute = (date - hour * 3600) / 60
    val second = date - hour * 3600 - minute * 60
    if (hour < 10) {
        if (minute < 10) {
            return if (second < 10) {
                "0$hour:0$minute:0$second"
            } else "0$hour:0$minute:$second"
        }
        return if (second < 10) {
            "0$hour$minute:0$second"
        } else "0$hour$minute:$second"
    }
    if (minute < 10) {
        return if (second < 10) {
            "$hour:0$minute:0$second"
        } else "$hour:0$minute:$second"
    }

    return if (second < 10) {
        (hour + minute).toString() + ":0" + second
    } else (hour + minute).toString() + ":" + second
}


/**
 * 获取运算后内存大小
 * {@code maxSize}最大内存大小，{@ multiplierSize}内存运算阈值
 */
fun Context.getCalculateCacheSize(maxSize: Int, multiplierSize: Float): Int {
    val activityManager: ActivityManager =
        getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val targetMemoryCacheSize: Int = (activityManager.memoryClass * multiplierSize * 1024).toInt()
    if (targetMemoryCacheSize > maxSize) {
        return maxSize
    }
    return targetMemoryCacheSize
}

/**
 * {@code frameId} 容器中Fragment,替换为 {@code fragment},并设置标识为tag
 */
fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment,
    @IdRes frameId: Int,
    tag: String
) {
    supportFragmentManager.transact {
        replace(frameId, fragment, tag)
    }
}

/**
 * 设置 {@link FragmentManager} 切换动画
 */
@SuppressLint("PrivateResource")
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        setCustomAnimations(
            R.anim.abc_grow_fade_in_from_bottom,
            R.anim.abc_fade_out,
            R.anim.abc_fade_in,
            R.anim.abc_shrink_fade_out_from_bottom
        )
        action()
    }.commit()
}

