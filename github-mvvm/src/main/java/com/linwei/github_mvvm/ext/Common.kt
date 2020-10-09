package com.linwei.github_mvvm.ext

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.linwei.cams.ext.dp
import com.linwei.cams.http.glide.GlideLoadOption
import com.linwei.cams.manager.ImageLoaderManager
import com.linwei.cams.utils.TimeUtils.getDate
import com.linwei.github_mvvm.R
import java.util.*


/**
 * Navigation 的页面跳转
 * @param view [View]
 * @param args [Bundle]
 * @param actionId [Int]
 * @param inclusive [Boolean]
 */
fun navigationPopUpTo(
    view: View,
    args: Bundle?,
    actionId: Int,
    inclusive: Boolean
) {
    val controller: NavController = Navigation.findNavController(view)
    controller.navigate(
        actionId,
        args, NavOptions.Builder().setPopUpTo(controller.graph.id, inclusive).build()
    )
}

/**
 * Navigation 退出
 * @param view [View]
 */
fun navigationBack(view: View) {
    val controller: NavController = Navigation.findNavController(view)
    controller.popBackStack()
}

/**
 * 加载用户头像图片
 * @param imageView [ImageView]
 * @param url [String]
 * @param size [Point]
 */
fun loadUserHeaderImage(imageView: ImageView, url: String, size: Point = Point(50.dp, 50.dp)) {
    val option: GlideLoadOption = GlideLoadOption()
        .setDefaultImg(R.mipmap.ic_launcher)
        .setErrorImg(R.mipmap.ic_launcher)
        .setCircle(true)
        .setSize(size)
        .setUri(url)
    ImageLoaderManager.sInstance.imageLoader().loadImage(option, imageView, null)
}



