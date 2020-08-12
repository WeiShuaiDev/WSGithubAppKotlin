package com.linwei.github_mvvm.mvvm.viewmodel

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.MainContract
import com.linwei.github_mvvm.mvvm.model.MainModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainViewModel constructor(
    model: MainModel,
    application: Application
) : BaseViewModel(model, application), MainContract.ViewModel {


}