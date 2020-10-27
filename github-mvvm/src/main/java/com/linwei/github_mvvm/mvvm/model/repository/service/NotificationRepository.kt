package com.linwei.github_mvvm.mvvm.model.repository.service

import android.app.Application
import android.os.Build
import android.util.Base64
import android.webkit.CookieManager
import android.webkit.WebStorage
import androidx.lifecycle.*
import com.linwei.cams.ext.*
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.accessTokenPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authIDPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.authInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.putAuthInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.putUserInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userBasicCodePref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userInfoPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userNamePref
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.AuthService
import com.linwei.github_mvvm.mvvm.model.api.service.NotificationService
import com.linwei.github_mvvm.mvvm.model.api.service.ReposService
import com.linwei.github_mvvm.mvvm.model.api.service.UserService
import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.OrgMemberEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.UserEventEntity
import com.linwei.github_mvvm.utils.GsonUtils
import timber.log.Timber
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/27
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
open class NotificationRepository @Inject constructor(
        private val application: Application,
        private val appGlobalModel: AppGlobalModel,
        dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    private val notificationService: NotificationService by lazy {
        dataRepository.obtainRetrofitService(NotificationService::class.java)
    }

    fun requestNotify(
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
                            observer.onSuccess(code, data)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
        }
    }

}