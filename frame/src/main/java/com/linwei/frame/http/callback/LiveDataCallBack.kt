package com.linwei.frame.http.callback

import androidx.lifecycle.Observer
import com.linwei.frame.http.config.ApiConstant
import com.linwei.frame.http.config.NetWorkStateCode
import com.linwei.frame.http.model.BaseResponse

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
abstract class LiveDataCallBack<T, V> : Observer<T> {
    override fun onChanged(t: T) {
        if (t != null && t is BaseResponse<*>) {
            val data = t as BaseResponse<*>
            if (!NetWorkStateCode().isExistByCode(data.code)) {
                if (ApiConstant.REQUEST_SUCCESS == data.code) {
                    onSuccess(data.code, data.result as V, data.sign)
                } else {
                    onFailure(data.code, data.message)
                }
            } else {
                onFailure(data.code, data.message)
            }
        } else {
            onFailure(ApiConstant.REQUEST_FAILURE, "")
        }
    }

    /**
     * 请求网络成功
     */
    open fun onSuccess(code: String?, data: V?, sign: String?) {

    }

    /**
     * 请求网络失败
     */
    open fun onFailure(code: String?, message: String?) {

    }

}

