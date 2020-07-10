package com.linwei.cams_aac.global

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.module.GlobalConfigModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class GlobalAacConfiguration : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {

    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {
    }

}