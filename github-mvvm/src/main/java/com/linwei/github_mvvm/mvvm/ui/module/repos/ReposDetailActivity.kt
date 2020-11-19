package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.content.Context
import android.os.Bundle
import android.widget.AdapterView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.linwei.cams_mvvm.base.BaseMvvmActivityWithTop
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposDetailContract
import com.linwei.github_mvvm.mvvm.ui.adapter.FragmentPagerViewAdapter
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposDetailViewModel
import dagger.android.AndroidInjection
import devlight.io.library.ntb.NavigationTabBar
import kotlinx.android.synthetic.main.activity_repos_detail.*
import org.jetbrains.anko.startActivity
import java.util.Locale.ROOT
import javax.inject.Inject
import kotlin.collections.ArrayList

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposDetailActivity : BaseMvvmActivityWithTop<ReposDetailViewModel, ViewDataBinding>(),
    ReposDetailContract.View {

    @Inject
    lateinit var reposTabModel: MutableList<NavigationTabBar.Model>

    private var mReposName: String? = null
    private var mUserName: String? = null

    companion object {
        private const val USER_NAME: String = "USER_NAME"
        private const val REPOS_NAME: String = "REPOS_NAME"

        @JvmStatic
        fun start(context: Context?, userName: String?, reposName: String?) {
            context?.startActivity<ReposDetailActivity>(
                USER_NAME to userName, REPOS_NAME to reposName
            )
        }
    }

    override fun useDataBinding(): Boolean = false

    override fun setUpOnCreateAndSuperStart(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperStart(savedInstanceState)
        AndroidInjection.inject(this)
    }

    override fun setUpOnCreateAndSuperEnd(savedInstanceState: Bundle?) {
        super.setUpOnCreateAndSuperEnd(savedInstanceState)
        mUserName = intent.getStringExtra(USER_NAME)
        mReposName = intent.getStringExtra(REPOS_NAME)

    }

    override fun provideContentViewId(): Int = R.layout.activity_repos_detail

    override fun provideTopBarId(): Int = R.layout.include_top_view_primary

    override fun fetchTopBarTitleId(): Int = R.string.repos_readme

    override fun initLayoutView(savedInstanceState: Bundle?) {
        initControlBar()
        initReposDetailVP()
    }

    override fun initLayoutData() {
        mViewModel?.starredStatus?.observe(this, Observer {
            initControlBar()
        })

        mViewModel?.watchedStatus?.observe(this, Observer {
            initControlBar()
        })

        mViewModel?.toReposStatus(mUserName, mReposName)
    }

    override fun initLayoutListener() {

    }

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = this
    }

    /**
     * 初始化 `ViewPage` 配置信息。
     */
    private fun initReposDetailVP() {
        val fragmentList: ArrayList<Fragment> = getFragmentList()
        repos_detail_view_pager.adapter =
            FragmentPagerViewAdapter(fragmentList, supportFragmentManager)
        repos_detail_tab_bar.models = reposTabModel
        repos_detail_tab_bar.setViewPager(repos_detail_view_pager, 0)
        repos_detail_view_pager.offscreenPageLimit = fragmentList.size
    }

    /**
     * 创建 `ViewPage` 中 `Fragment`对象
     * [ReposReadmeFragment]、[ReposActionListFragment]、[ReposFileListFragment]、[ReposIssueListFragment]
     */
    private fun getFragmentList(): ArrayList<Fragment> {
        return arrayListOf(
            ReposReadmeFragment(mUserName, mReposName),
            ReposActionListFragment(mUserName, mReposName),
            ReposFileListFragment(mUserName, mReposName),
            ReposIssueListFragment(mUserName, mReposName)
        )
    }

    /**
     * 初始化底部仓库状态控制器
     */
    private fun initControlBar() {
        val dataList: ArrayList<String> = getControlList()
        repos_detail_control_bar.list.clear()
        repos_detail_control_bar.list.addAll(dataList)
        repos_detail_control_bar.listView.adapter?.notifyDataSetChanged()
        repos_detail_control_bar.itemClick = AdapterView.OnItemClickListener { _, _, position, _ ->
            val item: String = repos_detail_control_bar.list[position]
            when {
                item.toLowerCase(ROOT).contains("star") -> {
                    mViewModel?.toChangeStarStatus(mUserName, mReposName)
                }
                item.toLowerCase(ROOT).contains("watch") -> {
                    mViewModel?.toChangeWatchStatus(mUserName, mReposName)
                }
                item.contains("fork") -> {
                    mViewModel?.toForkRepository(mUserName, mReposName)
                }
            }
        }
    }

    /**
     * 获取底部仓库状态数据
     * @return [List]
     */
    private fun getControlList(): ArrayList<String> {
        val controlList: ArrayList<String> = arrayListOf()
        val starStatus: Boolean? = mViewModel?.starredStatus?.value
        val watchStatus: Boolean? = mViewModel?.watchedStatus?.value
        if (starStatus != null) {
            val star: String =
                if (starStatus) "{APP_REPOS_ITEM_STARED} unStar" else "{APP_REPOS_ITEM_STAR} star"
            controlList.add(star)
        }

        if (watchStatus != null) {
            val watch: String =
                if (watchStatus) "{APP_REPOS_ITEM_WATCHED} unWatch" else "{APP_REPOS_ITEM_WATCH} watch"
            controlList.add(watch)
        }

        if (starStatus != null && watchStatus != null) {
            controlList.add("{APP_REPOS_ITEM_FORK} fork")
        }
        return controlList
    }
}