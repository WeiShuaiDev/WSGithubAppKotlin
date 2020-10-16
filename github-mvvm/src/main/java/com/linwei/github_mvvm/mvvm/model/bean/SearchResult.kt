package com.linwei.github_mvvm.mvvm.model.bean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

import java.util.ArrayList


class SearchResult<M>: Serializable {

    @SerializedName("total_count")
    var totalCount: String? = null
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false
    var items: ArrayList<M>? = null

}
