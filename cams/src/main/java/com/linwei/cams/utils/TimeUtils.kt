package com.linwei.cams.utils

import android.annotation.SuppressLint
import com.linwei.cams.R
import com.linwei.cams.ext.string
import java.text.SimpleDateFormat
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/27
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: TimeUtils工具类
 *-----------------------------------------------------------------------
 */
object TimeUtils {

    private const val mSeparator: Char = ' '

    /**
     * 根据 {@code second} 有效期时间，{@code strInfo} 字符串数据源，生成有效期时间字符串数据包。
     * @param second 有效期时间
     * @param strInfo 字符串数据源
     * @return 有效期时间字符串数据包
     */
    fun newStringWithDateInfo(second: Int = 0, strInfo: String): String {
        return createDateInfo(second) + strInfo
    }


    /**
     *  根据 {@code second} 有效期时间，{@code strInfo} 字符串数组，生成有效期时间字节数组数据包。
     * @param second 有效期时间
     * @param dataArray 字节数组数据源
     * @return 生成有效期时间字节数组数据包
     */
    fun newStringWithDateInfo(second: Int = 0, dataArray: ByteArray): ByteArray {
        val tempArray: ByteArray = createDateInfo(second).toByteArray()

        val dateArray = ByteArray(dataArray.size + tempArray.size)
        ArrayUtils.arrayCopyOfRange(tempArray, 0, dateArray, 0, tempArray.size)
        ArrayUtils.arrayCopyOfRange(dataArray, 0, dataArray, tempArray.size, dataArray.size)
        return dateArray
    }


    /**
     * 判断 {@code byteArray}字节数据数据包有效期, 如果数据包有效，则解析{@code saveDate} 保存数据时间。 {@code deleteAfter} 有效期时间
     * 通过字符串数组返回
     * @param byteArray 字节数组数据源
     * @return 生成保存时间、有效期时间字符串数组
     */
    private fun getDateInfoFromDate(byteArray: ByteArray?): Array<String>? {
        if (hasDateInfo(byteArray)) {
            val saveDate: String = ArrayUtils.copyOfRange(byteArray!!, 0, 13).toString()
            val deleteAfter: String =
                ArrayUtils.copyOfRange(byteArray, 14, ArrayUtils.indexOf(byteArray, mSeparator))
                    .toString()
            return arrayOf(saveDate, deleteAfter)
        }
        return null
    }

    /**
     * 判断 {@code byteArray}字节数据数据包有效期,如果数据包有效，则解析出数据源字节数组。
     * @param byteArray 字节数组数据包
     * @return 生成字节数组数据源
     */
    fun clearDateInfo(byteArray: ByteArray?): ByteArray? {
        if (hasDateInfo(byteArray)) {
            return ArrayUtils.copyOfRange(
                byteArray!!,
                ArrayUtils.indexOf(byteArray, mSeparator) + 1,
                byteArray.size
            )
        }
        return byteArray
    }

    /**
     * 判断 {@code strInfo}字符串数据数据包有效期，如果数据包有效，则解析出数据源字符串。
     * @param strInfo 字符串类型数据包
     * @return 生成字符串数据源
     */
    fun clearDateInfo(strInfo: String?): String? {
        if (hasDateInfo(strInfo?.toByteArray())) {
            return strInfo?.substring(strInfo.indexOf(mSeparator) + 1, strInfo.length)
        }
        return strInfo
    }

    /**
     *  判断 {@code strInfo} 字符串数据包是否过期
     * @param strInfo 字符串类型数据包
     * @return true:无效数据包  false:有效数据包
     */
    fun isDue(strInfo: String?): Boolean = isDue(strInfo?.toByteArray())

    /**
     *  判断 {@code byteArray} 字节数组类型数据包是否过期，通过保存时间 {@code saveTime} ，有效期 {@code deleteAfter}
     *  跟当前时间进行比较，确定数据有效性。
     * @param byteArray 字节数组类型数据包
     * @return true:无效数据包 false: 有效数据包
     */
    fun isDue(byteArray: ByteArray?): Boolean {
        val dateArray: Array<String>? = getDateInfoFromDate(byteArray)
        if (dateArray?.size == 2) {
            var saveTimeStr: String = dateArray[0]
            while (saveTimeStr.startsWith("0")) {
                saveTimeStr = saveTimeStr.substring(1, saveTimeStr.length)
            }
            val saveTime: Long = saveTimeStr.toLong()
            val deleteAfter: Long = dateArray[1].toLong()
            if (System.currentTimeMillis() > saveTime + deleteAfter * 1000) {
                return true
            }
        }
        return false
    }

    /**
     * 判断是否合法有效期数据，校验规则 {@code byteArray} 长度必须大于15， {@code byteArray} 数组角标13，
     * 是否以 {@code '-'} 区别有效期数据，{@code mSeparator} 分隔内容数据角标必须大于14，因为 {@code byteArray}
     * 字节数组角标0-14，存储数据有效期。字节数组角标>14,保存内容数据。
     * @param byteArray 字节数组
     * @return true:格式正确 false:格式错误
     */
    fun hasDateInfo(byteArray: ByteArray?): Boolean {
        return byteArray != null && byteArray.size > 15 && byteArray[13].toChar() == '-'
                && ArrayUtils.indexOf(byteArray, mSeparator) > 14
    }


    /**
     * 增加数据有效期时间 {@code second},获取当前12位毫秒时间，拼接 {@code second} 秒，作为数据有效期时间。
     * 并通过 {@link mSeparator}作为数据、有效期时间分隔符。
     * 格式：当前时间(毫秒单位)-有效期时间(秒单位)分隔符
     * @param second 数据保质期时间
     */
    private fun createDateInfo(second: Int = 0): String {
        var currentTime: String = System.currentTimeMillis().toString()
        while (currentTime.length < 13) {
            currentTime = "0${currentTime}"
        }
        return "${currentTime}-${second}${mSeparator}"
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
     * 时间格式化
     */
    fun getDate(date: Date?): String {
        if (date?.toString() == null) {
            return ""
        } else if (date.toString().length < 10) {
            return date.toString()
        }
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date)
            .substring(0, 10)
    }

    private const val MILLIS_LIMIT = 1000.0

    private const val SECONDS_LIMIT = 60 * MILLIS_LIMIT

    private const val MINUTES_LIMIT = 60 * SECONDS_LIMIT

    private const val HOURS_LIMIT = 24 * MINUTES_LIMIT

    private const val DAYS_LIMIT = 30 * HOURS_LIMIT

    /**
     * 获取时间格式化
     */
    fun getNewsTime(date: Date?): String {
        if (date == null) {
            return ""
        }
        val subTime: Long = Date().time - date.time
        return when {
            subTime < MILLIS_LIMIT -> R.string.justNow.string()
            subTime < SECONDS_LIMIT -> Math.round(subTime / MILLIS_LIMIT)
                .toString() + " " + R.string.secondAgo.string()
            subTime < MINUTES_LIMIT -> Math.round(subTime / SECONDS_LIMIT)
                .toString() + " " + R.string.minutesAgo.string()
            subTime < HOURS_LIMIT -> Math.round(subTime / MINUTES_LIMIT)
                .toString() + " " + R.string.hoursAgo.string()
            subTime < DAYS_LIMIT -> Math.round(subTime / HOURS_LIMIT)
                .toString() + " " + R.string.daysAgo.string()
            else -> getDate(date)
        }
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


}