package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

import java.util.Date


class IssueEvent : Serializable {

    var id: String? = null
    var user: User? = null
    @SerializedName("created_at")
    var createdAt: Date? = null
    @SerializedName("updated_at")
    var updatedAt: Date? = null
    var body: String? = null
    @SerializedName("body_html")
    var bodyHtml: String? = null
    @SerializedName("event")
    var type: String? = null
    @SerializedName("html_url")
    var htmlUrl: String? = null
}
