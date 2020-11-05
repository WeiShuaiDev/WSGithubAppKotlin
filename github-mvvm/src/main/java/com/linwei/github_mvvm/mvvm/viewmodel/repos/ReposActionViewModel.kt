package com.linwei.github_mvvm.mvvm.viewmodel.repos

import android.app.Application
import android.view.View
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposActionListContract
import com.linwei.github_mvvm.mvvm.model.repository.repos.ReposActionListModel
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
class ReposActionViewModel @Inject constructor(
    val model: ReposActionListModel,
    application: Application
) : BaseViewModel(model, application), ReposActionListContract.ViewModel {


    fun onTabIconClick(v: View) {

    }
}