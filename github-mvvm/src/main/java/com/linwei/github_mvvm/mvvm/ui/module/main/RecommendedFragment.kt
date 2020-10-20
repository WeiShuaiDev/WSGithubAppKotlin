package com.linwei.github_mvvm.mvvm.ui.module.main

import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentRecommendedBinding
import com.linwei.github_mvvm.mvvm.contract.main.RecommendedContract
import com.linwei.github_mvvm.mvvm.ui.adapter.ListDropDownAdapter
import com.linwei.github_mvvm.mvvm.ui.adapter.ReposAdapter
import com.linwei.github_mvvm.mvvm.viewmodel.main.RecommendedViewModel
import kotlinx.android.synthetic.main.fragment_dynamic.*
import kotlinx.android.synthetic.main.fragment_recommended.*
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
class RecommendedFragment : BaseMvvmFragment<RecommendedViewModel, FragmentRecommendedBinding>(),
    RecommendedContract.View {

    private lateinit var mReposAdapter: ReposAdapter

    private lateinit var mRecyclerview: RecyclerView

    private lateinit var mSwipeLayout: SwipeRefreshLayout

    override fun provideContentViewId(): Int = R.layout.fragment_recommended

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {
        initReceivedEventRV()
        initDropDownMenu()
    }

    /**
     * 初始化接收事件列表适配器
     */
    private fun initReceivedEventRV() {
        mSwipeLayout = SwipeRefreshLayout(mContext)
        mRecyclerview = RecyclerView(mContext)

        mSwipeLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        mSwipeLayout.addView(
            mRecyclerview,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        mReposAdapter = ReposAdapter(mutableListOf())
        mRecyclerview.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mReposAdapter
        }
    }

    /**
     * 初始化 DropDownMenu
     */
    private fun initDropDownMenu() {
        val sortData: List<List<String>>? = mViewModel?.sortData

        val sortValue: List<List<String>>? = mViewModel?.sortValue

        val sortType: ArrayList<String>? = mViewModel?.sortType

        sortData?.let {
            val dropMap = HashMap<String, View>()

            for (i: Int in it.indices) {
                val dropList = ListView(mContext)
                dropList.dividerHeight = 0
                dropList.adapter = ListDropDownAdapter(mContext, it[i])
                dropMap[it[i][0]] = dropList

                dropList.setOnItemClickListener { view, _, p, _ ->
                    (view.adapter as ListDropDownAdapter).setCheckItem(p)

                    mRecommendedDropMenu.setTabText(it[i][p])
                    mRecommendedDropMenu.closeMenu()

                    sortValue?.let {
                        sortType?.let {
                            it[i] = sortValue[i][p]
                            reloadData()
                        }
                    }
                }
            }
            mRecommendedDropMenu.setDropDownMenu(
                dropMap.keys.toList(),
                dropMap.values.toList(),
                mSwipeLayout
            )
        }
    }

    override fun initLayoutData() {
        mViewModel?.reposUIModel?.observe(viewLifecycleOwner, Observer {
            it?.let {
                it.isNotNullOrSize().yes {
                    mReposAdapter.setNewInstance(it.toMutableList())
                }

                if (mSwipeLayout.isRefreshing)
                    mSwipeLayout.isRefreshing = false
            }
        })

    }

    override fun initLayoutListener() {
        mReposAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            Timber.i("Trend position${position}")
        }

        mSwipeLayout.setOnRefreshListener {
            mViewModel?.toTrendData()
        }
    }

    override fun reloadData() {
        mSwipeLayout.isRefreshing = true
        mViewModel?.toTrendData()
    }

    override fun loadData() {
        mSwipeLayout.isRefreshing = true
        mViewModel?.toTrendData()
    }
}