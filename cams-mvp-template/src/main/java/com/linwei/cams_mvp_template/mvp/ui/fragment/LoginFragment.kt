package com.linwei.cams_mvp_template.mvp.ui.fragment

import android.view.View
import com.linwei.cams.base.fragment.BaseFragment
import com.linwei.cams_mvp.base.BaseMvpFragment
import com.linwei.cams_mvp.di.component.MvpFragmentComponent
import com.linwei.cams_mvp_template.mvp.contract.MainContract
import com.linwei.cams_mvp_template.mvp.presenter.MainPresenter

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/6
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LoginFragment : BaseMvpFragment<MainPresenter>(), MainContract.View  {
    override fun setUpFragmentChildComponent(mvpFragmentComponent: MvpFragmentComponent?) {
    }

    override fun initLayoutView(rootView: View?) {
    }

    override fun initLayoutData() {
    }

    override fun initLayoutListener() {
    }

    override fun reloadData() {
    }

    override fun loadData() {
    }
}