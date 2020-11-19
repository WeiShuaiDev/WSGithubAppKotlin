package com.linwei.github_mvvm.mvvm.ui.module.issue

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentIssueDetailBinding
import com.linwei.github_mvvm.mvvm.contract.issue.IssueDetailContract
import com.linwei.github_mvvm.mvvm.viewmodel.issue.IssueDetailViewModel
import kotlinx.android.synthetic.main.fragment_issue_detail.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/18
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class IssueDetailFragment : BaseMvvmFragment<IssueDetailViewModel, FragmentIssueDetailBinding>(),
    IssueDetailContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_issue_detail

    override fun obtainStateViewRoot(): View? = issue_detail_root

    override fun bindViewModel() {
        mViewModel?.let {
            it.mLifecycleOwner = viewLifecycleOwner
        }

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