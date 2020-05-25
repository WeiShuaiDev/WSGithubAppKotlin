package com.linwei.frame.common.activity

import android.app.Activity
import com.linwei.frame.di.component.AppComponent
import com.linwei.frame.http.cache.Cache

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Activity接口
 *-----------------------------------------------------------------------
 */
interface IActivity {

    /**
     * 提供在 {@link Activity} 生命周期内的缓存容器,
     * 可向此 {@link Activity} 存取一些必要的数据
     */
    fun provideCache(): Cache<String, Object>

    /**
     * 提供给 {@link IActivity}实现类，进行{@code appComponent}依赖
     * AppComponent主要提供一些单例工具
     *@param appComponent
     */
    fun setupActivityComponent(appComponent: AppComponent)

    /**
     * 当前Activity对象增加到内存栈，方便管理
     * @param activity
     */
    fun addStackSingleActivity(activity: Activity?)


    /**
     * 删除内存栈中当前Activity对象
     * @param activity
     */
    fun removeStackSingleActivity(activity: Activity?)


    /**
     * 是否使用事件总线，默认{@link Boolean}是false,内部提供RxBus、EventBus,Otto事件总线，根据Maven地址动态切换。
     * 如果需要用RxBus,EventBus,Otto库，需要在项目app build.gradle的dependencies手动增加Maven地址，
     * 不使用就不要引入进来，减少包体积。
     */
    fun useEventBus(): Boolean = false


}