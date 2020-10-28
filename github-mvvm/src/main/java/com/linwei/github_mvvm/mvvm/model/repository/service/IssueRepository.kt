package com.linwei.github_mvvm.mvvm.model.repository.service

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.model.AppGlobalModel
import com.linwei.github_mvvm.mvvm.model.api.service.IssueService
import com.linwei.github_mvvm.mvvm.model.repository.db.LocalDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.IssueDao
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/28
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
open class IssueRepository @Inject constructor(
        private val appGlobalModel: AppGlobalModel,
        dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    /**
     *  问题服务接口
     */
    private val issueService: IssueService by lazy {
        dataRepository.obtainRetrofitService(IssueService::class.java)
    }

    /**
     * 问题服务接口
     */
    private val issueDao: IssueDao by lazy {
        dataRepository.obtainRoomDataBase(LocalDatabase::class.java).issueDao()
    }

}