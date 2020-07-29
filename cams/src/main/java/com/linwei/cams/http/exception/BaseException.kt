package com.linwei.cams.http.exception

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/29
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 异常处理
 *-----------------------------------------------------------------------
 */
class BaseException(val code: String, val msg: String) : Throwable()
