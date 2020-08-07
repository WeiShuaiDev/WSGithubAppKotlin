package com.linwei.cams_mvp_template.di.component

import com.linwei.cams_mvp.di.component.MvpActivityComponent
import com.linwei.cams_mvp_template.di.module.MainModule
import com.linwei.cams_mvp_template.di.scope.MvpScope
import com.linwei.cams_mvp_template.mvp.contract.MainContract
import com.linwei.cams_mvp_template.mvp.ui.activity.MainActivity
import dagger.BindsInstance
import dagger.Component

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/6
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */

@MvpScope
@Component(
    dependencies = [MvpActivityComponent::class],
    modules = [MainModule::class]
)
interface MainActivityComponent {
    /**
     * 注入 [MainActivity] 对象
     * @param activity [MainActivity]
     */
    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun view(view: MainContract.View): Builder

        fun component(component: MvpActivityComponent): Builder

        fun build(): MainActivityComponent
    }
}