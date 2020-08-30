package com.linwei.cams.http.glide.loader

import android.graphics.drawable.Drawable

import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.linwei.cams.http.glide.ImageLoader
import java.io.File

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/30
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class GlideImageDownLoadTarget constructor(private val mCallback: ImageLoader.Callback?) :
    SimpleTarget<File>() {

    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
        mCallback?.onSuccess(resource)
    }

    override fun onLoadStarted(placeholder: Drawable?) {
        super.onLoadStarted(placeholder)
        mCallback?.onStart()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        mCallback?.onFail(null)
    }

}