package com.linwei.cams.http.glide


import android.graphics.Bitmap
import androidx.annotation.UiThread
import java.io.File

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/29
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface ImageLoader {

    /**
     * 加载图片
     * @param loadOption 加载图片配置
     * @param target 加载目标对象，ImageView or SimpleDraweeView
     * @param callback 加载回调
     * @param extendOption 额外配置接口
     */
    fun loadImage(loadOption: GlideLoadOption, target: Any?, callback: Callback?, extendOption: ExtendedOptions? = null)

    /**
     * 清除缓存
     * @param type GSYImageConst，清除类型
     */
    fun clearCache(type: Int = GlideImageConst.CLEAR_DISK_CACHE)

    /**
     * 清除指定缓存
     * @param type GSYImageConst，清除类型
     * @param loadOption 加载图片配置
     */
    fun clearCacheKey(type: Int = GlideImageConst.CLEAR_DISK_CACHE, loadOption: GlideLoadOption)

    /**
     * 是否已经缓存到本地
     * @param loadOption 加载图片配置
     * @param extendOption 额外配置接口
     * @return Boolean 是否已经缓存到本地
     */
    fun isCache(loadOption: GlideLoadOption, extendOption: ExtendedOptions? = null): Boolean

    /**
     * 获取本地缓存
     * @param loadOption 加载图片配置
     * @param extendOption 额外配置接口
     * @return File
     */
    fun getLocalCache(loadOption: GlideLoadOption, extendOption: ExtendedOptions? = null): File?

    /**
     * 获取本地缓存bitmap
     * @param loadOption 加载图片配置
     * @param extendOption 额外配置接口
     * @return Bitmap
     */
    fun getLocalCacheBitmap(loadOption: GlideLoadOption, extendOption: ExtendedOptions? = null): Bitmap?


    /**
     * 获取本地缓存大小
     * @return Long
     */
    fun getCacheSize(): Long?


    /**
     * 下载图片
     * @param loadOption 加载图片配置
     * @param callback 加载回调
     * @param extendOption 额外配置接口
     * @return Bitmap
     */
    fun downloadOnly(loadOption: GlideLoadOption, callback: Callback?, extendOption: ExtendedOptions? = null)

    /**
     * 额外配置支持
     */
    interface ExtendedOptions {
        /**
         * @param option 配置对象
         * Glide    com.bumptech.glide.request.RequestOptions
         * Picasso  com.squareup.picasso.RequestCreator
         * Fresco   com.facebook.imagepipeline.request.ImageRequestBuilder
         */
        fun onOptionsInit(option: Any?)
    }

    /**
     * 回调接口
     */
    @UiThread
    interface Callback {
        fun onStart()

        fun onSuccess(result: Any?)

        fun onFail(error: Exception?)
    }
}