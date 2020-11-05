package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.view.View
import androidx.lifecycle.Observer
import com.github.nukc.stateview.StateView
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentReposReadmeBinding
import com.linwei.github_mvvm.mvvm.contract.event.issue.IssueDetailContract
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposReadmeViewModel
import kotlinx.android.synthetic.main.fragment_repos_readme.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposReadmeFragment(val userName: String?, val reposName: String?) :
    BaseMvvmFragment<ReposReadmeViewModel, FragmentReposReadmeBinding>(),
    IssueDetailContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_repos_readme

    override fun obtainStateViewRoot(): View = repos_readme_root

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner

        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {
        repos_readme_web.webView.requestIntercept = false
        repos_readme_web.webView.settings.defaultTextEncodingName = "UTF-8"//设置默认为utf-8
    }

    override fun initLayoutData() {
        mViewModel?.readmeUrl?.observe(viewLifecycleOwner, Observer {
            repos_readme_web.webView.loadData(it, "text/html; charset=UTF-8", null)
        })
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                mViewModel?.toReposReadme(userName, reposName)
            }
        }
    }

    override fun reloadData() {
        mViewModel?.toReposReadme(userName, reposName)
    }

    override fun loadData() {
        mViewModel?.toReposReadme(userName, reposName)
    }
}