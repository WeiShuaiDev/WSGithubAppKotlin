package com.linwei.github_mvvm.mvvm.ui.module.push

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentPushDetailBinding
import com.linwei.github_mvvm.mvvm.contract.push.PushDetailContract
import com.linwei.github_mvvm.mvvm.viewmodel.push.PushDetailViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class PushDetailFragment : BaseMvvmFragment<PushDetailViewModel, FragmentPushDetailBinding>(),
    PushDetailContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_push_detail

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
    }

    override fun initLayoutListener() {
    }

    override fun reloadData() {
    }

    override fun loadData() {
    }



}