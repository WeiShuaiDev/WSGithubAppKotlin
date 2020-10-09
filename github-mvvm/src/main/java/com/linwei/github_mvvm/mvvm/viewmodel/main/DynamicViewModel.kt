package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.main.DynamicModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class DynamicViewModel @Inject constructor(
    val model: DynamicModel,
    application: Application
) : BaseViewModel(model, application), DynamicContract.ViewModel {

    private val _eventUiModel = MutableLiveData<Event>()
    val eventUiModel: LiveData<Event>
        get() = _eventUiModel

    override fun toReceivedEvent(page: Int) {
        mLifecycleOwner?.let {
            model.requestReceivedEvent(it, page, _eventUiModel)
        }
    }
}