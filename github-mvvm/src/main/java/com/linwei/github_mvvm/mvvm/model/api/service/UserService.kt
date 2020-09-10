package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.bean.AccessToken
import com.linwei.github_mvvm.mvvm.model.bean.LoginRequestModel
import retrofit2.Response
import retrofit2.http.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface UserService {

    /**
     * `AccountLogin` 登录获取授权码
     * @param authRequestModel [LoginRequestModel]
     */
    @POST("authorizations")
    fun authorizations(@Body authRequestModel: LoginRequestModel): LiveData<AccessToken>

    /**
     * `OAuth` 登录获取授权码
     * @param client_id [String]
     * @param client_secret [String]
     * @param code [String]
     */
    @GET("https://github.com/login/oauth/access_token")
    fun authorizationsCode(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("code") code: String
    ): LiveData<Response<AccessToken>>

}