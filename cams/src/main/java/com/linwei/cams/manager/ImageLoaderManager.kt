package com.linwei.cams.manager

import com.linwei.cams.http.glide.ImageLoader
import kotlin.properties.Delegates

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/27
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ImageLoaderManager private constructor(private val mImageLoader: ImageLoader) :
    ImageLoader by mImageLoader {

    companion object {
        //委托notNull，这个值在被获取之前没有被分配，它就会抛出一个异常。
        var sInstance: ImageLoaderManager by Delegates.notNull()

        /**
         * 静态初始化、建议Application中初始化
         * @param imageLoader 内含GSYPicassoImageLoader、GSYFrescoImageLoader、GSYPicassoImageLoader
         */
        fun initialize(imageLoader: ImageLoader) {
            sInstance = ImageLoaderManager(imageLoader)
        }
    }

    /**
     * 图片加载对象
     */
    fun imageLoader(): ImageLoader {
        return this
    }

    /**
     * 强制转换的图片加载对象
     */
    fun <T : ImageLoader> imageLoaderExtend(): T {
        return this as T
    }
}