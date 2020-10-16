package com.linwei.github_mvvm.mvvm.model.bean


import com.google.gson.annotations.SerializedName
import java.io.Serializable


class CommitFile : Serializable {

    var sha: String? = null
    @SerializedName("filename")
    var fileName: String? = null
    var status: String? = null
    var additions: Int = 0
    var deletions: Int = 0
    var changes: Int = 0
    @SerializedName("blob_url")
    var blobUrl: String? = null
    @SerializedName("raw_url")
    var rawUrl: String? = null
    @SerializedName("contents_url")
    var contentsUrl: String? = null
    var patch: String? = null

}
