package com.linwei.github_mvvm.mvvm.model.bean


import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CommitGitInfo : Serializable {

    var message: String? = null
    var url: String? = null
    @SerializedName("comment_count")
    var commentCount: Int = 0
    var author: CommitGitUser? = null
    var committer: CommitGitUser? = null


}
