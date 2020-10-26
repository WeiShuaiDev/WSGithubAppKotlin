package com.linwei.github_mvvm.mvvm.model.repository.service

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.api.service.NotificationService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.utils.GsonUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MineModel @Inject constructor(dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), MineContract.Model {

    /**
     *  通知服务接口
     */
    private val notificationService: NotificationService by lazy {
        dataRepository.obtainRetrofitService(NotificationService::class.java)
    }

    override fun requestNotify(
        owner: LifecycleOwner,
        all: Boolean?,
        participating: Boolean?,
        page: Int,
        observer: LiveDataCallBack<Page<List<Notification>>>
    ): LiveData<Page<List<Notification>>> {

        return if (all == null || participating == null) {
            notificationService.getNotificationUnRead(true, page)
        } else {
            notificationService.getNotification(true, all, participating, page)
        }.apply {
            observe(
                owner,
                object : LiveDataCallBack<Page<List<Notification>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Notification>>?) {
                        super.onSuccess(code, data)
                        data?.let {
                            observer.onSuccess(code, data)
                            Timber.i(" request Http Notification Data Success~")
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        observer.onFailure(code, message)
                        Timber.i(" request Http Notification Data Failed~")
                    }
                })
        }
    }
}