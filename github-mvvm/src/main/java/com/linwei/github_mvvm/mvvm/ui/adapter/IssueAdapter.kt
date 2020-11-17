package com.linwei.github_mvvm.mvvm.ui.adapter

import android.animation.Animator
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.LayoutFileItemBinding
import com.linwei.github_mvvm.databinding.LayoutIssueItemBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.model.ui.FileUIModel
import com.linwei.github_mvvm.mvvm.model.ui.IssueUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/16
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class IssueAdapter(
     data: MutableList<IssueUIModel>
) : BaseQuickAdapter<IssueUIModel, BaseViewHolder>(R.layout.layout_issue_item, data),
    LoadMoreModule {

    companion object {
        private const val COUNT: Int = 10
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<LayoutIssueItemBinding>(
            viewHolder.itemView,
            GithubDataBindingComponent()
        )
    }

    override fun convert(holder: BaseViewHolder, item: IssueUIModel) {
        val binding: LayoutIssueItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        if (binding != null) {
            binding.issueUIModel = item
            binding.executePendingBindings()
        }
    }

    /**
     * 增加`Item`数据
     * @param issueUIModel [IssueUIModel]
     */
    fun addItemData(issueUIModel: IssueUIModel){
        data.add(0, issueUIModel)
        notifyDataSetChanged()
    }

    override fun startAnim(anim: Animator, index: Int) {
        super.startAnim(anim, index)
        if (index < COUNT)
            anim.startDelay = (index * 150).toLong()
    }
}