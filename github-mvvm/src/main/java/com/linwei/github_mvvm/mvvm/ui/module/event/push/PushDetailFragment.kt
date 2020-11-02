package com.linwei.github_mvvm.mvvm.ui.module.event.push

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentPushDetailBinding
import com.linwei.github_mvvm.mvvm.contract.event.push.PushDetailContract
import com.linwei.github_mvvm.mvvm.viewmodel.event.push.PushDetailViewModel

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
        TODO("Not yet implemented")
    }

    override fun initLayoutData() {
        TODO("Not yet implemented")
    }

    override fun initLayoutListener() {
        TODO("Not yet implemented")
    }

    override fun reloadData() {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }



}