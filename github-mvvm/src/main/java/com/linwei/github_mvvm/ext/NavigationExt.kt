package com.linwei.github_mvvm.ext

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation

/**
 * @Author: weiyun
 * @Time: 2020/9/15
 * @Description:
 */

/**
 * Navigation 的页面跳转
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
 */
fun navigationBack(view: View) {
    val controller: NavController = Navigation.findNavController(view)
    controller.popBackStack()
}
