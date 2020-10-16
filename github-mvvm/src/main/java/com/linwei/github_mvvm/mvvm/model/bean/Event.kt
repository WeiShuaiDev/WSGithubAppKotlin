package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

import java.util.Date

class Event : Serializable {
    var id: String? = null
    var type: String? = null
    var actor: User? = null
    var repo: Repository? = null
    var org: User? = null
    var payload: EventPayload? = null
    @SerializedName("public")
    var isPublic: Boolean = false
    @SerializedName("created_at")
    var createdAt: Date? = null

}