package com.weiyun.cashloan.data.network.callback

import com.linwei.frame.http.config.ApiConstant
import com.linwei.frame.http.config.NetWorkStateCode
import com.linwei.frame.http.model.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
abstract class RetrofitCallback<T>() : Callback<BaseResponse<T>> {

    override fun onFailure(call: Call<BaseResponse<T>>?, throwable: Throwable?) {
        throwable?.message?.let {
            onFailure(ApiConstant.REQUEST_FAILURE, it)
        }
    }

    override fun onResponse(call: Call<BaseResponse<T>>?, response: Response<BaseResponse<T>>?) {
        if (response != null) {
            val data: BaseResponse<T>? = response.body()
            if (response.isSuccessful && !NetWorkStateCode().isExistByCode(data?.code)) {
                onSuccess(data?.code, data?.result, data?.sign)
            } else {
                onFailure(data?.code, data?.message)
            }
        } else {
            onFailure(ApiConstant.REQUEST_FAILURE, "")
        }
    }

    /**
     * 请求网络成功
     */
    abstract fun onSuccess(code: String?, data: T?, sign: String?)

    /**
     * 请求网络失败
     */
    abstract fun onFailure(code: String?, message: String?)

}