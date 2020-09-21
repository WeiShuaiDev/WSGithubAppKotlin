package com.linwei.github_mvvm.mvvm.model.api.service

import androidx.lifecycle.LiveData
import com.linwei.github_mvvm.mvvm.model.bean.*
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
interface AuthService {

    /**
     * `AccountLogin` 登录获取授权码
     * @param authRequestModel [LoginRequestBean]
     * @param clientId [String]
     * @param fingerPrint [String]
     */
    @PUT("/authorizations/clients/{clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body authRequestModel: AuthRequest,
        @Path("clientId") clientId: String = AuthRequest.clientId,
        @Path("fingerPrint") fingerPrint: String = AuthRequest.fingerPrint
    ): LiveData<AuthResponse>

    /**
     * 删除授权
     * @param id [String]
     */
    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): LiveData<Any>

    /**
     * `OAuth` 登录获取授权码
     * @param client_id [String]
     * @param client_secret [String]
     * @param code [String]
     */
    @GET("https://github.com/login/oauth/access_token")
    @Headers("Accept: application/json")
    fun createCodeAuthorization(
        @Query("client_id") client_id: String,
        @Query("client_secret") client_secret: String,
        @Query("code") code: String
    ): LiveData<AccessToken>

}