package com.linwei.github_mvvm.mvvm.ui.module.event.repos

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentReposDetailBinding
import com.linwei.github_mvvm.mvvm.contract.event.repos.ReposDetailContract
import com.linwei.github_mvvm.mvvm.viewmodel.event.repos.ReposDetailViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposDetailFragment : BaseMvvmFragment<ReposDetailViewModel, FragmentReposDetailBinding>(),
    ReposDetailContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_repos_detail

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