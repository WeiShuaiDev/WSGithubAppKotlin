package com.linwei.cams_mvvm_template.mvvm.viewmodel.main

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.cams_mvvm_template.mvvm.contract.main.MainContract
import com.linwei.cams_mvvm_template.mvvm.model.main.MainModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainViewModel @Inject constructor(
    model: MainModel,
    application: Application
) : BaseViewModel(model, application), MainContract.ViewModel {


}