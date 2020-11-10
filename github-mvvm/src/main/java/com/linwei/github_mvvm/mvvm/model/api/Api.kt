package com.linwei.github_mvvm.mvvm.model.api

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 网络接口定义
 *-----------------------------------------------------------------------
 */
object Api {

    const val GITHUB_BASE_URL: String = "https://github.com/"

    const val GITHUB_API_BASE_URL = "https://api.github.com/"

    const val GRAPHIC_HOST = "https://ghchart.rshah.org/"

    const val API_TOKEN = "4d65e2a5626103f92a71867d7b49fea0"

    const val PAGE_SIZE: Int = 30

    const val OK_HTTP_CACHE_SIZE: Long = 1024 * 1024 * 10L

    const val FORCE_NETWORK = "forceNetwork"

    fun getReposHtmlUrl(userName: String, reposName: String): String =
        "$GITHUB_BASE_URL$userName/$reposName"

    fun getIssueHtmlUrl(userName: String, reposName: String, number: String): String =
        "$GITHUB_BASE_URL$userName/$reposName/issues/$number"

    fun getUserHtmlUrl(userName: String) =
        GITHUB_BASE_URL + userName

    fun getFileHtmlUrl(userName: String, reposName: String, path: String, branch: String = "master"): String =
        "$GITHUB_BASE_URL$userName/$reposName/blob/$branch/$path"

    fun getCommitHtmlUrl(userName: String, reposName: String, sha: String): String =
        "$GITHUB_BASE_URL$userName/$reposName/commit/$sha"
}