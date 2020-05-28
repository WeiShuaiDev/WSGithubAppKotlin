package com.linwei.frame.utils

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


    fun createDateInfo(second: Int): String {
        var currentTime: String = System.currentTimeMillis().toString()
        while (currentTime.length < 13) {
            currentTime = "0${currentTime}"
        }
        return "${currentTime}-${second}${mSeparator}"
    }


}