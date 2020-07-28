package com.linwei.cams.ext

import android.app.ActivityManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.linwei.cams.R
import com.linwei.cams.base.BaseApplication
import com.linwei.cams.utils.ToastUtils
import android.annotation.SuppressLint
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Base64
import com.linwei.cams.base.App
import com.linwei.cams.di.component.AppComponent
import java.io.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 提供 AppCompat 扩展方法
 *-----------------------------------------------------------------------
 */
val ctx: Context = BaseApplication.mContext

/**
 * 获取 [AppComponent]
 */
fun obtainAppComponent(): AppComponent {
    return (ctx as App).getAppComponent()
}

/**
 * 获取 [Application]
 */
fun obtainApplication(): Application {
    return (ctx as App).getApplication()
}


/**
 * 启动默认浏览器
 */
fun Context.jumpIntent(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/**
 * 根据不同的外链，进行跳转
 * 外链格式:https://play.google.com/store/apps/details?id="
 * @param url [String]
 */
fun Context.jumpGooglePlay(url: String) {
    try {
        val launchIntent: Intent? = packageManager.getLaunchIntentForPackage("com.android.vending")
        val componentName: ComponentName = ComponentName(
            "com.android.vending",
            "com.google.android.finsky.activities.LaunchUrlHandlerActivity"
        )
        launchIntent?.component = componentName
        launchIntent?.data = Uri.parse(
            "market://details?id=" + url.split("id=")
                .toTypedArray().get(1)
        )
        startActivity(launchIntent)
    } catch (e: Exception) {
        jumpIntent(url)
    }
}

/**
 * 根据不同的外链，进行跳转
 * 格式:"market://details?"
 * @param url [String]
 */
fun Context.jumpNewGooglePlay(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.setPackage("com.android.vending") //这里对应的是谷歌商店，跳转别的商店改成对应的即可
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else { //没有应用市场，通过浏览器跳转到Google Play
            jumpIntent(url)
        }
    } catch (activityNotFoundException1: ActivityNotFoundException) {
        jumpIntent(url)
    }
}

/**
 * 转换为Base64
 */
fun base64(bytes: ByteArray, flag: Int): String {
    if (bytes.isNotEmpty()) {
        return Base64.encodeToString(bytes, flag)
    } else {
        return ""
    }
}

/**
 * 获取运算后内存大小
 * {@code maxSize}最大内存大小，{@ multiplierSize}内存运算阈值
 */
fun Context.getCalculateCacheSize(maxSize: Int, multiplierSize: Float): Int {
    val activityManager: ActivityManager =
        getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val targetMemoryCacheSize: Int = (activityManager.memoryClass * multiplierSize * 1024).toInt()
    if (targetMemoryCacheSize > maxSize) {
        return maxSize
    }
    return targetMemoryCacheSize
}

/**
 * {@code frameId} 容器中Fragment,替换为 {@code fragment},并设置标识为tag
 */
fun AppCompatActivity.replaceFragmentInActivity(
    fragment: Fragment,
    @IdRes frameId: Int,
    tag: String
) {
    supportFragmentManager.transact {
        replace(frameId, fragment, tag)
    }
}

/**
 * 设置 {@link FragmentManager} 切换动画
 */
@SuppressLint("PrivateResource")
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        setCustomAnimations(
            R.anim.abc_grow_fade_in_from_bottom,
            R.anim.abc_fade_out,
            R.anim.abc_fade_in,
            R.anim.abc_shrink_fade_out_from_bottom
        )
        action()
    }.commit()
}



