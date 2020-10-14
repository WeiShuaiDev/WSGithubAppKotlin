package com.linwei.cams.http.adapter

import androidx.lifecycle.LiveData
import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams.http.model.BaseResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: LiveData适配器，通过在Retrofit中注册[LiveDataCallAdapter]，来适配LiveData<BaseResponse<T>> 回调
 *-----------------------------------------------------------------------
 */
class LiveDataCallAdapter<R, T>(private val responseType: Type) : CallAdapter<R, LiveData<T>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): LiveData<T> {
        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)

            @Suppress("UNCHECKED_CAST")
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>?, response: Response<R>?) {
                            if (response?.isSuccessful == true) {
                                postValue(response.body() as T)
                            } else {
                                val value: T = BaseResponse<R>(
                                    response?.code().toString(),
                                    response?.message() ?: "",
                                    null
                                ) as T
                                postValue(value)
                            }
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            val value: T = BaseResponse<R>(
                                ApiStateConstant.REQUEST_FAILURE,
                                t.message ?: "",
                                null
                            ) as T
                            postValue(value)
                        }
                    })
                }
            }
        }
    }
}
