package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import android.view.View
import androidx.core.content.ContextCompat
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
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.model.conversion.EventConversion
import com.linwei.github_mvvm.mvvm.model.repository.service.MineModel
import com.linwei.github_mvvm.mvvm.model.ui.EventUIModel
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
    val model: MineModel,
    application: Application
) : BaseViewModel(model, application), MineContract.ViewModel {

    /**
     * 接收通知数据
     */
    private val _notifyColor = MutableLiveData<Int>()
    val notifyColor: LiveData<Int>
        get() = _notifyColor

    override fun toNotifyData() {
        mLifecycleOwner?.let {
            model.requestNotify(
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

            R.id.mine_header_honor -> {

            }
        }
    }

}