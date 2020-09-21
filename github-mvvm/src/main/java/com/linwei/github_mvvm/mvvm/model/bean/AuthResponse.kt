package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * @Author: weiyun
 * @Time: 2020/9/21
 * @Description:
 */
class AuthResponse : Serializable {
    var id: Int = 0
    var url: String? = null
    var app: AppInfo? = null
    var token: String? = null

    @SerializedName("hashed_token")
    var hashedToken: String? = null

    @SerializedName("token_last_eight")
    var tokenLastEight: String? = null
    var note: String? = null

    @SerializedName("note_url")
    var noteUrl: String? = null

    @SerializedName("created_at")
    var createdAt: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null
    var scopes: List<String>? = null
}