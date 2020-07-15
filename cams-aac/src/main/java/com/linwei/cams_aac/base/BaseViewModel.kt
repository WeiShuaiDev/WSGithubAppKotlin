package com.linwei.cams_aac.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.linwei.cams_aac.aac.IViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class BaseViewModel : IViewModel {
    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
    }

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

}