package com.linwei.github_mvvm.mvvm.ui.module.main

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.nukc.stateview.StateView
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentMineBinding
import com.linwei.github_mvvm.databinding.LayoutUserHeaderBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.ui.adapter.UserInfoAdapter
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

    private lateinit var mUserInfoAdapter: UserInfoAdapter

    private lateinit var mLayoutUserHeaderBinding: LayoutUserHeaderBinding

    override fun provideContentViewId(): Int = R.layout.fragment_mine

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.mineViewModel = mViewModel
            it.globalModel = mAppGlobalModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {
        initUserInfoRV()
    }

    /**
     * 初始化用户信息列表适配器
     */
    private fun initUserInfoRV() {
        mUserInfoAdapter = UserInfoAdapter(mutableListOf())
        mUserInfoAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mUserInfoAdapter.loadMoreModule.isAutoLoadMore = true   //自动加载

        //RecyclerView add header view
        mLayoutUserHeaderBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.layout_user_header,
            null, false, GithubDataBindingComponent()
        )
        mLayoutUserHeaderBinding.userUIModel = mAppGlobalModel.userObservable
        mLayoutUserHeaderBinding.mineViewModel = mViewModel
        mUserInfoAdapter.addHeaderView(mLayoutUserHeaderBinding.root)

        mine_recycler.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mUserInfoAdapter
        }
    }

    override fun initLayoutData() {
        mViewModel?.notifyColor?.observe(viewLifecycleOwner, Observer {
            mLayoutUserHeaderBinding.root.mine_header_notify.setTextColor(it)
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

        mUserInfoAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            Timber.i("UserInfo position${position}")
        }

        mUserInfoAdapter.loadMoreModule.setOnLoadMoreListener {

        }

        mine_swipe_refresh.setOnRefreshListener {
            mUserInfoAdapter.loadMoreModule.isEnableLoadMore = false
        }
    }

    override fun reloadData() {
        mine_swipe_refresh.isRefreshing = true
    }

    override fun loadData() {
        mine_swipe_refresh.isRefreshing = true
    }
}