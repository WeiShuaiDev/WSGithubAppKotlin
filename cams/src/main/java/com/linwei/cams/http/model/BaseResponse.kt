package com.linwei.cams.http.model

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: 网络请求状态实体类
 *-----------------------------------------------------------------------
 */
data class BaseResponse<out T>(
    val code: String, val message: String, val result: T?
)