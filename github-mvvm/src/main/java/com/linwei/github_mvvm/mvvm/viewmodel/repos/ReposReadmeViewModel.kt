package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.isNotNullOrEmpty
import com.linwei.cams.ext.string
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposReadmeContract
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposReadmeModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDevA
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposReadmeViewModel @Inject constructor(
    val model: ReposReadmeModel,
    application: Application
) : BaseViewModel(model, application), ReposReadmeContract.ViewModel {

    /**
     * Readme url
     */
    private val _readmeUrl = MutableLiveData<String>()
    val readmeUrl: LiveData<String>
        get() = _readmeUrl

    override fun toReposReadme(userName: String?, reposName: String?) {
        if (isEmptyParameter(userName, reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            postUpdateStatus(StatusCode.FAILURE)
            return
        }

        mLifecycleOwner?.let {
            model.obtainReposReadme(
                it,
                userName!!,
                reposName!!,
                object : LiveDataCallBack<String>() {
                    override fun onSuccess(code: String?, data: String?) {
                        super.onSuccess(code, data)
                        if (data.isNotNullOrEmpty()) {
                            _readmeUrl.value = data
                            postUpdateStatus(StatusCode.SUCCESS)
                        } else {
                            postUpdateStatus(StatusCode.ERROR)
                        }
                    }

                    override fun onFailure(code: String?, message: String?) {
                        super.onFailure(code, message)
                        postUpdateStatus(StatusCode.FAILURE)
                    }
                })
        }
    }
}