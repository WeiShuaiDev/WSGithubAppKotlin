package com.linwei.frame.common.activity

import com.linwei.frame.di.component.AppComponent

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
     * 提供给 {@link IActivity}实现类，进行{@param AppComponent}依赖
     * AppComponent主要提供一些单例工具
     *@param appComponent
     */
    fun setupActivityComponent(appComponent: AppComponent)
}