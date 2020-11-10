package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.string
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposFileListContract
import com.linwei.github_mvvm.mvvm.model.bean.FileModel
import com.linwei.github_mvvm.mvvm.model.conversion.ReposConversion
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposFileListModel
import com.linwei.github_mvvm.mvvm.model.ui.FileUIModel
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
class ReposFileListViewModel @Inject constructor(
    val model: ReposFileListModel,
    application: Application
) : BaseViewModel(model, application), ReposFileListContract.ViewModel {

    private val _fileUIModel = MutableLiveData<List<FileUIModel>>()
    val fileUIModel: LiveData<List<FileUIModel>>
        get() = _fileUIModel

    var path: String? = ""

    var userName: String? = ""

    var reposName: String? = ""

    override fun toFiles() {
        if (isEmptyParameter(userName, reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            postUpdateStatus(StatusCode.FAILURE)
            return
        }

        mLifecycleOwner?.let {
            model.obtainFiles(
                it,
                userName!!,
                reposName!!,
                path!!,
                object : LiveDataCallBack<List<FileModel>>() {
                    override fun onSuccess(code: String?, data: List<FileModel>?) {
                        super.onSuccess(code, data)
                        if (data.isNotNullOrSize()) {
                            _fileUIModel.value = ReposConversion.fileListToFileUIList(data)
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