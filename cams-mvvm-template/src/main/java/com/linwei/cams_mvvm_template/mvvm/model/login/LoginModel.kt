package com.linwei.cams_mvvm_template.mvvm.model.login

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.cams_mvvm_template.mvvm.contract.login.LoginContract
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
class LoginModel @Inject constructor(dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), LoginContract.Model {


}