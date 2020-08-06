package com.linwei.cams_mvvm_template.di.module

import com.linwei.cams_mvp.di.scope.ModelScope
import com.linwei.cams_mvp_template.mvp.model.MainModel
import com.linwei.cams_mvvm_template.mvvm.contract.MainContract
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

        @ModelScope
        @Binds
        fun bindMainModel(mainModel: MainModel): MainContract.Model

    }
}
