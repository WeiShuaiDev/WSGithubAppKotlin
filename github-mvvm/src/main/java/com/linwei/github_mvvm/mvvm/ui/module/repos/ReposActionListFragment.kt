package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.nukc.stateview.StateView
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentReposActionListBinding
import com.linwei.github_mvvm.databinding.LayoutReposHeaderBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.contract.event.issue.IssueDetailContract
import com.linwei.github_mvvm.mvvm.model.conversion.ReposConversion
import com.linwei.github_mvvm.mvvm.ui.adapter.CommitInfoAdapter
import com.linwei.github_mvvm.mvvm.ui.adapter.EventInfoAdapter
import com.linwei.github_mvvm.mvvm.viewmodel.ConversionBean
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposActionViewModel
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.fragment_repos_action_list.*
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
class ReposActionListFragment(val userName: String?, val reposName: String?) :
    BaseMvvmFragment<ReposActionViewModel, FragmentReposActionListBinding>(),
    IssueDetailContract.View, NavigationTabBar.OnTabBarSelectedIndexListener {

    @Inject
    lateinit var actionTabModel: MutableList<NavigationTabBar.Model>

    private lateinit var mCommitInfoAdapter: CommitInfoAdapter

    private lateinit var mLayoutReposHeaderBinding: LayoutReposHeaderBinding

    private var mPageCode: Int = 1

    override fun provideContentViewId(): Int = R.layout.fragment_repos_action_list

    override fun obtainStateViewRoot(): View = repos_action_list_root

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
        val headerView:View= reposHeaderBinding()

        mCommitInfoAdapter = CommitInfoAdapter(mutableListOf())
        mCommitInfoAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mCommitInfoAdapter.loadMoreModule.isAutoLoadMore = true   //自动加载
        mCommitInfoAdapter.addHeaderView(headerView)
    }

    private fun reposHeaderBinding(): View {
        mLayoutReposHeaderBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.layout_repos_header,
            null, false, GithubDataBindingComponent()
        )
        mLayoutReposHeaderBinding.actionViewModel = mViewModel

        mLayoutReposHeaderBinding.reposActionTabBar.models = actionTabModel

        mLayoutReposHeaderBinding.reposActionTabBar.onTabBarSelectedIndexListener = this
        mLayoutReposHeaderBinding.reposActionTabBar.modelIndex = 0
        return mLayoutReposHeaderBinding.root
    }

    override fun initLayoutData() {
        mViewModel?.reposInfo?.observe(viewLifecycleOwner, Observer {
            it?.let {
                mLayoutReposHeaderBinding.reposUIModel = ReposConversion.reposToReposUIModel(it)
            }
        })

        mViewModel?.repoCommitPage?.observe(viewLifecycleOwner, Observer {
            mLayoutReposHeaderBinding.reposActionTabBar.isTouchEnable = true
            it?.let {
                mPageCode = it.next
                it.result.isNotNullOrSize().yes {
                    if (it.prev == -1) {
                        mCommitInfoAdapter.setNewInstance(
                            ConversionBean.repoCommitConversionByCommitUIModel(it)
                        )
                    } else {
                        mCommitInfoAdapter.addData(
                            ConversionBean.repoCommitConversionByCommitUIModel(it)
                        )
                    }

                    (it.next == it.last).yes {
                        mCommitInfoAdapter.loadMoreModule.loadMoreEnd()
                    }.otherwise {
                        mCommitInfoAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                if (repos_action_refresh.isRefreshing)
                    repos_action_refresh.isRefreshing = false
            }
        })

        mViewModel?.eventPage?.observe(viewLifecycleOwner, Observer {
            mLayoutReposHeaderBinding.reposActionTabBar.isTouchEnable = true
            it?.let {
                mPageCode = it.next
                it.result.isNotNullOrSize().yes {
//                    if (it.prev == -1) {
//                        mEventInfoAdapter.setNewInstance(
//                            ConversionBean.eventConversionByEventUIModel(it)
//                        )
//                    } else {
//                        mEventInfoAdapter.addData(ConversionBean.eventConversionByEventUIModel(it))
//                    }
//
//                    (it.next == it.last).yes {
//                        mEventInfoAdapter.loadMoreModule.loadMoreEnd()
//                    }.otherwise {
//                        mEventInfoAdapter.loadMoreModule.loadMoreComplete()
//                    }
                }
                if (repos_action_refresh.isRefreshing)
                    repos_action_refresh.isRefreshing = false
            }
        })
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                loadData()
            }
        }

        mCommitInfoAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel?.loadDataByLoadMore(mPageCode)
        }

        repos_action_refresh.setOnRefreshListener {
            loadData()
        }
    }

    override fun reloadData() {
        repos_action_refresh.isRefreshing = true

        mPageCode = 1
        mViewModel?.loadDataByRefresh()
        mViewModel?.loadDataByLoadMore(mPageCode)
    }

    override fun loadData() {
        repos_action_refresh.isRefreshing = true
        mPageCode = 1
        mViewModel?.loadDataByLoadMore(mPageCode)
    }

    override fun onEndTabSelected(model: NavigationTabBar.Model?, index: Int) {

    }

    override fun onStartTabSelected(model: NavigationTabBar.Model?, index: Int) {
        mLayoutReposHeaderBinding.reposActionTabBar.isTouchEnable = false
        mViewModel?.showType=index
        loadData()
    }
}