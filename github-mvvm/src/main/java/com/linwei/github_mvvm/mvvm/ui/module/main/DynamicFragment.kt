package com.linwei.github_mvvm.mvvm.ui.module.main

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.nukc.stateview.StateView
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentDynamicBinding
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.ui.adapter.ReceivedEventAdapter
import com.linwei.github_mvvm.mvvm.viewmodel.main.DynamicViewModel
import kotlinx.android.synthetic.main.fragment_dynamic.*
import timber.log.Timber

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class DynamicFragment : BaseMvvmFragment<DynamicViewModel, FragmentDynamicBinding>(),
    DynamicContract.View {

    private lateinit var mReceivedEventAdapter: ReceivedEventAdapter

    override fun obtainStateViewRoot(): View? = mContentRoot

    override fun provideContentViewId(): Int = R.layout.fragment_dynamic

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {
        initReceivedEventRV()
    }

    override fun initLayoutData() {
        mViewModel?.eventUiModel?.observe(viewLifecycleOwner, Observer {
            it.isNotNullOrSize().yes {
                mReceivedEventAdapter.setNewInstance(it.toMutableList())
            }
        })
    }

    /**
     * 初始化接收事件列表适配器
     */
    private fun initReceivedEventRV() {
        mReceivedEventAdapter = ReceivedEventAdapter(mutableListOf())
        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mReceivedEventAdapter
        }
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                mViewModel?.toReceivedEvent(1)
            }
        }

        mReceivedEventAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            Timber.i("ReceivedEvent position${position}")
        }
    }

    override fun reloadData() {
        mViewModel?.toReceivedEvent(1)
    }

    override fun loadData() {
        mViewModel?.toReceivedEvent(1)
    }
}
