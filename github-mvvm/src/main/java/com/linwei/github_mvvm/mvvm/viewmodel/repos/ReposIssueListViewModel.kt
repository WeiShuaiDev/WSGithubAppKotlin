package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposIssueListModel
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
class ReposIssueListViewModel @Inject constructor(
    val model: ReposIssueListModel,
    application: Application
) : BaseViewModel(model, application), ReposIssueListContract.ViewModel {


}