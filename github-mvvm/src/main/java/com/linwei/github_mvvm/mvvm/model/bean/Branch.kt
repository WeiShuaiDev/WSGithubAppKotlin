package com.linwei.github_mvvm.mvvm.model.bean


import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Branch:Serializable {

    var name: String? = null
    @SerializedName("zipball_url")
    var zipballUrl: String? = null
    @SerializedName("tarball_url")
    var tarballUrl: String? = null

    var isBranch = true

}
