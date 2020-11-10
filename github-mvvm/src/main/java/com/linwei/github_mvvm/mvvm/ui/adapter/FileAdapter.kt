package com.linwei.github_mvvm.mvvm.ui.adapter
import android.animation.Animator
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.LayoutFileItemBinding
import com.linwei.github_mvvm.ext.GithubDataBindingComponent
import com.linwei.github_mvvm.mvvm.model.ui.FileUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/10
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class FileAdapter(
    data: MutableList<FileUIModel>
) : BaseQuickAdapter<FileUIModel, BaseViewHolder>(R.layout.layout_file_item, data),
    LoadMoreModule {

    companion object {
        private const val COUNT: Int = 10
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<LayoutFileItemBinding>(
            viewHolder.itemView,
            GithubDataBindingComponent()
        )
    }

    override fun convert(holder: BaseViewHolder, item:FileUIModel) {
        val binding: LayoutFileItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        if (binding != null) {
            binding.fileUIModel = item
            binding.executePendingBindings()
        }
    }

    override fun startAnim(anim: Animator, index: Int) {
        super.startAnim(anim, index)
        if (index < COUNT)
            anim.startDelay = (index * 150).toLong()
    }
}