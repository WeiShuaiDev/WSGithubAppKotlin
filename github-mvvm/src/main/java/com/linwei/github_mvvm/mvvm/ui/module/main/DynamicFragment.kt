package com.linwei.github_mvvm.mvvm.ui.module.main

import android.view.View
import androidx.lifecycle.Observer
import com.linwei.cams.ext.isNotNullOrSize
import com.linwei.cams.ext.yes
import com.linwei.cams.http.model.StatusCode
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.cams_mvvm.livedatabus.StatusLiveEvent
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentDynamicBinding
import com.linwei.github_mvvm.mvvm.contract.main.DynamicContract
import com.linwei.github_mvvm.mvvm.viewmodel.main.DynamicViewModel
import com.linwei.github_mvvm.utils.GsonUtils

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class DynamicFragment : BaseMvvmFragment<DynamicViewModel, FragmentDynamicBinding>(),
    DynamicContract.View {

    override fun provideContentViewId(): Int = R.layout.fragment_dynamic

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.viewModel = mViewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {

    }

    override fun initLayoutData() {
        mViewModel?.eventUiModel?.observe(viewLifecycleOwner, Observer {
            it.isNotNullOrSize().yes {
                it.forEach {

                }
            }
        })

    }

    override fun initLayoutListener() {
    }

    override fun reloadData() {
        mViewModel?.toReceivedEvent(1)
    }

    override fun loadData() {
        mViewModel?.toReceivedEvent(1)
    }
}
