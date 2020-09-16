package com.linwei.github_mvvm.mvvm.model.bean

import com.linwei.cams.ext.deviceManager
import com.linwei.github_mvvm.BuildConfig
import java.io.Serializable

/**
 * 请求登录的model对象
 */
class AuthRequestBean : Serializable {

    var scopes: List<String>? = null
        private set

    var note: String? = null
        private set

    var note_url: String? = null
        private set

    var client_secret: String? = null
        private set

    companion object {
        private val scopes: List<String>? =
            listOf("user", "repo", "notifications", "gist", "admin:org")
        private const val note: String = BuildConfig.NOTE
        private const val noteUrl: String = BuildConfig.NOTE_URL
        const val clientId: String = BuildConfig.CLIENT_ID
        const val clientSecret: String = BuildConfig.CLIENT_SECRET


        fun generate(): AuthRequestBean {
            val model = AuthRequestBean()
            model.scopes = scopes
            model.note = note
            model.note_url = noteUrl
            model.client_secret = clientSecret
            return model
        }

        /**
         * 设备Id
         */
        private val deviceId: String = deviceManager().getDeviceId()

        /**
         * 指纹信息
         *
         */
        val fingerPrint: String by lazy {
            clientId + deviceId
        }
    }
}

/**
 * Account 认证数据
 */
data class AuthResponseBean(
    var id: Int,
    var url: String,
    var app: AppInfo,
    var token: String,
    var hashed_token: String,
    var token_last_eight: String,
    var note: String,
    var note_url: String,
    var created_at: String,
    var updated_at: String,
    var scopes: List<String>
) : Serializable

data class AppInfo(var name: String, var url: String, var client_id: String) : Serializable

/**
 * OAuth 认证数据
 */
data class AccessTokenBean(
    var access_token: String?,
    var token_type: String?,
    var scope: String?
) : Serializable
