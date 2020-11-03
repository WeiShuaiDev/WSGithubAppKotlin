package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposDetailContract
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposDetailViewModel
import dagger.android.AndroidInjection
import org.jetbrains.anko.startActivity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposDetailActivity : BaseMvvmActivity<ReposDetailViewModel, ViewDataBinding>(),
    ReposDetailContract.View {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startActivity<ReposDetailActivity>()
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun provideContentViewId(): Int = R.layout.activity_repos_detail

    override fun initLayoutView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initLayoutData() {
        TODO("Not yet implemented")
    }

    override fun initLayoutListener() {
        TODO("Not yet implemented")
    }

    override fun bindViewModel() {
        TODO("Not yet implemented")
    }
}