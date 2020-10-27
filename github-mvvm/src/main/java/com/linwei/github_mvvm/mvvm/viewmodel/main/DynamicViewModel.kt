package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.conversion.EventConversion
import com.linwei.github_mvvm.mvvm.model.ui.EventUIModel
import com.linwei.github_mvvm.mvvm.model.repository.DynamicModel
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
        private val model: DynamicModel,
        application: Application
) : BaseViewModel(model, application), DynamicContract.ViewModel {

    /**
     * 接收事件数据
     */
    private val _page = MutableLiveData<Page<List<Event>>>()
    val page: LiveData<Page<List<Event>>>
        get() = _page

    override fun toReceivedEvent(page: Int) {
        mLifecycleOwner?.let {
            model.obtainReceivedEvent(
                it,
                page,
                object : LiveDataCallBack<Page<List<Event>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                        super.onSuccess(code, data)

                        data?.result.isNotNullOrSize().yes {
                            _page.value = data!!

                            postUpdateStatus(StatusCode.SUCCESS)
                        }.otherwise {
                            _page.value = null

                            postUpdateStatus(StatusCode.FAILURE)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        _page.value = null

                        postUpdateStatus(StatusCode.ERROR)
                    }
                })
        }
    }

    /**
     * 进行数据转换 'Event' ->'EventUIModel'
     */
    fun eventConversionByEventUIModel(page: Page<List<Event>>?): MutableList<EventUIModel> {
        val eventUIList: MutableList<EventUIModel> = mutableListOf()
        page?.apply {
            result?.let {
                for (event: Event in it) {
                    eventUIList.add(EventConversion.eventToEventUIModel(event))
                }
            }
        }
        return eventUIList
    }
}