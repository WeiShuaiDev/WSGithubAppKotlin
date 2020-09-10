package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.model.main.DynamicModel
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
class DynamicViewModel @Inject constructor(
    model: DynamicModel,
    application: Application
) : BaseViewModel(model, application), DynamicContract.ViewModel {


}