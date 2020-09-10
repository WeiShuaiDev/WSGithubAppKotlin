package com.linwei.github_mvvm.mvvm.model.main

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.main.MineContract
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MineModel @Inject constructor(dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), MineContract.Model {


}