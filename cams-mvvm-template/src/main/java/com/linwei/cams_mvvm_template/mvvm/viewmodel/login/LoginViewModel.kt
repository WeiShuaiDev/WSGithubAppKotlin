package com.linwei.cams_mvvm_template.mvvm.viewmodel.login

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.cams_mvvm_template.mvvm.contract.login.LoginContract
import com.linwei.cams_mvvm_template.mvvm.model.login.LoginModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/24
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LoginViewModel @Inject constructor(
        model: LoginModel,
        application: Application
) : BaseViewModel(model, application), LoginContract.ViewModel {

}