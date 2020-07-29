package com.linwei.cams.http.callback

import com.linwei.cams.R
import com.linwei.cams.ext.string
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams.http.config.NetWorkStateCode
import com.linwei.cams.http.model.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: Retrofit中根据设置默认适配器,请求接口返回数据 `Call<BaseResponse<Any>>`
 *              通过使用 `RetrofitCallback` 回调接口，处理网络请求。
 *-----------------------------------------------------------------------
 */
abstract class RetrofitCallback<T> : Callback<BaseResponse<T>> {

    override fun onFailure(call: Call<BaseResponse<T>>, throwable: Throwable) {
        throwable.message?.let {
            onFailure(ApiStateConstant.REQUEST_FAILURE, it)
        }
    }

    override fun onResponse(call: Call<BaseResponse<T>>, response: Response<BaseResponse<T>>?) {
        if (response != null) {
            val data: BaseResponse<T> = response.body()!!
            if (response.isSuccessful && !NetWorkStateCode().isExistByCode(data.code)) {
                if (ApiStateConstant.REQUEST_SUCCESS == data.code) {
                    onSuccess(data.code, data.result)
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
     * @param data [T] 响应数据
     */
    open fun onSuccess(code: String?, data: T?) {}

    /**
     * 接口请求失败，回调 [onFailure] 方法
     * @param code [String] 失败状态码
     * @param message [String] 失败信息
     */
    open fun onFailure(code: String?, message: String?) {}

}