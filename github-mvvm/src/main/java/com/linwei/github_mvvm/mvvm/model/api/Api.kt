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
interface Api {

    companion object {
        const val GITHUB_BASE_URL: String = "https://github.com/"

        const val GITHUB_API_BASE_URL = "https://api.github.com/"

        const val GRAPHIC_HOST = "https://ghchart.rshah.org/"

        const val PAGE_SIZE: Int = 30

    }
}