package com.linwei.github_mvvm.mvvm.model.login

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import io.reactivex.Observable
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class AccountLoginModel @Inject constructor(dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), AccountLoginContract.Model {

    override fun requestAccountLogin(username: String, password: String) {

    }

    override fun requestTokenObservable(): Observable<String> {
        return Observable.just("3")
    }

    override fun requestCodeTokenObservable(code: String): Observable<String> {
        return Observable.just("2")
    }


}