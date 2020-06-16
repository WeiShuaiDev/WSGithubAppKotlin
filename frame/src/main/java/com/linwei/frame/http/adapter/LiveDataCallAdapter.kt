package com.linwei.frame.http.adapter

import androidx.lifecycle.LiveData
import com.linwei.frame.http.config.ApiConstant.REQUEST_FAILURE
import com.linwei.frame.http.model.BaseResponse
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
 * @Description:
 *-----------------------------------------------------------------------
 */
class LiveDataCallAdapter<T>(private val responseType: Type) : CallAdapter<LiveData<T>> {
    override fun <R : Any?> adapt(call: Call<R>?): LiveData<T> {
        return object : LiveData<T>() {
            private val started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {//确保执行一次
                    call?.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>?, response: Response<R>?) {
                            postValue(response?.body() as T)
                        }

                        override fun onFailure(call: Call<R>, t: Throwable) {
                            val value = BaseResponse<R>(REQUEST_FAILURE, t.message ?: "", null, "") as T
                            postValue(value)
                        }
                    })
                }
            }
        }
    }

    override fun responseType() = responseType
}