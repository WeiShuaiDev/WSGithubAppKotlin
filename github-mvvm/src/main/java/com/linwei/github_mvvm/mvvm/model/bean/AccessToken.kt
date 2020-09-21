package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * OAuth 认证数据
 */
class AccessToken : Serializable {
    @SerializedName("access_token")
    var accessToken: String? = null

    @SerializedName("token_type")
    var tokenType: String? = null

    var scope: String? = null
}