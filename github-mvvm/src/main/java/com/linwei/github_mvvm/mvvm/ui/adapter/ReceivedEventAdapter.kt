package com.linwei.github_mvvm.mvvm.ui.adapter

import android.animation.Animator
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.LayoutEventItemBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.model.data.EventUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/13
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Fragment的ViewPager适配器
 *-----------------------------------------------------------------------
 */
class ReceivedEventAdapter(data: MutableList<EventUIModel>) :
    BaseQuickAdapter<EventUIModel, BaseViewHolder>(R.layout.layout_event_item, data) {

    companion object {
        private const val COUNT: Int = 10
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<LayoutEventItemBinding>(
            viewHolder.itemView,
            GithubDataBindingComponent()
        )
    }

    override fun convert(holder: BaseViewHolder, item: EventUIModel) {
        val binding: LayoutEventItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        if (binding != null) {
            binding.eventUIModel = item
            binding.executePendingBindings();
        }
    }

    override fun startAnim(anim: Animator, index: Int) {
        super.startAnim(anim, index)
        if (index < COUNT)
            anim.startDelay = (index * 150).toLong();
    }
}