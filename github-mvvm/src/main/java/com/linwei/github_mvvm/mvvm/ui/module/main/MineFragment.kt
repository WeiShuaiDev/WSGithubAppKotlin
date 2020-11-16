package com.linwei.github_mvvm.mvvm.ui.module.main

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
import com.linwei.github_mvvm.databinding.FragmentMineBinding
import com.linwei.github_mvvm.databinding.LayoutUserHeaderBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.mvvm.ui.adapter.EventInfoAdapter
import com.linwei.github_mvvm.mvvm.viewmodel.ConversionBean
import com.linwei.github_mvvm.mvvm.viewmodel.main.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_user_header.view.*
import timber.log.Timber
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MineFragment : BaseMvvmFragment<MineViewModel, FragmentMineBinding>(), MineContract.View {

    @Inject
    lateinit var mAppGlobalModel: AppGlobalModel

    private lateinit var mEventInfoAdapter: EventInfoAdapter

    private lateinit var mLayoutUserHeaderBinding: LayoutUserHeaderBinding

    override fun provideContentViewId(): Int = R.layout.fragment_mine

    private var mPage: Page<List<Event>>? = null

    private var mPageCode: Int = 1

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner

        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.globalModel = mAppGlobalModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {
        mEventInfoAdapter = EventInfoAdapter(mutableListOf())

        mEventInfoAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mEventInfoAdapter.loadMoreModule.isAutoLoadMore = true   //自动加载

        //RecyclerView add header view
        mLayoutUserHeaderBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.layout_user_header,
            null, false, GithubDataBindingComponent()
        )
        mLayoutUserHeaderBinding.userUIModel = mAppGlobalModel.userObservable
        mLayoutUserHeaderBinding.mineViewModel = mViewModel
        mEventInfoAdapter.addHeaderView(mLayoutUserHeaderBinding.root)

        mine_recycler.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mEventInfoAdapter
        }
    }

    override fun initLayoutData() {
        mViewModel?.notifyColor?.observe(viewLifecycleOwner, Observer {
            mLayoutUserHeaderBinding.root.mine_header_notify.setTextColor(it)
        })

        mViewModel?.pageByOrgMember?.observe(viewLifecycleOwner, Observer {

        })

        mViewModel?.pageByUserEvents?.observe(viewLifecycleOwner, Observer {
            it?.let {
                mPage = it
                mPageCode = it.next
                it.result.isNotNullOrSize().yes {

                    if (it.prev == -1) {
                        mEventInfoAdapter.setNewInstance(
                            ConversionBean.eventConversionByEventUIModel(it)
                        )
                    } else {
                        mEventInfoAdapter.addData(ConversionBean.eventConversionByEventUIModel(it))
                    }

                    (it.next == it.last).yes {
                        mEventInfoAdapter.loadMoreModule.loadMoreEnd()
                    }.otherwise {
                        mEventInfoAdapter.loadMoreModule.loadMoreComplete()
                    }
                }
                if (mine_swipe_refresh.isRefreshing)
                    mine_swipe_refresh.isRefreshing = false
            }
        })
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {

            }
        }

        //用户通知事件信息事件
        mLayoutUserHeaderBinding.root.mine_header_notify.setOnClickListener {

        }

        mEventInfoAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            Timber.i("UserInfo position${position}")
        }

        mEventInfoAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel?.loadDataByLoadMore(mPageCode)
        }

        mine_swipe_refresh.setOnRefreshListener {
            mEventInfoAdapter.loadMoreModule.isEnableLoadMore = false
            mPageCode = 1
            mViewModel?.loadDataByLoadMore(mPageCode)
        }
    }

    override fun loadData() {
        mine_swipe_refresh.isRefreshing = true
        mPageCode = 1
        mViewModel?.loadDataByLoadMore(mPageCode)
    }

    override fun reloadData() {
        mine_swipe_refresh.isRefreshing = true
        mViewModel?.loadDataByLoadMore(mPageCode)
    }
}