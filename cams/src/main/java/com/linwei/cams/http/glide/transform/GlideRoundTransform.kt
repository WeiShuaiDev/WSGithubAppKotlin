package com.linwei.cams.http.glide.transform

import android.graphics.*
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.linwei.cams.ext.dp2px
import com.linwei.cams.ext.px

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/29
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 第三方 `Glide` 库, 加载图片转换为圆形图片转换器
 *-----------------------------------------------------------------------
 */
class GlideRoundTransform(private var radius: Float = 5f) : CenterCrop() {

    init {
        radius = radius.px.toFloat()
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap? {
        val source: Bitmap = super.transform(pool, toTransform, outWidth, outHeight)
        return roundCrop(pool, source)
    }

    /**
     * 根据 [radius] 大小，绘制圆角图片
     * @param pool [BitmapPool]
     * @param transform [Bitmap]
     * @return [Bitmap]
     */
    private fun roundCrop(pool: BitmapPool, source: Bitmap?): Bitmap? {
        if (source == null) return null

        var result: Bitmap? = pool.get(source.width, source.height, Bitmap.Config.ARGB_8888)
        if (result == null) {
            result = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(result!!)
        val paint = Paint()
        paint.shader = BitmapShader(
            source,
            Shader.TileMode.CLAMP,
            Shader.TileMode.CLAMP
        )
        paint.isAntiAlias = true
        val rectF = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rectF, radius, radius, paint)
        return result
    }
}