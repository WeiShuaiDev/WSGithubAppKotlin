package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.color
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.bean.User
import com.linwei.github_mvvm.mvvm.model.conversion.EventConversion
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
import com.linwei.github_mvvm.mvvm.model.repository.MineModel
import com.linwei.github_mvvm.mvvm.model.ui.EventUIModel
import com.linwei.github_mvvm.mvvm.model.ui.UserUIModel
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
class MineViewModel @Inject constructor(
    private val model: MineModel,
    private val appGlobalModel: AppGlobalModel,
    application: Application
) : BaseViewModel(model, application), MineContract.ViewModel {

    /**
     * 接收通知数据
     */
    private val _notifyColor = MutableLiveData<Int>()
    val notifyColor: LiveData<Int>
        get() = _notifyColor

    /**
     * 用户关注数据
     */
    private val _pageByOrgMember = MutableLiveData<Page<List<User>>>()
    val pageByOrgMember: LiveData<Page<List<User>>>
        get() = _pageByOrgMember

    /**
     * 用户产生数据
     */
    private val _pageByUserEvents = MutableLiveData<Page<List<Event>>>()
    val pageByUserEvents: LiveData<Page<List<Event>>>
        get() = _pageByUserEvents

    override fun loadDataByRefresh() {
        //获取用户接收到的事件数据
        toNotifyData()
        //获取用户信息数据
        toPersonInfo()
    }

    override fun loadDataByLoadMore(page: Int) {
        (appGlobalModel.userObservable.type == "Organization").yes {
            //获取用户关注数据
            toOrgMembers(page)
        }.otherwise {
            //获取用户产生事件数据
            toUserEvents(page)
        }
    }

    override fun toPersonInfo() {
        mLifecycleOwner?.let {

        }
    }

    override fun toOrgMembers(page: Int) {
        mLifecycleOwner?.let {
            model.obtainOrgMembers(
                it, page,
                object : LiveDataCallBack<Page<List<User>>>() {
                    override fun onSuccess(code: String?, data: Page<List<User>>?) {
                        super.onSuccess(code, data)

                        data?.result.isNotNullOrSize().yes {
                            _pageByOrgMember.value = data

                            postUpdateStatus(StatusCode.SUCCESS)
                        }.otherwise {
                            postUpdateStatus(StatusCode.FAILURE)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        postUpdateStatus(StatusCode.ERROR)
                    }
                })
        }
    }

    override fun toUserEvents(page: Int) {
        mLifecycleOwner?.let {
            model.obtainUserEvents(
                it, page,
                object : LiveDataCallBack<Page<List<Event>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Event>>?) {
                        super.onSuccess(code, data)

                        data?.result.isNotNullOrSize().yes {
                            _pageByUserEvents.value = data

                            postUpdateStatus(StatusCode.SUCCESS)
                        }.otherwise {

                            postUpdateStatus(StatusCode.FAILURE)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        postUpdateStatus(StatusCode.ERROR)
                    }
                })
        }
    }

    override fun toNotifyData() {
        mLifecycleOwner?.let {
            model.obtainNotify(
                it, null, null,
                1,
                object : LiveDataCallBack<Page<List<Notification>>>() {
                    override fun onSuccess(code: String?, data: Page<List<Notification>>?) {
                        super.onSuccess(code, data)

                        data?.result.isNotNullOrSize().yes {
                            _notifyColor.value = R.color.colorNotifyActiveText.color()

                            postUpdateStatus(StatusCode.SUCCESS)
                        }.otherwise {
                            _notifyColor.value = R.color.colorNotifyNormalText.color()

                            postUpdateStatus(StatusCode.FAILURE)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        _notifyColor.value = R.color.colorNotifyNormalText.color()

                        postUpdateStatus(StatusCode.ERROR)
                    }
                })
        }
    }

    /**
     * `Tab` 点击事件
     * @param v [View]
     */
    fun onTabIconClick(v: View?) {
        when (v?.id) {
            R.id.mine_header_repos -> {  //仓库

            }

            R.id.mine_header_fan -> {   //粉丝

            }

            R.id.mine_header_focus -> {   //关注

            }

            R.id.mine_header_star -> {   //星标

            }

            R.id.mine_header_honor -> {  //荣誉

            }
        }
    }
}