package com.linwei.github_mvvm.mvvm.ui.module.repos

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.github.nukc.stateview.StateView
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.repos.ReposFileListContract
import com.linwei.github_mvvm.mvvm.viewmodel.repos.ReposFileListViewModel
import kotlinx.android.synthetic.main.fragment_repos_file_list.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class ReposFileListFragment(val userName: String?, val reposName: String?) :
    BaseMvvmFragment<ReposFileListViewModel, ViewDataBinding>(),
    ReposFileListContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_repos_file_list

    override fun obtainStateViewRoot(): View? = repos_file_list_root

    override fun useDataBinding(): Boolean = false

    override fun bindViewModel() {
        mViewModel?.mLifecycleOwner = viewLifecycleOwner
    }

    override fun initLayoutView(rootView: View?) {
    }

    override fun initLayoutData() {
        mViewModel?.fileUIModel?.observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
    }

    override fun initLayoutListener() {
        mStateView?.onRetryClickListener = object : StateView.OnRetryClickListener {
            override fun onRetryClick() {
                mViewModel?.toFiles(userName, reposName, "")
            }
        }
    }

    override fun reloadData() {
        mViewModel?.toFiles(userName, reposName, "")
    }

    override fun loadData() {
        mViewModel?.toFiles(userName, reposName, "")
    }
}