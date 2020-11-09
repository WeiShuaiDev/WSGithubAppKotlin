package com.linwei.github_mvvm.mvvm.model.repository.repos

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.contract.repos.ReposIssueListContract
import com.linwei.github_mvvm.mvvm.model.repository.service.ReposRepository
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
class ReposIssueListModel @Inject constructor(
    dataRepository: DataMvvmRepository,
    private val reposRepository: ReposRepository
) : BaseModel(dataRepository), ReposIssueListContract.Model {

}