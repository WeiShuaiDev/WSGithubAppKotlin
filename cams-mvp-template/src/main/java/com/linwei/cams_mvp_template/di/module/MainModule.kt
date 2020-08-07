package com.linwei.cams_mvp_template.di.module

import com.linwei.cams_mvp_template.di.scope.MvpScope
import com.linwei.cams_mvp_template.mvp.model.MainModel
import com.linwei.cams_mvp_template.mvp.contract.MainContract
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/6
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module(includes = [MainModule.Bindings::class])
object MainModule {

    @Module
    interface Bindings {

        @MvpScope
        @Binds
        fun bindMainModel(mainModel: MainModel): MainContract.Model

    }
}
