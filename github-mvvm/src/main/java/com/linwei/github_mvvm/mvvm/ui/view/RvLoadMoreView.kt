package com.linwei.github_mvvm.mvvm.ui.view

import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.github_mvvm.R

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RvLoadMoreView : BaseLoadMoreView() {
    override fun getLoadComplete(holder: BaseViewHolder): View {
        // 布局中 “当前一页加载完成”的View
        return holder.getView(R.id.load_more_load_complete_view)
    }

    override fun getLoadEndView(holder: BaseViewHolder): View {
        // 布局中 “全部加载结束，没有数据”的View
        return holder.getView(R.id.load_more_load_end_view)
    }

    override fun getLoadFailView(holder: BaseViewHolder): View {
        // 布局中 “加载失败”的View
        return holder.getView(R.id.load_more_load_fail_view)
    }

    override fun getLoadingView(holder: BaseViewHolder): View {
        // 布局中 “加载中”的View
        return holder.getView(R.id.load_more_loading_view)
    }

    override fun getRootView(parent: ViewGroup): View {
        // 整个 LoadMore 布局
        return parent.getItemView(R.layout.layout_item_load_more)
    }
}