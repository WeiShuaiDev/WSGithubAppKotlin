package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class AppInfo : Serializable {
    var name: String? = null
    var url: String? = null

    @SerializedName("client_id")
    var clientId: String? = null
}