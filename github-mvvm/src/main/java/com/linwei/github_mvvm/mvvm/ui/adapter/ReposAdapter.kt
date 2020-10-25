package com.linwei.github_mvvm.mvvm.ui.adapter

import android.animation.Animator
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.LayoutReposItemBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.model.ui.ReposUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/13
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposAdapter(data: MutableList<ReposUIModel>) :
    BaseQuickAdapter<ReposUIModel, BaseViewHolder>(R.layout.layout_repos_item, data) {

    companion object {
        private const val COUNT: Int = 10
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<LayoutReposItemBinding>(
            viewHolder.itemView,
            GithubDataBindingComponent()
        )
    }

    override fun convert(holder: BaseViewHolder, item: ReposUIModel) {
        val binding: LayoutReposItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        if (binding != null) {
            binding.reposUIModel = item
            binding.executePendingBindings()
        }
    }

    override fun startAnim(anim: Animator, index: Int) {
        super.startAnim(anim, index)
        if (index < COUNT)
            anim.startDelay = (index * 150).toLong()
    }
}