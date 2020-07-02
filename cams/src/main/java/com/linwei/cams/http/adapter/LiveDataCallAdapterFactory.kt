package com.linwei.cams.http.adapter

import androidx.lifecycle.LiveData
import com.linwei.cams.http.model.BaseResponse
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
 * @Description: 根据每次请求接口定义，进行类型判断，如果 `LiveData<BaseResponse<*>>` 类型，则判断 LiveData 泛型
 * 是不是BaseResponse,都满足条件后，交给 [LiveDataCallAdapter] 处理，否则不处理。
 *-----------------------------------------------------------------------
 */
class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        if (getRawType(returnType) != LiveData::class.java) return null
        //获取响应返回值泛型信息，并对类型进行判断
        val observableType: Type = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType: Class<*> = getRawType(observableType)

        if (rawType != BaseResponse::class.java) {
            throw IllegalArgumentException("type must be ApiResponse")
        }

        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }
        return LiveDataCallAdapter<Any, Any>(observableType)
    }

}