package com.linwei.cams_mvvm_template.global

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.module.GlobalConfigModule

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class GlobalConfiguration : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {

    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(ActivityLifecycleImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {
        lifecycles.add(FragmentLifecycleImpl())
    }
}