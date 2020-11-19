package com.linwei.github_mvvm.mvvm.model.repository.person

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.person.PersonContract
import com.linwei.github_mvvm.mvvm.model.repository.service.UserRepository
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/11/2
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class PersonModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val userRepository: UserRepository
) : BaseModel(dataRepository), PersonContract.Model {
    

}