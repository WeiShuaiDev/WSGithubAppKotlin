package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.bean.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.ArrayList

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/21
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface RepoService {

    /**
     * 用户仓库数据
     * @param user [String]
     * @param page [Int]
     * @param sort [String]
     * @param per_page [Int]
     */
    @GET("users/{user}/repos")
    fun getUserRepository100StatusDao(
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "pushed",
        @Query("per_page") per_page: Int = 100
    ): LiveData<ArrayList<Repository>>


    /**
     * 标记为星际状态，仓库数据
     * @param user [String]
     * @param page [Int]
     * @param sort [String]
     * @param per_page [Int]
     */
    @GET("users/{user}/starred")
    fun getStarredRepos(
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "updated",
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Repository>>

    /**
     * @param page [Int]
     * @param type [String]
     * @param sort [String]
     * @param direction [String]
     * @param per_page [Int]
     */
    @GET("user/repos")
    fun getUserRepos(
        @Query("page") page: Int,
        @Query("type") type: String,
        @Query("sort") sort: String,
        @Query("direction") direction: String,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Repository>>

    /**
     * @param user [String]
     * @param page [Int]
     * @param sort [String]
     * @param per_page [Int]
     */
    @GET("users/{user}/repos")
    fun getUserPublicRepos(
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("sort") sort: String = "pushed",
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Repository>>

    /**
     * 检查当前仓库是否标记为 `Star`
     * @param owner [String]
     * @param repo [String]
     */
    @GET("user/starred/{owner}/{repo}")
    fun checkRepoStarred(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ResponseBody>

    /**
     * 当前仓库标记为 `Star`
     * @param owner [String]
     * @param repo [String]
     */
    @PUT("user/starred/{owner}/{repo}")
    fun starRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ResponseBody>

    /**
     * 当前仓库去除 `Star` 标记
     * @param owner [String]
     * @param repo [String]
     */
    @DELETE("user/starred/{owner}/{repo}")
    fun unstarRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ResponseBody>

    /**
     * 检查当前仓库是否标记为 `Watched`
     * @param owner [String]
     * @param repo [String]
     */
    @GET("user/subscriptions/{owner}/{repo}")
    fun checkRepoWatched(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ResponseBody>

    /**
     * 当前仓库去除 `Star` 标记
     * @param owner [String]
     * @param repo [String]
     */
    @PUT("user/subscriptions/{owner}/{repo}")
    fun watchRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ResponseBody>

    /**
     * 当前仓库去除 `Star` 标记
     * @param owner [String]
     * @param repo [String]
     */
    @DELETE("user/subscriptions/{owner}/{repo}")
    fun unwatchRepo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ResponseBody>

    /**
     * @param url [String]
     */
    @GET
    @Headers("Accept: application/vnd.github.html")
    fun getFileAsHtmlStream(
        @Url url: String
    ): LiveData<ResponseBody>

    /**
     * @param url [String]
     */
    @GET
    @Headers("Accept: application/vnd.github.VERSION.raw")
    fun getFileAsStream(
        @Url url: String
    ): LiveData<ResponseBody>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param path [String]
     * @param ref [String]
     */
    @GET("repos/{owner}/{repo}/contents/{path}")
    fun getRepoFiles(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path", encoded = true) path: String,
        @Query("ref") branch: String = "master"
    ): LiveData<ArrayList<FileModel>>

    /**
     * @param owner [String]
     * @param repo [String]
     */
    @GET("repos/{owner}/{repo}/branches")
    fun getBranches(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ArrayList<Branch>>

    /**
     * @param owner [String]
     * @param repo [String]
     */
    @GET("repos/{owner}/{repo}/tags")
    fun getTags(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<ArrayList<Branch>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param page [Int]
     */
    @GET("repos/{owner}/{repo}/stargazers")
    fun getStargazers(
        @Path(value = "owner") owner: String,
        @Path(value = "repo") repo: String,
        @Query("page") page: Int
    ): LiveData<ArrayList<User>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param page [Int]
     */
    @GET("repos/{owner}/{repo}/subscribers")
    fun getWatchers(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int
    ): LiveData<ArrayList<User>>

    /**
     * @param owner [String]
     * @param repo [String]
     */
    @GET("repos/{owner}/{repo}")
    fun getRepoInfo(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<Repository>

    /**
     * @param owner [String]
     * @param repo [String]
     */
    @POST("repos/{owner}/{repo}/forks")
    fun createFork(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): LiveData<Repository>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("repos/{owner}/{repo}/forks")
    fun getForks(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Repository>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param page [Int]
     * @param per_page [Int]
     * List public events for a network of repositories
     */
    @GET("networks/{owner}/{repo}/events")
    fun getRepoEvent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Event>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param page [Int]
     * @param per_page [Int]
     * */
    @GET("repos/{owner}/{repo}/releases")
    @Headers("Accept: application/vnd.github.html")
    fun getReleases(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Release>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("repos/{owner}/{repo}/releases")
    fun getReleasesNotHtml(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<Release>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param tag [String]
     */
    @GET("repos/{owner}/{repo}/releases/tags/{tag}")
    @Headers("Accept: application/vnd.github.html")
    fun getReleaseByTagName(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("tag") tag: String
    ): LiveData<Release>

    /**
     * @param forceNetWork [Boolean]
     * @param languageType [String]
     * @param since [String]
     */
    @GET("https://github.com/trending/{languageType}")
    @Headers("Content-Type: text/plain;charset=utf-8")
    fun getTrendData(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("languageType") languageType: String,
        @Query("since") since: String
    ): LiveData<String>

    /**
     * @param apiToken [String]
     * @param since [String]
     * @param languageType [String]
     */
    @GET("https://guoshuyu.cn/github/trend/list")
    @Headers("Content-Type: text/plain;charset=utf-8")
    fun getTrendDataAPI(
        @Header("api-token") apiToken: String,
        @Query("since") since: String,
        @Query("languageType") languageType: String
    ): LiveData<List<TrendingRepoModel>>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param branch [String]
     */
    @GET("repos/{owner}/{repo}/readme")
    @Headers("Content-Type: text/plain;charset=utf-8", "Accept: application/vnd.github.html")
    fun getReadmeHtml(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("ref") branch: String = "master"
    ): LiveData<String>

    /**
     * @param owner [String]
     * @param repo [String]
     * @param path [String]
     * @param branch [String]
     */
    @GET("repos/{owner}/{repo}/contents/{path}")
    @Headers("Content-Type: text/plain;charset=utf-8", "Accept: application/vnd.github.html")
    fun getRepoFilesDetail(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path(value = "path", encoded = true) path: String,
        @Query("ref") branch: String = "master"
    ): LiveData<String>

}