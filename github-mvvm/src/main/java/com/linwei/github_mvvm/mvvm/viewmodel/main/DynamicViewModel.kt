package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.conversion.EventConversion
import com.linwei.github_mvvm.mvvm.model.data.EventUIModel
import com.linwei.github_mvvm.mvvm.model.main.DynamicModel
import java.util.ArrayList
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

    /**
     * 接收事件数据
     */
    private val _eventUiModel = MutableLiveData<List<EventUIModel>>()
    val eventUiModel: LiveData<List<EventUIModel>>
        get() = _eventUiModel

    private var _next: Int = -1

    override fun toReceivedEvent(page: Int) {
        mLifecycleOwner?.let {
            postUpdateStatus(StatusCode.LOADING)

            model.requestReceivedEvent(
                it,
                page,
                object : LiveDataCallBack<Page<List<Event>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                        super.onSuccess(code, data)

                        val eventUIList = ArrayList<EventUIModel>()
                        data?.apply {
                            result?.let {
                                for (event: Event in it) {
                                    eventUIList.add(EventConversion.eventToEventUIModel(event))
                                }
                            }
                        }

                        eventUIList.isNotNullOrSize().yes {
                            postUpdateStatus(StatusCode.SUCCESS)
                            _eventUiModel.value = eventUIList
                        }.otherwise {
                            postUpdateStatus(StatusCode.FAILURE)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        _eventUiModel.value = null

                        postUpdateStatus(StatusCode.ERROR)
                    }
                })
        }
    }
}