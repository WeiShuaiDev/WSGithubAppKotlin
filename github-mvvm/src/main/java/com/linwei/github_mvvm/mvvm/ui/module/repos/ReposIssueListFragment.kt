package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentReposIssueListBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.ui.view.ExpandNavigationTabBar
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
    ReposIssueListContract.View, NavigationTabBar.OnTabBarSelectedIndexListener {

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

    override fun bindingContentView(
        inflater: LayoutInflater,
        contentView: View,
        parentView: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (useDataBinding()) {
            mViewDataBinding = DataBindingUtil.bind(contentView, GithubDataBindingComponent())
            mViewDataBinding?.root
        } else null
    }

    override fun initLayoutView(rootView: View?) {
        repos_issue_navigation_tab_bar.models = issueTabModel
        repos_issue_navigation_tab_bar.onTabBarSelectedIndexListener = this
        repos_issue_navigation_tab_bar.modelIndex = 0
    }

    override fun initLayoutData() {

    }

    override fun initLayoutListener() {
        repos_issue_navigation_tab_bar.doubleTouchListener =
            object : ExpandNavigationTabBar.TabDoubleClickListener {
                override fun onDoubleClick(position: Int) {
                }
            }
    }

    override fun reloadData() {

    }

    override fun loadData() {

    }

    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
    }
}