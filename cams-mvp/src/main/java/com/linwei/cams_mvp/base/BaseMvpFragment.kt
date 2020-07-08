package com.linwei.cams_mvp.base

import com.linwei.cams.base.fragment.BaseFragment
import com.linwei.cams.di.component.AppComponent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/8
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构 `Fragment`基类
 *-----------------------------------------------------------------------
 */
abstract class BaseMvpFragment : BaseFragment() {
    override fun setupFragmentComponent(appComponent: AppComponent?) {
    }

}