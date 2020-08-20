package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import com.linwei.github_mvvm.mvvm.model.main.RecommendedModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RecommendedViewModel constructor(
    model: RecommendedModel,
    application: Application
) : BaseViewModel(model, application), RecommendedContract.ViewModel {


}