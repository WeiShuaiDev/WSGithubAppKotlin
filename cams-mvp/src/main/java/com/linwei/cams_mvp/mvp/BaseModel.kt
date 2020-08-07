package com.linwei.cams_mvp.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.linwei.cams.ext.execute
import com.linwei.cams.http.callback.RxJavaCallback
import com.linwei.cams.http.model.BaseResponse
import com.linwei.cams.manager.RepositoryManager
import com.linwei.cams.utils.NetworkUtils
import io.reactivex.Observable

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构  `Model` 接口实现类,提供网络请求功能。
 *-----------------------------------------------------------------------
 */
open class BaseModel(private var repositoryManager: RepositoryManager?) : IModel,
    LifecycleObserver {

    override fun onDestroy() {
        repositoryManager = null
    }

    /**
     * @param observable [Observable] 接口对象
     * @param callBack  [RxJavaCallback] 接口回调对象
     */
    fun <T> doRequest(observable: Observable<BaseResponse<T>>, subscriber: RxJavaCallback<T>) {
        if (NetworkUtils.isNetworkAvailable())
            observable.execute(subscriber)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }
}