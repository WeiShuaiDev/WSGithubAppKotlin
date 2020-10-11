package com.linwei.cams.http.model

import androidx.annotation.IntDef
import com.linwei.cams.http.model.StatusCode.Companion.END
import com.linwei.cams.http.model.StatusCode.Companion.ERROR
import com.linwei.cams.http.model.StatusCode.Companion.FAILURE
import com.linwei.cams.http.model.StatusCode.Companion.LOADING
import com.linwei.cams.http.model.StatusCode.Companion.START
import com.linwei.cams.http.model.StatusCode.Companion.SUCCESS

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 定义四种网络请求状态，[START] 开始；[LOADING] 加载中; [SUCCESS] 成功;
 * [FAILURE] 失败; [ERROR] 错误； [END] 结束；
 *-----------------------------------------------------------------------
 */

@Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
@IntDef(START, LOADING, SUCCESS, FAILURE, ERROR, END)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class StatusCode {
    companion object {
        const val START: Int = 1
        const val LOADING: Int = 2
        const val SUCCESS: Int = 3
        const val FAILURE: Int = 4
        const val ERROR: Int = 5
        const val END: Int = 6
    }
}
