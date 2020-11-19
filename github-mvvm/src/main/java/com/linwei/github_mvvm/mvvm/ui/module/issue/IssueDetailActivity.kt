package com.linwei.github_mvvm.mvvm.ui.module.issue

import android.content.Context
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.linwei.cams_mvvm.base.BaseMvvmActivityWithTop
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import org.jetbrains.anko.startActivity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/18
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class IssueDetailActivity : BaseMvvmActivityWithTop<BaseViewModel, ViewDataBinding>() {

    companion object {
        @JvmStatic
        fun start(context: Context, userName: String, reposName: String, issueNumber: Int) {
            context.startActivity<IssueDetailActivity>()
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun fetchTopBarTitleStr(): String=""

    override fun provideContentViewId(): Int = R.layout.activity_issue_detail

    override fun bindViewModel() {

    }

    override fun initLayoutView(savedInstanceState: Bundle?) {

    }

    override fun initLayoutData() {

    }

    override fun initLayoutListener() {

    }
}