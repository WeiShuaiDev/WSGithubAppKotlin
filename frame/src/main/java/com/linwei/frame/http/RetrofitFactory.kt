package com.linwei.frame.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.linwei.frame.http.adapter.LiveDataCallAdapterFactory
import com.linwei.frame.http.config.ApiConstant
import com.linwei.frame.http.interceptor.CommonInterceptor
import com.linwei.frame.http.interceptor.LogInterceptor
import com.linwei.frame.http.interceptor.ResultStateInterceptor
import com.linwei.frame.http.model.BaseResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RetrofitFactory private constructor() {
    private val mRetrofit: Retrofit

    /*
        单例实现
     */
    companion object {
        val instance: RetrofitFactory by lazy { RetrofitFactory() }
        const val TAG: String = "RetrofitFactory"
    }

    init {
        //Retrofit实例化
        val factory = Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(createBaseGson()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())

        mRetrofit = factory.client(initClient(60, 60, 60))
            .build()
    }

    /**
     * 创建Gson数据转换
     */
    private fun createBaseGson(): Gson {
        return GsonBuilder()
            .registerTypeHierarchyAdapter(
                BaseResponse::class.java,
                JsonDeserializer { json, typeOfT, _ ->
                    try {
                        val jsonObject = json.asJsonObject
                        if (jsonObject.has("data")) {
                            if ("" == jsonObject.get("data").asString) {
                                return@JsonDeserializer BaseResponse(
                                    jsonObject.get("code").asString,
                                    jsonObject.get("error").asString,
                                    null,
                                    ""
                                )
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    Gson().fromJson<BaseResponse<*>>(json, typeOfT)
                }).create()
    }

    /**
     * 初始化OKHttp
     */
    private fun initClient(
        connectTimeOut: Long,
        writeTimeOut: Long,
        readTimeOut: Long
    ): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(CommonInterceptor())  // 通用拦截
            .addInterceptor(ResultStateInterceptor()) //请求状态拦截
            .addInterceptor(LogInterceptor()) //Log拦截
            .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
            .writeTimeout(writeTimeOut, TimeUnit.SECONDS)
            .readTimeout(readTimeOut, TimeUnit.SECONDS)
        return okHttpBuilder.build()
    }

    /**
     * 具体服务实例化
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}
