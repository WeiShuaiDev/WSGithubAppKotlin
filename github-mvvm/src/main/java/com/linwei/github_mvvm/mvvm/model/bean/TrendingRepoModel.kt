package com.linwei.github_mvvm.mvvm.model.bean

import java.io.Serializable

class TrendingRepoModel: Serializable {
    var fullName: String = ""
    var url: String = ""
    var description: String = ""
    var language: String = ""
    var meta: String = ""
    var contributors: List<String> = arrayListOf()
    var contributorsUrl: String = ""
    var starCount: String = ""
    var forkCount: String = ""
    var name: String = ""
    var reposName: String = ""
}