package com.linwei.cams_mvp_template.mvp.presenter

import com.linwei.cams.manager.EventBusManager
import com.linwei.cams_mvp.mvp.BasePresenter
import com.linwei.cams_mvp_template.mvp.contract.MainContract
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
class MainPresenter @Inject constructor(
    eventBusManager: EventBusManager,
    model: MainContract.Model,
    rootView: MainContract.View
) : BasePresenter<MainContract.Model, MainContract.View>(
    eventBusManager,
    model,
    rootView
), MainContract.Presenter {


}