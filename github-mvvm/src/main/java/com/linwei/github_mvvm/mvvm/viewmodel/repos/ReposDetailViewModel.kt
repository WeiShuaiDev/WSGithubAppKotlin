package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.string
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposDetailContract
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposDetailModel
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
class ReposDetailViewModel @Inject constructor(
    val model: ReposDetailModel,
    application: Application
) : BaseViewModel(model, application), ReposDetailContract.ViewModel {

    private val starredStatus = MutableLiveData<Boolean>()

    private val watchedStatus = MutableLiveData<Boolean>()

    override fun toReposStatus(reposName: String?) {
        if (isEmptyParameter(reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }

        mLifecycleOwner?.let {
            model.obtainCheckRepoStarred(it, reposName!!, starredStatus)
            model.obtainCheckRepoWatched(it, reposName, watchedStatus)
        }
    }

    override fun toChangeStarStatus(reposName: String?) {
        if (isEmptyParameter(reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }
        mLifecycleOwner?.let {
            model.obtainChangeStarStatus(it, reposName!!, starredStatus)
        }
    }

    override fun toChangeWatchStatus(reposName: String?) {
        if (isEmptyParameter(reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }
        mLifecycleOwner?.let {
            model.obtainChangeWatchStatus(it, reposName!!, watchedStatus)
        }
    }

    override fun toForkRepository(reposName: String?) {
        if (isEmptyParameter(reposName)) {
            postMessage(obj = R.string.unknown_error.string())
            return
        }
        mLifecycleOwner?.let {
            model.obtainChangeWatchStatus(it, reposName!!, watchedStatus)
            model.obtainForkRepository(it, reposName, null)
        }
    }
}