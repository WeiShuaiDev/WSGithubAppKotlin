package com.linwei.cams_aac.di.module

import com.linwei.cams_aac.di.scope.ModelScope
import com.linwei.cams_aac.http.DataAacRepository
import com.linwei.cams_aac.http.IDataAacRepository
import com.linwei.cams_aac.livedatabus.MessageLiveEvent
import com.linwei.cams_aac.livedatabus.StatusLiveEvent
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

    /**
     * 消息事件总线
     * @return message [MessageLiveEvent]
     */
    @ModelScope
    @Provides
    internal fun provideMessageLiveEvent(): MessageLiveEvent {
        return MessageLiveEvent()
    }

    /**
     * 状态事件总线
     */
    @ModelScope
    @Provides
    internal fun provideStatusLiveEvent(): StatusLiveEvent {
        return StatusLiveEvent()
    }


    @Module
    abstract class Bindings {
        @ModelScope
        @Binds
        internal abstract fun bindDataAacRepository(dataAacRepository: DataAacRepository): IDataAacRepository
    }
}