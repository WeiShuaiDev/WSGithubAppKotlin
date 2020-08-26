package com.linwei.cams_mvvm.di.module

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.http.IDataMvvmRepository
import com.linwei.cams_mvvm.livedatabus.MessageLiveEvent
import com.linwei.cams_mvvm.livedatabus.StatusLiveEvent
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/16
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: `MVVM` 架构 `Model` 中 `Module`模块,提供事件总线，数据处理库。
 *-----------------------------------------------------------------------
 */
@Module(includes = [MvvmModelModule.Bindings::class])
object MvvmModelModule {

    /**
     * 消息事件总线
     * @return message [MessageLiveEvent]
     */
    @Provides
    fun provideMessageLiveEvent(): MessageLiveEvent {
        return MessageLiveEvent()
    }

    /**
     * 状态事件总线
     */
    @Provides
    fun provideStatusLiveEvent(): StatusLiveEvent {
        return StatusLiveEvent()
    }


    @Module
    abstract class Bindings {
        @Binds
        abstract fun bindDataAacRepository(dataAacRepository: DataMvvmRepository): IDataMvvmRepository
    }
}