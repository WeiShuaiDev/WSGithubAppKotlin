package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.nukc.stateview.StateView
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.ext.isImageEnd
import com.linwei.github_mvvm.ext.launchUrl
import com.linwei.github_mvvm.ext.toSplitString
import com.linwei.github_mvvm.mvvm.contract.repos.ReposFileListContract
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.ui.FileUIModel
import com.linwei.github_mvvm.mvvm.ui.adapter.FileAdapter
import com.linwei.github_mvvm.mvvm.ui.view.HorizontalTextList
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposFileListViewModel
import kotlinx.android.synthetic.main.fragment_repos_file_list.*

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

    private lateinit var mFileAdapter: FileAdapter

    override fun provideContentViewId(): Int = R.layout.fragment_repos_file_list

    override fun obtainStateViewRoot(): View? = repos_file_list_root

    override fun useDataBinding(): Boolean = false

    override fun bindViewModel() {
        mViewModel?.let {
            it.mLifecycleOwner = viewLifecycleOwner
            it.userName = userName
            it.reposName = reposName
        }
    }

    override fun initLayoutView(rootView: View?) {
        mFileAdapter = FileAdapter(mutableListOf())
        mFileAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mFileAdapter.loadMoreModule.isAutoLoadMore = true   //自动加载
        repos_file_recycler.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = mFileAdapter
        }
    }

    override fun initLayoutData() {
        mViewModel?.fileUIModel?.observe(viewLifecycleOwner, Observer {
            it?.let {
                mFileAdapter.setNewInstance(it.toMutableList())
            }
        })
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                mViewModel?.toFiles()
            }
        }

        mFileAdapter.setOnItemClickListener { adapter: BaseQuickAdapter<*, *>, view: View, position: Int ->
            adapter.data[position]?.let {
                val itemData: FileUIModel = it as FileUIModel
                (itemData.type == "file").yes {
                    val isImageEnd: Boolean = itemData.title.isImageEnd()
                    isImageEnd.yes {
                        if (!isEmptyParameter(userName, reposName)) {
                            val path: String =
                                repos_file_select_header.mDataList.toSplitString() + "/" + itemData.title
                            val url: String =
                                Api.getFileHtmlUrl(userName!!, reposName!!, path) + "?raw=true"
                            launchUrl(mActivity, url)
                        }
                    }.otherwise {

                    }
                }.otherwise {
                    addSelectList(itemData.title)
                    mViewModel?.path = repos_file_select_header.mDataList.toSplitString()
                    mViewModel?.toFiles()
                }
            }
        }

        repos_file_select_header.mItemClick = object :
            HorizontalTextList.OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                (position == 0).yes {
                    clearSelectList()
                    mViewModel?.path = ""
                }.otherwise {
                    notifySelectList(position)
                    mViewModel?.path = repos_file_select_header.mDataList.toSplitString()
                }
                mViewModel?.toFiles()
            }
        }
    }

    /**
     * 清空 `HorizontalTextList` 数据。
     */
    private fun clearSelectList() {
        repos_file_select_header.mDataList.clear()
        repos_file_select_header.mDataList.add(".")
        repos_file_select_header.mRecycler.adapter?.notifyDataSetChanged()
    }

    /**
     * @param item [String]
     * 增加 `HorizontalTextList` [item] 标签数据。
     */
    private fun addSelectList(item: String) {
        repos_file_select_header.mDataList.add(item)
        repos_file_select_header.mRecycler.adapter?.notifyDataSetChanged()
    }

    /**
     * @param position [Int]
     * 选择 `HorizontalTextList` [item] 标签数据，并刷新列表数据。
     */
    private fun notifySelectList(position: Int) {
        val nextList: ArrayList<String> = arrayListOf()
        val result: MutableList<String> =
            repos_file_select_header.mDataList.subList(0, position + 1)
        nextList.addAll(result)
        repos_file_select_header.mDataList.clear()
        repos_file_select_header.mDataList.addAll(nextList)
        repos_file_select_header.mRecycler.adapter?.notifyDataSetChanged()
    }

    override fun reloadData() {
        mViewModel?.toFiles()
    }

    override fun loadData() {
        mViewModel?.toFiles()
    }
}