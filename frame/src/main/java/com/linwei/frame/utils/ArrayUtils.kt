package com.linwei.frame.utils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/27
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: ArrayUtils工具类
 *-----------------------------------------------------------------------
 */
object ArrayUtils {

    /**
     *
     * @param ByteArray
     * @param from
     * @param to
     */
    fun copyOfRange(original: ByteArray, from: Int, to: Int): ByteArray {
        val newLength: Int = to - from
        if (newLength < 0) throw IllegalArgumentException("$from > $to")
        val copy: ByteArray = byteArrayOf()
        System.arraycopy(original, from, copy, 0, Math.min(original.size - from, newLength))
        return copy
    }


    fun indexOf(original: ByteArray, char: Char): Int {
        return 0
    }


}