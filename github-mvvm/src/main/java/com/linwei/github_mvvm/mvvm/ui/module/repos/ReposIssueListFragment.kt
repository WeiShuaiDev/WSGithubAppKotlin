package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.nukc.stateview.StateView
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.string
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentReposIssueListBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.ext.IssueDialogClickListener
import com.linwei.github_mvvm.ext.showIssueEditDialog
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.bean.SearchResult
import com.linwei.github_mvvm.mvvm.model.ui.IssueUIModel
import com.linwei.github_mvvm.mvvm.ui.adapter.IssueAdapter
import com.linwei.github_mvvm.mvvm.viewmodel.ConversionBean
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
    ReposIssueListContract.View, NavigationTabBar.OnTabBarSelectedIndexListener,
    IssueDialogClickListener {

    @Inject
    lateinit var issueTabModel: MutableList<NavigationTabBar.Model>

    @Inject
    lateinit var statusList: MutableList<String>

    private lateinit var mIssueAdapter: IssueAdapter

    private var mPageCode: Int = 1

    override fun obtainStateViewRoot(): View? = repos_issue_list_root

    override fun provideContentViewId(): Int = R.layout.fragment_repos_issue_list

    override fun bindViewModel() {
        mViewModel?.let {
            it.mLifecycleOwner = viewLifecycleOwner
            it.userName = userName
            it.reposName = reposName
            it.status = statusList[0]
        }

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

        mIssueAdapter = IssueAdapter(mutableListOf())
        mIssueAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mIssueAdapter.loadMoreModule.isAutoLoadMore = true   //自动加载
        repos_issue_recycler.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mIssueAdapter
        }

    }

    @Suppress("UNCHECKED_CAST")
    override fun initLayoutData() {
        mViewModel?.page?.observe(viewLifecycleOwner, Observer {
            repos_issue_navigation_tab_bar.isTouchEnable = true
            it?.let {
                mPageCode = it.next

                val list: MutableList<IssueUIModel> = if (it.result is SearchResult<*>) {
                    val searchResult: SearchResult<Issue> = it.result as SearchResult<Issue>
                    ConversionBean.issueConversionByIssueUIModel(searchResult.items)
                } else if (it.result is List<*>) {
                    ConversionBean.issueConversionByIssueUIModel(it.result as List<Issue>)
                } else {
                    mutableListOf()
                }

                list.isNotNullOrSize().yes {
                    if (it.prev == -1) {
                        mIssueAdapter.setNewInstance(list)
                    } else {
                        mIssueAdapter.addData(list)
                    }

                    (it.next == it.last).yes {
                        mIssueAdapter.loadMoreModule.loadMoreEnd()
                    }.otherwise {
                        mIssueAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                if (repos_issue_refresh.isRefreshing)
                    repos_issue_refresh.isRefreshing = false
            }
        })

        mViewModel?.issueUiModel?.observe(viewLifecycleOwner, Observer {
            mIssueAdapter.addItemData(it)
            repos_issue_recycler.layoutManager?.scrollToPosition(0)
        })
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                reloadData()
            }
        }

        mIssueAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->

        }

        mIssueAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel?.loadData(isLoad = false, page = mPageCode)
        }

        repos_issue_create_btn.setOnClickListener {
            mActivity.showIssueEditDialog(
                R.string.issue.string(), true,
                "", "", this
            )
        }

        repos_issue_refresh.setOnRefreshListener {
            reloadData()
        }
    }

    override fun reloadData() {
        loadData()
    }

    override fun loadData() {
        repos_issue_refresh.isRefreshing = true
        mPageCode = 1
        mViewModel?.loadData(isLoad = true, page = mPageCode)
    }

    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {
    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
        repos_issue_navigation_tab_bar.isTouchEnable = false
        mViewModel?.status = statusList[index]
        reloadData()
    }

    override fun onConfirm(
        dialog: Dialog,
        title: String,
        editTitle: String?,
        editContent: String?
    ) {
        val issue: Issue = Issue().apply {
            this.title = editTitle
            this.body = editContent
        }
        mViewModel?.toCreateIssue(issue)
        dialog.dismiss()
    }
}