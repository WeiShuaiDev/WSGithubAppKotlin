package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentReposIssueListBinding
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposIssueListViewModel
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_repos_issue_list.*
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposIssueListFragment(val userName: String?, val reposName: String?) :
    BaseMvvmFragment<ReposIssueListViewModel, FragmentReposIssueListBinding>(),
    ReposIssueListContract.View {

    @Inject
    lateinit var issueTabModel: MutableList<NavigationTabBar.Model>

    @Inject
    lateinit var statusList: MutableList<String>

    override fun obtainStateViewRoot(): View? = repos_issue_list_root

    override fun provideContentViewId(): Int = R.layout.fragment_repos_issue_list

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner
        mViewModel?.userName = userName
        mViewModel?.reposName = reposName

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