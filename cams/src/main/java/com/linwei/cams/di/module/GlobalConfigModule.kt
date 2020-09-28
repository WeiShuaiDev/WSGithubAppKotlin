package com.linwei.cams.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.linwei.cams.http.GlobalHttpHandler
import com.linwei.cams.http.adapter.LiveDataCallAdapterFactory
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.http.cache.CacheType.Companion.ACTIVITY_CACHE_TYPE_ID
import com.linwei.cams.http.cache.CacheType.Companion.CACHE_SERVICE_CACHE_TYPE_ID
import com.linwei.cams.http.cache.CacheType.Companion.DATABASE_CACHE_TYPE_ID
import com.linwei.cams.http.cache.CacheType.Companion.EXTRAS_TYPE_ID
import com.linwei.cams.http.cache.CacheType.Companion.FRAGMENT_CACHE_TYPE_ID
import com.linwei.cams.http.cache.CacheType.Companion.RETROFIT_SERVICE_CACHE_TYPE_ID
import com.linwei.cams.http.cache.kinds.LruCache
import com.linwei.cams.http.model.BaseResponse
import com.linwei.cams.utils.FileUtils
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.internal.threadFactory
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.*
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: 框架独创的建造者模式,可向框架中注入外部配置的自定义参数
 *-----------------------------------------------------------------------
 */
@Module
class GlobalConfigModule(private val mBuilder: Builder) {

    companion object {
        fun builder(): Builder {
            return Builder()
        }
    }

    @Singleton
    @Provides
    fun provideCacheFactory(application: Application): Cache.Factory {
        return mBuilder.cacheFactory ?: object : Cache.Factory {
            override fun <K, V> build(type: CacheType): Cache<K, V> {
                return when (type.getCacheTypeId()) {
                    RETROFIT_SERVICE_CACHE_TYPE_ID,
                    CACHE_SERVICE_CACHE_TYPE_ID,
                    DATABASE_CACHE_TYPE_ID,
                    EXTRAS_TYPE_ID,
                    ACTIVITY_CACHE_TYPE_ID,
                    FRAGMENT_CACHE_TYPE_ID -> {
                        LruCache(type.calculateCacheSize(application))
                    }
                    else -> {
                        LruCache(type.calculateCacheSize(application))
                    }
                }
            }
        }
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): HttpUrl? {
        return mBuilder.baseUrl
    }

    @Singleton
    @Provides
    fun provideCacheDir(application: Application): File {
        return FileUtils.getCacheDirectory(application)
    }

    @Singleton
    @Provides
    fun provideInterceptors(): MutableList<Interceptor> {
        return mBuilder.interceptors
    }

    @Singleton
    @Provides
    fun provideGsonConfiguration(): AppModule.GsonConfiguration? {
        return mBuilder.gsonConfiguration ?: object : AppModule.GsonConfiguration {
            override fun configGson(context: Context, builder: GsonBuilder) {
                builder.registerTypeHierarchyAdapter(
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
                    })
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitConfiguration(adapterFactory: LiveDataCallAdapterFactory): ClientModule.RetrofitConfiguration? {
        return mBuilder.retrofitConfiguration ?: object : ClientModule.RetrofitConfiguration {
            override fun configRetrofit(context: Context, builder: Retrofit.Builder) {
                builder.addCallAdapterFactory(adapterFactory)
            }
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitServiceDelegate(): ClientModule.RetrofitServiceDelegate {
        return mBuilder.retrofitServiceDelegate ?: object : ClientModule.RetrofitServiceDelegate {
            override fun <T> createRetrofitService(retrofit: Retrofit, serviceClass: Class<T>): T? {
                return null
            }
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClientConfiguration(): ClientModule.OkHttpClientConfiguration? {
        return mBuilder.okHttpClientConfiguration
    }

    @Singleton
    @Provides
    fun provideRxCacheConfiguration(): ClientModule.RxCacheConfiguration? {
        return mBuilder.rxCacheConfiguration
    }

    @Singleton
    @Provides
    fun provideGlobalHttpHandler(): GlobalHttpHandler {
        return mBuilder.globalHttpHandler ?: GlobalHttpHandler.Emtry
    }

    @Singleton
    @Provides
    fun provideExecutorService(): ExecutorService? {
        Executors.newCachedThreadPool()
        return mBuilder.executorService ?: ThreadPoolExecutor(
            5, Int.MAX_VALUE, 60,
            TimeUnit.MINUTES, SynchronousQueue(),
            threadFactory("frame Executor", false)
        )
    }

    class Builder {
        var baseUrl: HttpUrl? = null
        var cacheFactory: Cache.Factory? = null
        var cacheDir: File? = null
        var interceptors: MutableList<Interceptor> = mutableListOf()

        var gsonConfiguration: AppModule.GsonConfiguration? = null
        var retrofitConfiguration: ClientModule.RetrofitConfiguration? = null
        var retrofitServiceDelegate: ClientModule.RetrofitServiceDelegate? = null
        var okHttpClientConfiguration: ClientModule.OkHttpClientConfiguration? = null
        var rxCacheConfiguration: ClientModule.RxCacheConfiguration? = null
        var globalHttpHandler: GlobalHttpHandler? = null
        var executorService: ExecutorService? = null

        fun httpUrl(url: String?): Builder {
            if (url.isNullOrEmpty()) {
                throw NullPointerException("BaseUrl can not be empty")
            }
            this.baseUrl = url.toHttpUrlOrNull()
            return this
        }

        fun cacheFactory(cacheFactory: Cache.Factory?): Builder {
            this.cacheFactory = cacheFactory
            return this
        }

        fun cacheDir(cacheDir: File?): Builder {
            this.cacheDir = cacheDir
            return this
        }

        fun interceptors(interceptor: Interceptor?): Builder {
            interceptor?.let {
                if (!interceptors.contains(interceptor)) {
                    interceptors.add(interceptor)
                }
            }
            return this
        }

        fun retrofitConfiguration(retrofitConfiguration: ClientModule.RetrofitConfiguration?): Builder {
            this.retrofitConfiguration = retrofitConfiguration
            return this
        }

        fun retrofitServiceDelegate(retrofitServiceDelegate: ClientModule.RetrofitServiceDelegate?): Builder {
            this.retrofitServiceDelegate = retrofitServiceDelegate
            return this
        }

        fun okHttpClientConfiguration(okHttpClientConfiguration: ClientModule.OkHttpClientConfiguration?): Builder {
            this.okHttpClientConfiguration = okHttpClientConfiguration
            return this
        }

        fun rxCacheConfiguration(rxCacheConfiguration: ClientModule.RxCacheConfiguration?): Builder {
            this.rxCacheConfiguration = rxCacheConfiguration
            return this
        }

        fun globalHttpHandler(globalHttpHandler: GlobalHttpHandler?): Builder {
            this.globalHttpHandler = globalHttpHandler
            return this
        }

        fun gsonConfiguration(gsonConfiguration: AppModule.GsonConfiguration?): Builder {
            this.gsonConfiguration = gsonConfiguration
            return this
        }

        fun executorService(executorService: ExecutorService?): Builder {
            this.executorService = executorService
            return this
        }

        fun build(): GlobalConfigModule {
            return GlobalConfigModule(this)
        }
    }
}