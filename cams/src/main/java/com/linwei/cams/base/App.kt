package com.linwei.cams.base

import android.app.Application
import com.linwei.cams.di.component.AppComponent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface App {

    fun getAppComponent(): AppComponent

    fun getApplication(): Application
}