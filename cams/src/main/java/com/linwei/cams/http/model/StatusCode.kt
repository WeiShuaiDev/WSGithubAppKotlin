package com.linwei.cams.http.model
import androidx.annotation.StringDef
import com.linwei.cams.http.model.StatusCode.Companion.ERROR
import com.linwei.cams.http.model.StatusCode.Companion.FAILURE
import com.linwei.cams.http.model.StatusCode.Companion.LOADING
import com.linwei.cams.http.model.StatusCode.Companion.SUCCESS

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 定义四种网络请求状态，[LOADING] 加载中; [SUCCESS] 成功; [FAILURE] 失败; [ERROR] 错误
 *-----------------------------------------------------------------------
 */

@StringDef(LOADING, SUCCESS, FAILURE, ERROR)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class StatusCode {
    companion object {
        const val LOADING: String = "0"
        const val SUCCESS: String = "1"
        const val FAILURE: String = "2"
        const val ERROR: String = "3"
    }
}
