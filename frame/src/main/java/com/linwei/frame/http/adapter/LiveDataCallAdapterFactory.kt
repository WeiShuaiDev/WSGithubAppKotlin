package com.linwei.frame.http.adapter

import androidx.lifecycle.LiveData
import com.linwei.frame.http.model.BaseResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): CallAdapter<*>? {
        if (getRawType(returnType) != LiveData::class.java) return null
        //获取请求数据泛型类型
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(observableType)

        if (rawType != BaseResponse::class.java) {
            throw IllegalArgumentException("type must be ApiResponse")
        }

        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any>(observableType)
    }
}