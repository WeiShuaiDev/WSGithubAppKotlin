package com.linwei.frame.http.callback

import androidx.lifecycle.Observer
import com.linwei.frame.R
import com.linwei.frame.ext.string
import com.linwei.frame.http.config.ApiStateConstant
import com.linwei.frame.http.config.NetWorkStateCode
import com.linwei.frame.http.model.BaseResponse

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: Retrofit+LiveData 中根据设置不同适配器 [LiveDataCallAdapter]，请求接口返回数据 `ListData<BaseResponse<Any>>`,
 *              通过使用 `LiveDataCallBack` 回调接口，处理网络请求。
 *-----------------------------------------------------------------------
 */
abstract class LiveDataCallBack<T, V> : Observer<T> {
    override fun onChanged(t: T) {
        if (t != null && t is BaseResponse<*>) {
            val data: BaseResponse<*> = t
            if (!NetWorkStateCode().isExistByCode(data.code)) {
                if (ApiStateConstant.REQUEST_SUCCESS == data.code) {
                    onSuccess(data.code, data.result as V?)
                } else {
                    onFailure(data.code, data.message)
                }
            } else {
                onFailure(data.code, data.message)
            }
        } else {
            onFailure(ApiStateConstant.REQUEST_FAILURE, R.string.unknown_error.string())
        }
    }


    /**
     * 接口请求成功，回调 [onSuccess] 方法
     * @param code [String] 成功状态码
     * @param data [V] 响应数据
     */
    open fun onSuccess(code: String?, data: V?) {

    }

    /**
     * 接口请求失败，回调 [onFailure] 方法
     * @param code [String] 失败状态码
     * @param message [String] 失败信息
     */
    open fun onFailure(code: String?, message: String?) {

    }

}

