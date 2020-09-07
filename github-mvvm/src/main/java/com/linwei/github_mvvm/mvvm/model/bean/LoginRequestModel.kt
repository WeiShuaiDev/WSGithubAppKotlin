package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import com.linwei.github_mvvm.BuildConfig
import java.util.*

/**
 * 请求登录的model对象
 */
class LoginRequestModel {

    var scopes: List<String>? = null
        private set
    var note: String? = null
        private set

    @SerializedName("client_id")
    var clientId: String? = null
        private set

    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        private val scopes: List<String>? = Arrays.asList("user", "repo", "gist", "notifications")
        const val note: String = BuildConfig.APPLICATION_ID
        const val clientId: String = BuildConfig.CLIENT_ID
        const val clientSecret: String = BuildConfig.CLIENT_SECRET

        fun generate(): LoginRequestModel {
            val model = LoginRequestModel()
            model.scopes = scopes
            model.note = note
            model.clientId = clientId
            model.clientSecret = clientSecret
            return model
        }
    }
}
