package com.linwei.cams_mvp.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.linwei.cams.manager.RepositoryManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVP架构中 `Model` 接口实现类
 *-----------------------------------------------------------------------
 */
class BaseModel(private var repositoryManager: RepositoryManager?) : IModel, LifecycleObserver {
    override fun onDestroy() {
        repositoryManager = null
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(this)
    }


}