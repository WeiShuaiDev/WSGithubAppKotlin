package com.linwei.github_mvvm.mvvm.model.repository.service

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.linwei.cams.http.callback.LiveDataCallBack
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.mvvm.BaseModel
import com.linwei.github_mvvm.mvvm.model.api.service.SearchService
import com.linwei.github_mvvm.mvvm.model.bean.*
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

    /**
     * 搜索用户
     * @param owner [LifecycleOwner]
     * @param query [String] 关键字
     * @param sort [String]  搜索排序依据，比如best_match
     * @param order [String]  排序
     * @param page [Int]
     * @return  [LiveDataCallBack]
     */
    fun requestSearchUsers(
        owner: LifecycleOwner,
        query: String,
        sort: String,
        order: String,
        page: Int, observer: LiveDataCallBack<Page<SearchResult<User>>>
    ): LiveData<Page<SearchResult<User>>> {

        return searchService.searchUsers(query = query, sort = sort, order = order, page = page)
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<SearchResult<User>>>() {
                        override fun onSuccess(code: String?, data: Page<SearchResult<User>>?) {
                            super.onSuccess(code, data)
                            observer.onSuccess(code, data)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
            }
    }

    /**
     * 搜索项目
     * @param owner [LifecycleOwner]
     * @param query [String] 关键字
     * @param sort [String]  搜索排序依据，比如best_match
     * @param order [String]  排序
     * @param page [Int]
     * @return  [LiveDataCallBack]
     */
    fun requestSearchRepos(
        owner: LifecycleOwner,
        query: String,
        sort: String,
        order: String,
        page: Int, observer: LiveDataCallBack<Page<SearchResult<Repository>>>
    ): LiveData<Page<SearchResult<Repository>>> {

        return searchService.searchRepos(query = query, sort = sort, order = order, page = page)
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<SearchResult<Repository>>>() {
                        override fun onSuccess(
                            code: String?,
                            data: Page<SearchResult<Repository>>?
                        ) {
                            super.onSuccess(code, data)
                            observer.onSuccess(code, data)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
            }
    }

    /**
     * 搜索问题
     * @param owner [LifecycleOwner]
     * @param query [String] 关键字
     * @param page [Int]
     * @return  [LiveDataCallBack]
     */
    fun requestSearchIssues(
        owner: LifecycleOwner,
        query: String,
        page: Int, observer: LiveDataCallBack<Page<SearchResult<Issue>>>
    ): LiveData<Page<SearchResult<Issue>>> {

        return searchService.searchIssues(forceNetWork = true, query = query, page = page)
            .apply {
                observe(
                    owner,
                    object : LiveDataCallBack<Page<SearchResult<Issue>>>() {
                        override fun onSuccess(code: String?, data: Page<SearchResult<Issue>>?) {
                            super.onSuccess(code, data)
                            observer.onSuccess(code, data)
                        }

                        override fun onFailure(code: String?, message: String?) {
                            super.onFailure(code, message)
                            observer.onFailure(code, message)
                        }
                    })
            }
    }


}