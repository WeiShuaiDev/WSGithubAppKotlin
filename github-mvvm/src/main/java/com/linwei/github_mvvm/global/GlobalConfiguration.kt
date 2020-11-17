package com.linwei.github_mvvm.global

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.fragment.app.FragmentManager
import com.linwei.cams.base.global.ConfigModule
import com.linwei.cams.base.lifecycle.AppLifecycles
import com.linwei.cams.di.module.ClientModule
import com.linwei.cams.di.module.GlobalConfigModule
import com.linwei.cams.ext.isNotNullOrEmpty
import com.linwei.cams.ext.otherwise
import com.linwei.cams.ext.yes
import com.linwei.cams.http.GlobalHttpHandler
import com.linwei.cams.http.adapter.LiveDataCallAdapterFactory
import com.linwei.cams.utils.FileUtils
import com.linwei.github_mvvm.BuildConfig
import com.linwei.github_mvvm.global.interceptor.HttpCacheInterceptor
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.accessTokenPref
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.isLoginState
import com.linwei.github_mvvm.mvvm.factory.UserInfoStorage.userBasicCodePref
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.bean.Page
import com.linwei.github_mvvm.utils.GsonUtils
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File

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
        builder
            .httpUrl(Api.GITHUB_API_BASE_URL)
            .interceptors(HttpCacheInterceptor())
            .retrofitConfiguration(object : ClientModule.RetrofitConfiguration {
                override fun configRetrofit(context: Context, builder: Retrofit.Builder) {
                    //Scalars 配置
                    builder.addConverterFactory(ScalarsConverterFactory.create())
                    //LiveData 适配器配置
                    builder.addCallAdapterFactory(LiveDataCallAdapterFactory())
                }
            }).okHttpClientConfiguration(object : ClientModule.OkHttpClientConfiguration {
                override fun configOkHttp(context: Context, builder: OkHttpClient.Builder) {
                    if (BuildConfig.LOG_DEBUG) {
                        builder.addInterceptor(
                            HttpLoggingInterceptor().setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                        )
                    }
                    //`Http` 网络请求路径
                    val cacheFile: File = FileUtils.getCacheFile(context, "GITHUB_CACHE")
                    builder.cache(Cache(cacheFile, Api.OK_HTTP_CACHE_SIZE))
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
                    chain: Interceptor.Chain,
                    httpResult: String,
                    request: Request,
                    response: Response
                ): Response {
                    val mediaType: MediaType? = response.body?.contentType()

                    val isPage: String? = request.header("Page")

                    isPage.isNotNullOrEmpty().yes {
                        val page = Page<Any>()

                        //获取请求分页数据
                        val linkStr: String? = response.header("Link", "")
                        linkStr.isNotNullOrEmpty().yes {
                            val links: List<String> = linkStr!!.split(",")
                            links.forEach {
                                when {
                                    it.contains("prev") -> {
                                        page.prev = parseNumber(it)
                                    }
                                    it.contains("next") -> {
                                        page.next = parseNumber(it)
                                    }
                                    it.contains("last") -> {
                                        page.last = parseNumber(it)
                                    }
                                    it.contains("first") -> {
                                        page.first = parseNumber(it)
                                    }
                                }
                            }
                        }

                        if (httpResult.isNotNullOrEmpty()) {
                            if (GsonUtils.isJsonArrayData(httpResult)) {
                                page.result =
                                    GsonUtils.parserJsonToArrayBeans(httpResult, Any::class.java)
                            } else {
                                page.result =
                                    GsonUtils.parserJsonToBean(httpResult, Any::class.java)
                            }
                        }

                        return response.newBuilder()
                            .body(GsonUtils.toJsonString(page).toResponseBody(mediaType))
                            .build()

                    }.otherwise {
                        return response.newBuilder()
                            .body(httpResult.toResponseBody(mediaType))
                            .build()
                    }
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

    /**
     * 解析 [item] 字符串数据，获取分页页码信息 [Int]
     * @param item [String]
     * @return [Int]
     */
    private fun parseNumber(item: String?): Int {
        if (item == null) {
            return -1
        }
        val startFlag = "<"
        val endFlag = ">"
        val startIndex: Int = item.indexOf(startFlag)
        val endStart: Int = item.indexOf(endFlag)
        if (startIndex < 0 || endStart < 0) {
            return -1
        }
        val startStart: Int = startIndex + startFlag.length
        val url: String = item.substring(startStart, endStart)
        val value: String? = Uri.parse(url).getQueryParameter("page")
        return value?.toInt() ?: -1
    }
}