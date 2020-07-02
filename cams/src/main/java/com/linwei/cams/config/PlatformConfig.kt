package com.linwei.cams.config

import java.lang.Exception

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: 判断Project项目maven远程库链接判断，通过反射方式进行类判断，
 *     从而来判断是否依赖远程库
 *-----------------------------------------------------------------------
 */
object PlatformConfig {

    val DEPENDENCY_EVENTBUS: Boolean =
        this.findClassByClassName("org.greenrobot.eventbus.EventBus")


    private fun findClassByClassName(className: String): Boolean {
        try {
            Class.forName(className)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}