package com.linwei.github_mvvm.mvvm.ui.adapter

import android.animation.Animator
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.LayoutEventItemBinding
import com.linwei.github_mvvm.databinding.LayoutUserItemBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.model.ui.UserUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/13
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class UserInfoAdapter(data: MutableList<UserUIModel>) :
    BaseQuickAdapter<UserUIModel, BaseViewHolder>(R.layout.layout_user_item, data),
    LoadMoreModule {

    companion object {
        private const val COUNT: Int = 10
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<LayoutUserItemBinding>(
            viewHolder.itemView,
            GithubDataBindingComponent()
        )
    }

    override fun convert(holder: BaseViewHolder, item: UserUIModel) {
        val binding: LayoutUserItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        if (binding != null) {
            binding.userUIModel = item
            binding.executePendingBindings()
        }
    }

    override fun startAnim(anim: Animator, index: Int) {
        super.startAnim(anim, index)
        if (index < COUNT)
            anim.startDelay = (index * 150).toLong()
    }
}