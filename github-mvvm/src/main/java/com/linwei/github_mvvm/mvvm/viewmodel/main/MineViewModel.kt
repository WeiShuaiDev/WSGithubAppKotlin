package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import android.view.View
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.main.MineModel
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
class MineViewModel @Inject constructor(
    model: MineModel,
    application: Application
) : BaseViewModel(model, application), MineContract.ViewModel {


    fun onTabIconClick(v: View?) {

    }

}