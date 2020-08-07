package com.linwei.cams_mvp.lifecycle

import android.app.Activity
import io.reactivex.subjects.Subject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `MVP` 架构  [Activity]、[Fragment]实现此接口,即可正常使用 [RxLifecycle],无需再继承 [RxLifecycle]
 *-----------------------------------------------------------------------
 */
interface RxLifecycle<E> {

    fun provideLifecycleSubject(): Subject<E>?
}
