package com.linwei.frame.ext

import android.app.ActivityManager
import android.content.Context
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.linwei.frame.R
import com.linwei.frame.base.App
import com.linwei.frame.di.component.AppComponent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 提供 AppCompat 扩展方法
 *-----------------------------------------------------------------------
 */

/**
 *
 */
fun Context.obtainAppComponent(): AppComponent {
    return (applicationContext as App).getAppComponent()
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

