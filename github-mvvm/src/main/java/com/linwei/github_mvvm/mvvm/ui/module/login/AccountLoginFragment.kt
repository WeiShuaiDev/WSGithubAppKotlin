package com.linwei.github_mvvm.mvvm.ui.module.login

import android.view.View
import androidx.lifecycle.Observer
import com.linwei.cams.ext.onClick
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.showShort
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentAccountLoginBinding
import com.linwei.github_mvvm.ext.navigationPopUpTo
import com.linwei.github_mvvm.mvvm.contract.login.AccountLoginContract
import com.linwei.github_mvvm.mvvm.ui.module.main.MainActivity
import com.linwei.github_mvvm.mvvm.viewmodel.login.AccountLoginViewModel
import kotlinx.android.synthetic.main.fragment_account_login.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `UserName` + `PassWord` 普通登录页面
 *-----------------------------------------------------------------------
 */
class AccountLoginFragment : BaseMvvmFragment<AccountLoginViewModel, FragmentAccountLoginBinding>(),
    AccountLoginContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_account_login

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner

        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {

    }

    override fun initLayoutData() {
        mViewModel?.loginResult?.observe(viewLifecycleOwner, Observer {
            it.yes {
                R.string.logcat_login_success.showShort()

                //登录成功后跳转回首页
                MainActivity.start(mContext)
            }.otherwise {
                R.string.logcat_login_failed.showShort()
            }
        })
    }

    override fun initLayoutListener() {
        login_quick.onClick({
            navigationPopUpTo(requireView(), null, R.id.action_account_login_to_oauth_login, false)
        })
    }

    override fun reloadData() {

    }

    override fun loadData() {

    }

}