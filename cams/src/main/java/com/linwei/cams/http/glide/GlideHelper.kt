package com.linwei.cams.http.glide

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.linwei.cams.R


/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/29
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 第三方 `Glide` 库, 提供加载网络图片
 * 样式：
 *      1、默认图片
 *      2、圆形图片
 *      3、圆角图片
 *
 *属性：1、Http 图片资源
 *      2、ResId 图片资源
 *      3、Uri 图片资源
 *-----------------------------------------------------------------------
 */
class GlideHelper {

    companion object {
        private var INSTANCE: GlideHelper? = null

        @JvmStatic
        fun getInstance(): GlideHelper {
            return INSTANCE
                ?: GlideHelper().apply {
                    INSTANCE = this
                }
        }
    }

    /**
     * 加载 `Http` 图片,根据 [url] 图片地址加载网路图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param url [String]  图片地址
     * @param iv [ImageView] 显示图片控件
     */
    fun load(context: Context, url: String, iv: ImageView) {
        val options = RequestOptions()
        Glide.with(context)
            .load(url)
            .apply(options.placeholder(R.mipmap.ic_bitmap_default))
            .into(iv)
    }


    /**
     * 加载 `Id` 图片,根据 [resId] 图片地址加载图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param url [Int]  图片Id
     * @param iv [ImageView] 显示图片控件
     */
    fun load(context: Context, resId: Int, iv: ImageView) {
        val options = RequestOptions()
        Glide.with(context)
            .load(resId)
            .apply(options.placeholder(R.mipmap.ic_bitmap_default))
            .into(iv)
    }


    /**
     * 加载 `Uri` 图片,根据 [url] 图片地址加载网路图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param uri [Uri]  图片地址
     * @param iv [ImageView] 显示图片控件
     */
    fun load(context: Context, uri: Uri, iv: ImageView) {
        val options = RequestOptions()
        Glide.with(context)
            .load(uri)
            .apply(options.placeholder(R.mipmap.ic_bitmap_default))
            .into(iv)
    }

    /**
     * 加载网络图片,根据 [url] 图片地址加载网路图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param url [String]  图片地址
     * @param iv [ImageView] 显示图片控件
     * @param placeHolderResId [Int] 默认图片
     */
    fun load(
        context: Context,
        url: String,
        iv: ImageView,
        placeHolderResId: Int
    ) {
        if (placeHolderResId == -1) {
            Glide.with(context)
                .load(url)
                .into(iv)
            return
        }
        val options = RequestOptions()
        Glide.with(context)
            .load(url)
            .apply(options.placeholder(placeHolderResId))
            .into(iv)
    }

    /**
     * 加载 `Id` 圆形图片,根据 [resId] 加载图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param url [Int]  图片Id
     * @param iv [ImageView] 显示图片控件
     */
    fun loadRound(
        context: Context,
        resId: Int,
        iv: ImageView
    ) {
        val options = RequestOptions()
        Glide.with(context)
            .load(resId)
            .apply(
                options.placeholder(R.mipmap.ic_bitmap_default)
                    .centerCrop()
                    .circleCrop()
            ).into(iv)
    }


    /**
     *  加载 `Http` 圆形图片,根据 [url] 图片地址加载网路图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param url [String]  图片地址
     * @param iv [ImageView] 显示图片控件
     */
    fun loadRound(
        context: Context,
        url: String,
        iv: ImageView
    ) {
        val options = RequestOptions()
        Glide.with(context)
            .load(url)
            .apply(
                options.placeholder(R.mipmap.ic_bitmap_default)
                    .centerCrop()
                    .circleCrop()
            ).into(iv)
    }

    /**
     *  加载 `Uri` 圆形图片,根据 [url] 图片地址加载图片，并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param uri [Uri]  图片地址
     * @param iv [ImageView] 显示图片控件
     */
    fun loadRound(
        context: Context,
        uri: Uri,
        iv: ImageView
    ) {
        val options = RequestOptions()
        Glide.with(context)
            .load(uri)
            .apply(
                options.placeholder(R.mipmap.ic_bitmap_default)
                    .centerCrop()
                    .circleCrop()
            ).into(iv)
    }

    /**
     *  加载 `Http` 圆角图片,根据 [url] 图片地址加载图片，[radius] 设置图片圆角度数， 并显示在 [iv] 控件上面。
     * @param context [Context] 上下文
     * @param radius [Int] 圆角
     * @param url [String]  图片地址
     * @param iv [ImageView] 显示图片控件
     */
    fun loadRoundCorner(
        context: Context,
        radius: Int,
        url: String,
        iv: ImageView
    ) {
        val options: RequestOptions = RequestOptions()
            .placeholder(R.mipmap.ic_bitmap_default)
            .transform(GlideRoundTransform(radius.toFloat()))
        Glide.with(context)
            .load(url)
            .apply(options)
            .into(iv)
    }
}