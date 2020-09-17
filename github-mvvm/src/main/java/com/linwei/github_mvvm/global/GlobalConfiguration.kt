package com.linwei.github_mvvm.global

import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.module.ClientModule
import com.linwei.cams.di.module.GlobalConfigModule
import com.linwei.cams.http.GlobalHttpHandler
import com.linwei.cams.http.adapter.LiveDataCallAdapterFactory
import com.linwei.github_mvvm.BuildConfig
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.accessTokenPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.isLoginState
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userBasicCodePref
import com.linwei.github_mvvm.mvvm.model.api.Api
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/12
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class GlobalConfiguration : ConfigModule {

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        builder.httpUrl(Api.GITHUB_API_BASE_URL)
            .retrofitConfiguration(object : ClientModule.RetrofitConfiguration {
                override fun configRetrofit(context: Context, builder: Retrofit.Builder) {
                    //LiveData 适配器配置
                    builder.addCallAdapterFactory(LiveDataCallAdapterFactory())
                }
            }).okHttpClientConfiguration(object : ClientModule.OkHttpClientConfiguration {
                override fun configOkHttp(context: Context, builder: OkHttpClient.Builder) {
                    if (BuildConfig.LOG_DEBUG)
                        builder.addInterceptor(
                            HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                }
            })
            .globalHttpHandler(object : GlobalHttpHandler {
                override fun onHttpRequestBefore(
                    chain: Interceptor.Chain,
                    request: Request
                ): Request {
                    return request.newBuilder().apply {
                        header(
                            "accept",
                            "application/vnd.github.v3.full+json, ${request.header("accept") ?: ""}"
                        )
                        when {
                            request.url.pathSegments.contains("authorizations") -> {
                                header("Authorization", "Basic $userBasicCodePref")
                            }
                            isLoginState -> {
                                header("Authorization", "Token $accessTokenPref")
                            }
                            else ->
                                removeHeader("Authorization")
                        }
                    }.build()
                }

                override fun onHttpResultResponse(
                    httpResult: String,
                    chain: Interceptor.Chain,
                    response: Response
                ): Response {
                    val mediaType: MediaType? = response.body?.contentType()
                    // 请求响应状态码
                    val code: String = response.code.toString()
                    // 请求响应状态信息
                    val message: String = response.message

                    return response.newBuilder()
                        .body(httpResult.toResponseBody(mediaType))
                        .build()
                }
            })
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        lifecycles.add(AppLifecycleImpl())
    }

    override fun injectActivityLifecycle(
        context: Context,
        lifecycles: MutableList<Application.ActivityLifecycleCallbacks>
    ) {
        lifecycles.add(ActivityLifecycleImpl())
    }

    override fun injectFragmentLifecycle(
        context: Context,
        lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>
    ) {
        lifecycles.add(FragmentLifecycleImpl())
    }
}