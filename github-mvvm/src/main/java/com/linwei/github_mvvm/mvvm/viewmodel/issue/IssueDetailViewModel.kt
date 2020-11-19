package com.linwei.github_mvvm.mvvm.viewmodel.issue

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.issue.IssueDetailContract
import com.linwei.github_mvvm.mvvm.model.repository.issue.IssueDetailModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/18
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDevA
 * @Description:
 *-----------------------------------------------------------------------
 */
class IssueDetailViewModel @Inject constructor(
    val model: IssueDetailModel,
    application: Application
) : BaseViewModel(model, application), IssueDetailContract.ViewModel {


}