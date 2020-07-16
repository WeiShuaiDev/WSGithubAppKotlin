package com.linwei.cams_aac.di.module

import com.linwei.cams_aac.di.scope.ModelScope
import com.linwei.cams_aac.http.DataAacRepository
import com.linwei.cams_aac.http.IDataAacRepository
import dagger.Binds
import dagger.Module

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/16
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Module(includes = [AacModelModule.Bindings::class])
object AacModelModule {

    @Module
    interface Bindings {

        @ModelScope
        @Binds
        fun bindDataAacRepository(dataAacRepository: DataAacRepository): IDataAacRepository
    }
}