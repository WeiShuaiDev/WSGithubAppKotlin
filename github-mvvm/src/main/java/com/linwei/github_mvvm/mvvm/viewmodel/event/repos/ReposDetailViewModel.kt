package com.linwei.github_mvvm.mvvm.viewmodel.event.repos

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.model.repository.event.ReposDetailModel
import com.linwei.github_mvvm.mvvm.model.repository.login.LoginModel
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
) : BaseViewModel(model, application) {


}