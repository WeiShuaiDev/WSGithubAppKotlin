package com.linwei.github_mvvm.mvvm.model.repository.issue

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.issue.IssueDetailContract
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
class IssueDetailModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val userRepository: UserRepository
) : BaseModel(dataRepository), IssueDetailContract.Model {


}