package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.bean.*
import okhttp3.ResponseBody
import retrofit2.http.*
import java.util.ArrayList

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/10
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface UserService {

    /**
     * 获取用户数据
     */
    @GET("user")
    fun getPersonInfo(@Header("forceNetWork") forceNetWork: Boolean): LiveData<User>

    /**
     * 获取用户数据
     * @param user [String] 用户名
     */
    @GET("users/{user}")
    fun getUser(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String
    ): LiveData<User>

    /**
     * 保存用户信息
     * @param body [UserInfoRequestModel]
     */
    @PATCH("user")
    fun saveUserInfo(
        @Body body: UserInfoRequestModel
    ): LiveData<User>

    /**
     * 关注用户
     * @param user [String]
     */
    @GET("user/following/{user}")
    fun checkFollowing(
        @Path("user") user: String
    ): LiveData<ResponseBody>

    /**
     * 检查一个用户是否关注另一个
     * @param user [String]
     * @param targetUser [String]
     */
    @GET("users/{user}/following/{targetUser}")
    fun checkFollowing(
        @Path("user") user: String,
        @Path("targetUser") targetUser: String
    ): LiveData<ResponseBody>

    /**
     * @param user [String]
     */
    @PUT("user/following/{user}")
    fun followUser(
        @Path("user") user: String
    ): LiveData<ResponseBody>

    /**
     * @param user [String]
     */
    @DELETE("user/following/{user}")
    fun unfollowUser(
        @Path("user") user: String
    ): LiveData<ResponseBody>

    /**
     * @param user [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("users/{user}/followers")
    fun getFollowers(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<User>>

    /**
     * 获取所有关注用户
     * @param user [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("users/{user}/following")
    fun getFollowing(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<ArrayList<User>>

    /**
     * 列出用户执行的事件
     * @param user [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("users/{user}/events")
    fun getUserEvents(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<Page<List<Event>>>

    /**
     * 列出用户已收到的事件
     * @param user [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("users/{user}/received_events")
    fun getNewsEvent(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<Page<List<Event>>>

    /**
     * 列出用户已收到的事件
     * @param org [String]
     * @param page [Int]
     * @param per_page [Int]
     */
    @GET("orgs/{org}/members")
    fun getOrgMembers(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("org") org: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int = Api.PAGE_SIZE
    ): LiveData<Page<List<User>>>

    /**
     * @param user [String]
     */
    @GET("users/{user}/orgs")
    fun getUserOrgs(
        @Header("forceNetWork") forceNetWork: Boolean,
        @Path("user") user: String
    ): LiveData<List<User>>


}