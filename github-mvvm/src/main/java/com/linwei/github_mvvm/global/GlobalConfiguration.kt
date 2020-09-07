package com.linwei.github_mvvm.global

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.module.ClientModule
import com.linwei.cams.di.module.GlobalConfigModule
import com.linwei.cams.http.adapter.LiveDataCallAdapterFactory
import com.linwei.github_mvvm.mvvm.model.api.Api
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class GlobalConfiguration : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.httpUrl(Api.GITHUB_BASE_URL)
            .retrofitConfiguration(object : ClientModule.RetrofitConfiguration {
                override fun configRetrofit(context: Context, builder: Retrofit.Builder) {
                    //LiveData 适配器配置
                    builder.addCallAdapterFactory(LiveDataCallAdapterFactory())
                }
            })
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