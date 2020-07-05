package com.linwei.cams.ext

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * 启动默认浏览器
 */
fun Context.jumpIntent(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

/*
*https://play.google.com/store/apps/details?id="
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
 * "market://details?"
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