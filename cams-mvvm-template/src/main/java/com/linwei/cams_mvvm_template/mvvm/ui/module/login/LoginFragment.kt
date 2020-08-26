package com.linwei.cams_mvvm_template.mvvm.ui.module.login

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.cams_mvvm_template.R
import com.linwei.cams_mvvm_template.databinding.FragmentLoginBinding
import com.linwei.cams_mvvm_template.mvvm.contract.login.LoginContract
import com.linwei.cams_mvvm_template.mvvm.viewmodel.login.LoginViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/7
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LoginFragment : BaseMvvmFragment<LoginViewModel, FragmentLoginBinding>(), LoginContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_login

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
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