package com.linwei.github_mvvm.mvvm.viewmodel.main

import android.app.Application
import android.view.View
import com.linwei.cams_mvvm.mvvm.BaseViewModel
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import com.linwei.github_mvvm.mvvm.model.repository.service.MineModel
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MineViewModel @Inject constructor(
    model: MineModel,
    application: Application
) : BaseViewModel(model, application), MineContract.ViewModel {





    fun onTabIconClick(v: View?) {
        when (v?.id) {
            R.id.mine_header_repos -> {  //仓库

            }

            R.id.mine_header_fan -> {   //粉丝

            }

            R.id.mine_header_focus -> {   //关注

            }

            R.id.mine_header_star -> {   //星标

            }

            R.id.mine_header_honor -> {

            }
        }
    }
}