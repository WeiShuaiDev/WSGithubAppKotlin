package com.linwei.cams_mvp.lifecycle

import com.trello.rxlifecycle4.android.FragmentEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构 [Fragment] 实现此接口,即可正常使用 [RxLifecycle]
 *-----------------------------------------------------------------------
 */
interface FragmentRxLifecycle : RxLifecycle<FragmentEvent> {
}