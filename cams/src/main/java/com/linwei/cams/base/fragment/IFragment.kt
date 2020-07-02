package com.linwei.cams.base.fragment

import com.linwei.cams.di.component.AppComponent
import com.linwei.cams.http.cache.Cache

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Fragment接口
 *-----------------------------------------------------------------------
 */
interface IFragment {

    /**
     * 提供在 {@link Activity} 生命周期内的缓存容器,
     * 可向此 {@link Activity} 存取一些必要的数据
     */
    fun provideCache(): Cache<String, Any>

    /**
     * 提供给 {@link IFragment}实现类，进行{@link AppComponent}依赖
     * AppComponent主要提供一些单例工具
     *@param appComponent
     */
    fun setupFragmentComponent(appComponent: AppComponent?)


    /**
     * 是否使用事件总线，默认{@link Boolean}是false,内部提供RxBus、EventBus,Otto事件总线，根据Maven地址动态切换。
     * 如果需要用RxBus,EventBus,Otto库，需要在项目app build.gradle的dependencies手动增加Maven地址，
     * 不使用就不要引入进来，减少包体积。
     */
    fun useEventBus(): Boolean = false
}