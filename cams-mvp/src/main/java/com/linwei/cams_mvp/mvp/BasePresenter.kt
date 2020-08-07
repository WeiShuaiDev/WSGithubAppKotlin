package com.linwei.cams_mvp.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.linwei.cams.manager.EventBusManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构  `Presenter` 接口实现类，提供注入 `Model`生命周期，事件总线处理。
 *-----------------------------------------------------------------------
 */
abstract class BasePresenter<M : IModel, V : IView>(
    private var eventBusManager: EventBusManager,
    private var model: M?,
    private var rootView: V?
) : IPresenter,
    LifecycleObserver {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        rootView?.let { v ->
            if (v is LifecycleOwner) {
                v.lifecycle.addObserver(this)

                model?.let { m ->
                    if (m is LifecycleObserver) {
                        v.lifecycle.addObserver(m)
                    }
                }
            }
        }
    }

    override fun onStart() {
        //绑定 `EventBus`
        if (useEventBus()) {
            eventBusManager.register(this)
        }
    }

    override fun onDestroy() {
        //解除 `EventBus` 绑定
        if (useEventBus()) {
            eventBusManager.unRegister(this)
        }
        //清除任务
        unDispose()

        model?.onDestroy()
        model = null
        rootView = null
    }

    /**
     * 是否使用事件总线，默认{@link Boolean}是false,内部提供RxBus、EventBus、Otto事件总线，根据Maven地址动态切换。
     * 如果需要用RxBus、EventBus、Otto库，需要在项目app build.gradle的dependencies手动增加Maven地址，
     * 不使用就不要引入进来，减少包体积。
     * @return [Boolean] true：使用；false：不使用
     */
    open fun useEventBus(): Boolean = false


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }

    /**
     * 将 [Disposable] 添加到 [CompositeDisposable] 中统一管理可在 `Activity#onDestroy()` 中使用
     * [unDispose] 停止正在执行的 RxJava 任务,避免内存泄漏目前框架已使用 [RxLifecycle] 避免内存泄漏,此方法作为备用方案
     *
     * @param disposable
     */
    fun addDispose(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    private fun unDispose() {
        mCompositeDisposable.clear()
    }
}