package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.bean.*
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/25
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface SearchService {

    /**
     * @param query [String] 关键字
     * @param sort [String]  搜索排序依据，比如best_match
     * @param order [String]  排序
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("search/users")
    @Headers("Page:page")
    fun searchUsers(
        @Query(value = "q", encoded = true) query: String,
        @Query("sort") sort: String = "best%20match",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<Page<SearchResult<User>>>

    /**
     * @param query [String]
     * @param sort [String]
     * @param order [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("search/repositories")
    @Headers("Page:page")
    fun searchRepos(
        @Query(value = "q", encoded = true) query: String,
        @Query("sort") sort: String = "best%20match",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<Page<SearchResult<Repository>>>

    /**
     * @param query [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("search/issues")
    @Headers("Page:page", "Accept:application/vnd.github.html,application/vnd.github.VERSION.raw")
    fun searchIssues(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Query(value = "q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<Page<SearchResult<Issue>>>

}
