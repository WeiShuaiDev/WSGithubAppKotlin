package com.linwei.cams_mvp.lifecycle

import android.app.Activity
import com.trello.rxlifecycle4.android.ActivityEvent

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 让 [Activity] 实现此接口,即可正常使用 {@link RxLifecycle}
 *-----------------------------------------------------------------------
 */
interface ActivityLifecycleable : Lifecycleable<ActivityEvent> {
}