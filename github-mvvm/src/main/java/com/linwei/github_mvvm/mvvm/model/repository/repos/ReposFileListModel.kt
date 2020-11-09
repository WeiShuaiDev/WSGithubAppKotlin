package com.linwei.github_mvvm.mvvm.model.repository.repos

import androidx.lifecycle.LifecycleOwner
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposFileListContract
import com.linwei.github_mvvm.mvvm.model.bean.FileModel
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposFileListModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val reposRepository: ReposRepository
) : BaseModel(dataRepository), ReposFileListContract.Model {

    override fun obtainFiles(
        owner: LifecycleOwner,
        userName: String,
        reposName: String,
        path: String,
        observer: LiveDataCallBack<List<FileModel>>
    ) {
        reposRepository.requestFiles(
            owner,
            userName,
            reposName,
            path,
            object : LiveDataCallBack<List<FileModel>>() {
                override fun onSuccess(code: String?, data: List<FileModel>?) {
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