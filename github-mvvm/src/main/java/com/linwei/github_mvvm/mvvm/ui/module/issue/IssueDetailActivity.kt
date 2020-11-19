package com.linwei.github_mvvm.mvvm.ui.module.issue

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.databinding.ViewDataBinding
import com.linwei.cams.ext.isEmptyParameter
import com.linwei.cams.ext.yes
import com.linwei.cams_mvvm.base.BaseMvvmActivity
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.ext.copy
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposDetailActivity
import kotlinx.android.synthetic.main.activity_issue_detail.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.share
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
class IssueDetailActivity : BaseMvvmActivity<BaseViewModel, ViewDataBinding>(),
    Toolbar.OnMenuItemClickListener, PopupMenu.OnMenuItemClickListener {

    private var mUserName: String? = null
    private var mReposName: String? = null
    private var mIssueNumber: Int = 0

    companion object {
        private const val USER_NAME: String = "USER_NAME"
        private const val REPOS_NAME: String = "REPOS_NAME"
        private const val ISSUE_NUMBER: String = "ISSUE_NUMBER"

        @JvmStatic
        fun start(context: Context, userName: String, reposName: String, issueNumber: Int) {
            context.startActivity<IssueDetailActivity>(
                USER_NAME to userName, REPOS_NAME to reposName, ISSUE_NUMBER to issueNumber
            )
        }
    }

    override fun setUpOnCreateAndSuperEnd(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperEnd(savedInstanceState)

        mUserName = intent.getStringExtra(USER_NAME)
        mReposName = intent.getStringExtra(REPOS_NAME)
        mIssueNumber = intent.getIntExtra(ISSUE_NUMBER, 0)
    }

    override fun useDataBinding(): Boolean = false

    override fun provideContentViewId(): Int = R.layout.activity_issue_detail

    override fun bindViewModel() {

    }

    override fun initLayoutView(savedInstanceState: Bundle?) {
        setSupportActionBar(activity_issue_detail_toolbar)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
        }
        activity_issue_detail_toolbar.title = "$mUserName/$mReposName"
        activity_issue_detail_toolbar.setOnMenuItemClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun initLayoutData() {

    }

    override fun initLayoutListener() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_home -> {
                ReposDetailActivity.start(mContext, mUserName, mReposName)
            }

            R.id.action_more -> {
                val pop = PopupMenu(mContext, activity_issue_detail_toolbar)
                pop.menuInflater.inflate(R.menu.toolbar_default_pop_menu, pop.menu)
                pop.gravity = Gravity.END
                pop.show()
                pop.setOnMenuItemClickListener(this)
            }

            R.id.action_browser -> {
                (!isEmptyParameter(mUserName, mReposName)).yes {
                    mContext.browse(
                        Api.getIssueHtmlUrl(
                            mUserName!!,
                            mReposName!!,
                            mIssueNumber.toString()
                        )
                    )
                }
            }

            R.id.action_copy -> {
                (!isEmptyParameter(mUserName, mReposName)).yes {
                    mContext.copy(
                        Api.getIssueHtmlUrl(
                            mUserName!!,
                            mReposName!!,
                            mIssueNumber.toString()
                        )
                    )
                }
            }

            R.id.action_share -> {
                (!isEmptyParameter(mUserName, mReposName)).yes {
                    mContext.share(
                        Api.getIssueHtmlUrl(
                            mUserName!!,
                            mReposName!!, mIssueNumber.toString()
                        )
                    )
                }
            }
        }
        return true
    }
}