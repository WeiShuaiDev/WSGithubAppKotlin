package com.linwei.github_mvvm.mvvm.ui.module.main

import android.view.View
import com.linwei.cams_mvvm.base.BaseMvvmFragment
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.databinding.FragmentMineBinding
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.viewmodel.main.MineViewModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MineFragment : BaseMvvmFragment<MineViewModel, FragmentMineBinding>(), MineContract.View {

    @Inject
    lateinit var mAppGlobalModel: AppGlobalModel

    override fun provideContentViewId(): Int = R.layout.fragment_mine

    override fun bindViewModel() {
        mViewDataBinding?.let {
            it.mineViewModel = mViewModel
            it.globalModel = mAppGlobalModel
            it.lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun initLayoutView(rootView: View?) {

    }

    override fun initLayoutData() {

    }

    override fun initLayoutListener() {

    }

    override fun reloadData() {

    }

    override fun loadData() {

    }
}