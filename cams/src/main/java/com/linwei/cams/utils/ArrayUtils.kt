package com.linwei.cams.utils

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
     * {@code original} 数组内容Copy, {@code from}起始位置,{@code to}结束位置。
     * 当 {@code original}数组Copy的长度 {@newLength} 小于0，程序会报一个 {@link IllegalArgumentException}异常
     * @param original 字节数组数据
     * @param from  Copy起始位置
     * @param to Copy结束位置
     */
    fun copyOfRange(original: ByteArray, from: Int, to: Int): ByteArray {
        val newLength: Int = to - from
        if (newLength < 0) throw IllegalArgumentException("$from > $to")
        val copy: ByteArray = byteArrayOf()
        System.arraycopy(original, from, copy, 0, Math.min(original.size - from, newLength))
        return copy
    }

    /**
     *  数据源字节数组 {@code src}从 {@srcPos}起始位置，copy 到目标字节数组 {@code dest} 起始位置
     *  {@code destPost} 开始,copy 数据源长度为 {@code length}
     * @param src 数据源字节数组
     * @param dest  目标字节数组
     * @param srcPos 数据源起始角标
     * @param destPos 目标起始角标
     * @param length 长度
     */
    fun arrayCopyOfRange(
        src: ByteArray,
        srcPos: Int,
        dest: ByteArray,
        destPos: Int,
        length: Int
    ): ByteArray? {
        if (length > src.size) throw IllegalArgumentException("$length > src byteArray max length")
        System.arraycopy(src, srcPos, dest, destPos, length)
        return dest
    }

    /**
     * 判断 {@code original} 数组中是否包含 {@code char}字符，如果判断存在该字符 {@code char},返回 {@code original}
     * 数组相等字符 {@code bytes}角标。不存在该字符 {@code char}，默认返回 -1
     * @param original 字节数组
     * @param char 字符
     */
    fun indexOf(original: ByteArray, char: Char): Int {
        original.forEachIndexed { index, bytes ->
            if (char == bytes.toChar()) {
                return index
            }
        }
        return -1
    }


}