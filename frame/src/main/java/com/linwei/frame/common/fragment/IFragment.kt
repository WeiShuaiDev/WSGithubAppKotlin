package com.linwei.frame.common.fragment

import com.linwei.frame.di.component.AppComponent

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
     * 提供给 {@link IFragment}实现类，进行{@link AppComponent}依赖
     * AppComponent主要提供一些单例工具
     *@param appComponent
     */
    fun setupFragmentComponent(appComponent: AppComponent)
}