package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposFileListContract
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposFileListModel
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

}