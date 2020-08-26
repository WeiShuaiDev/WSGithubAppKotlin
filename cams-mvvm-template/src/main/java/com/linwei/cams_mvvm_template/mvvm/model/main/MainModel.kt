package com.linwei.cams_mvvm_template.mvvm.model.main

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.cams_mvvm_template.mvvm.contract.main.MainContract
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/3
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class MainModel @Inject constructor(dataRepository: DataMvvmRepository) :
    BaseModel(dataRepository), MainContract.Model {


}