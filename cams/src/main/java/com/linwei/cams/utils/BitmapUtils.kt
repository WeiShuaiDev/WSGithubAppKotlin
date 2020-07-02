package com.linwei.cams.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/27
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: BitmapUtils工具类
 *-----------------------------------------------------------------------
 */
object BitmapUtils {

    /**
     * {@code bitmap}类型图片转换为字节数组 {@link ByteArray}
     * 通过字节数组流写入 {@code bitmap}图片数据，并压缩图片质量，转化为字节数组
     * @param bitmap Bitmap图片
     * @param quality 图片压缩质量
     */
    fun bitmap2Bytes(bitmap: Bitmap?, quality: Int = 100): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, quality, baos)
        return baos.toByteArray()
    }

    /**
     * {@link ByteArray}字节数组转换为 {@code bitmap} 类型图片
     *@param byteArray 字节数组数据
     */
    fun bytes2Bitmap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null || byteArray.isEmpty()) return null
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    /**
     *  {@code drawable}图片转换为 {@code bitmap} 类型图片
     * @param drawable
     */
    fun drawable2Bitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null
        //根据透明度，创建Bitmap Config配置信息
        @Suppress("DEPRECATION") val config: Bitmap.Config =
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        //根据配置信息，drawable长度，高度，创建Bitmap对象
        val bitmap: Bitmap =
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, config)
        //根据Bitmap,创建对应Canvas
        val canvas = Canvas(bitmap)
        drawable.bounds = Rect(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        //把 drawable 内容画到画布中
        drawable.draw(canvas)
        return bitmap
    }

    /**
     * {@code bitmap}类型图片转换为 {@code Drawable} 类型
     * 创建一个空的 {@link BitmapDrawable}对象，并设置 {@code bitmap}中密度 density
     * @param bitmap Bitmap图片
     */
    fun bitmap2Drawable(bitmap: Bitmap?): Drawable? {
        if (bitmap == null) return null
        @Suppress("DEPRECATION") val bitmapDrawable = BitmapDrawable(bitmap)
        bitmapDrawable.setTargetDensity(bitmap.density)
        return bitmapDrawable
    }

}