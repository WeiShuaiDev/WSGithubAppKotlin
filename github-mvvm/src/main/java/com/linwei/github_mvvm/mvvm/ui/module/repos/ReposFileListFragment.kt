package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.view.View
import androidx.databinding.ViewDataBinding
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentIssueDetailBinding
import com.linwei.github_mvvm.mvvm.contract.repos.ReposFileListContract
import com.linwei.github_mvvm.mvvm.viewmodel.event.issue.IssueDetailViewModel
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposFileListViewModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposFileListFragment(val userName: String?, val reposName: String?) :
    BaseMvvmFragment<ReposFileListViewModel, ViewDataBinding>(),
    ReposFileListContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_repos_file_list

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner
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