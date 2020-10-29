package com.linwei.github_mvvm.ext

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.linwei.cams.ext.ctx
import com.linwei.cams.ext.dp
import com.linwei.cams.http.glide.GlideLoadOption
import com.linwei.cams.manager.ImageLoaderManager
import com.linwei.cams.utils.TimeUtils.getDate
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.ui.module.login.UserActivity
import java.util.*
import java.util.regex.Pattern


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

/**
 * 重新在新的任务栈中，启动 [UserActivity]
 */
fun jumpUserActivity() {
    ctx.startActivity(Intent(ctx, UserActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

/**
 * 拓展获取版本号
 */
fun Context.getVersionName(): String {
    val manager:PackageInfo = packageManager.getPackageInfo(packageName, 0)
    return manager.versionName
}

/**
 * 拓展String版本号对比
 */
fun String.compareVersion(v2: String?): String? {
    if (v2 == null || v2.isEmpty()) return null
    val regEx = "[^0-9]"
    val p:Pattern = Pattern.compile(regEx)
    var s1: String = p.matcher(this).replaceAll("").trim()
    var s2: String = p.matcher(v2).replaceAll("").trim()

    val cha: Int = s1.length - s2.length
    val buffer = StringBuffer()
    var i = 0
    while (i < Math.abs(cha)) {
        buffer.append("0")
        ++i
    }

    if (cha > 0) {
        buffer.insert(0, s2)
        s2 = buffer.toString()
    } else if (cha < 0) {
        buffer.insert(0, s1)
        s1 = buffer.toString()
    }

    val s1Int:Int = s1.toInt()
    val s2Int:Int = s2.toInt()

    return if (s1Int > s2Int) this
    else v2
}




