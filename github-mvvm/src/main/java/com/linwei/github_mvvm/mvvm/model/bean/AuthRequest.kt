package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import com.linwei.cams.ext.deviceManager
import com.linwei.github_mvvm.BuildConfig
import java.io.Serializable

/**
 * 请求登录的model对象
 */
class AuthRequest : Serializable {

    var scopes: List<String>? = null
        private set

    var note: String? = null
        private set

    @SerializedName("note_url")
    var noteUrl: String? = null
        private set

    @SerializedName("client_secret")
    var clientSecret: String? = null
        private set

    companion object {
        private val scopes: List<String>? =
            listOf("user", "repo", "notifications", "gist", "admin:org")

        private val note: String = BuildConfig.NOTE
        private val noteUrl: String = BuildConfig.NOTE_URL
        val clientId: String = BuildConfig.CLIENT_ID
        val clientSecret: String = BuildConfig.CLIENT_SECRET


        fun generate(): AuthRequest {
            val model = AuthRequest()
            model.scopes = scopes
            model.note = note
            model.noteUrl = noteUrl
            model.clientSecret = clientSecret
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






