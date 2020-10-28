package com.linwei.github_mvvm.mvvm.model.repository.service

import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.model.api.service.IssueService
import com.linwei.github_mvvm.mvvm.model.api.service.SearchService
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/27
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
open class SearchRepository @Inject constructor(
    dataRepository: DataMvvmRepository
) : BaseModel(dataRepository) {

    /**
     *  搜索服务接口
     */
    private val searchService: SearchService by lazy {
        dataRepository.obtainRetrofitService(SearchService::class.java)
    }


}