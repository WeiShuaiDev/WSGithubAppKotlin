package com.linwei.github_mvvm.mvvm.viewmodel.login

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.model.login.AccountLoginModel
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
class AccountLoginViewModel @Inject constructor(
    model: AccountLoginModel,
    application: Application
) : BaseViewModel(model, application), AccountLoginContract.ViewModel {


}