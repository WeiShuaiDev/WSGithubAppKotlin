package com.linwei.frame.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.linwei.frame.http.adapter.LiveDataCallAdapterFactory
import com.linwei.frame.http.config.ApiConstant
import com.linwei.frame.http.interceptor.CommonInterceptor
import com.linwei.frame.http.interceptor.LogInterceptor
import com.linwei.frame.http.interceptor.ResultStateInterceptor
import com.linwei.frame.http.model.BaseResponse
import com.linwei.frame.manager.EventBusManager
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

    companion object {
        private var INSTANCE: RetrofitFactory? = null

        @JvmStatic
        fun getInstance(): RetrofitFactory {
            return INSTANCE
                ?: RetrofitFactory().apply {
                    INSTANCE = this
                }
        }

        const val TAG: String = "RetrofitFactory"
        const val CONNECT_TIME_OUT: Long = 60
        const val WRITE_TIME_OUT: Long = 60
        const val READ_TIME_OUT: Long = 60
    }

    init {
        //Retrofit实例化
        val factory = Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(createBaseGson()))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())

        mRetrofit =
            factory.client(initOkHttpClient(CONNECT_TIME_OUT, WRITE_TIME_OUT, READ_TIME_OUT))
                .build()
    }

    /**
     *  自定义 `Gson` 数据转换器，通过请求后响应进行预处理，转换为想要数据格式 [BaseResponse],如果请求响应 `JSON` 数据
     *  中不包含 `data` 字段,这进行默认 `Gson` 转换。
     * @return gson [Gson]
     */
    private fun createBaseGson(): Gson {
        return GsonBuilder()
            .registerTypeHierarchyAdapter(
                BaseResponse::class.java,
                JsonDeserializer { json, typeOfT, _ ->
                    try {
                        val jsonObject: JsonObject = json.asJsonObject
                        if (jsonObject.has("data")) {
                            if ("" == jsonObject.get("data").asString) {
                                return@JsonDeserializer BaseResponse(
                                    jsonObject.get("code").asString,
                                    jsonObject.get("error").asString,
                                    null
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
     * 创建 OkHttpBuilder 对象，并配置三个拦截器 [CommonInterceptor] 通用拦截器、[ResultStateInterceptor] 请求响应数据拦截、
     * [LogInterceptor] 日志拦截。对请求发起链接超时配置 [connectTimeOut],流写入超时配置 [writeTimeOut],流读取超时配置 [readTimeOut],
     * 最后通过调用 [build] 方法创建 [OkHttpClient] 对象
     * @param connectTimeOut [Long] 链接超时时间
     * @param writeTimeOut [Long] 写入超时时间
     * @param readTimeOut [Long] 读取超时时间
     * @return okHttpClient [OkHttpClient] 返回OkHttpClient对象
     */
    private fun initOkHttpClient(
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
     * 根据传进来 [service] `Class`对象，获取具体实例化对象。
     * @param service [Class]
     * @return [T]
     */
    fun <T> create(service: Class<T>): T {
        return mRetrofit.create(service)
    }
}
