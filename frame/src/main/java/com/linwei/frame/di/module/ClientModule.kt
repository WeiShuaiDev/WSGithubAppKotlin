package com.linwei.frame.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.linwei.frame.http.RetrofitFactory
import com.linwei.frame.http.adapter.LiveDataCallAdapterFactory
import com.linwei.frame.http.interceptor.CommonInterceptor
import com.linwei.frame.utils.FileUtils
import com.squareup.otto.Produce
import dagger.Module
import dagger.Provides
import io.rx_cache2.internal.RxCache
import io.victoralbertos.jolyglot.GsonSpeaker
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/22
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: 提供一些三方库客户端实例
 *-----------------------------------------------------------------------
 */
@Module
class ClientModule {

    /**
     * 创建 `OkHttpBuilder` 对象，并配置拦截器 [CommonInterceptor] 通用拦截器,对请求发起链接超时配置
     * [connectTimeOut],流写入超时配置 [writeTimeOut],流读取超时配置 [readTimeOut],最后通过调用 [build] 方法创建 [OkHttpClient] 对象
     * @param application [Application]
     * @param builder [OkHttpClient.Builder]
     * @param configuration [OkHttpClientConfiguration] `OkHttp`扩展配置
     * @param interceptors [MutableList]  拦截器
     * @param executorService [ExecutorService] 连接池
     * @return [OkHttpClient] 对象
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(
        application: Application,
        builder: OkHttpClient.Builder,
        configuration: OkHttpClientConfiguration?,
        interceptors: MutableList<Interceptor>,
        executorService: ExecutorService?
    ): OkHttpClient {
        return builder.also {

            it.connectTimeout(RetrofitFactory.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            it.writeTimeout(RetrofitFactory.WRITE_TIME_OUT, TimeUnit.SECONDS)
            it.readTimeout(RetrofitFactory.READ_TIME_OUT, TimeUnit.SECONDS)

            //通用拦截器，对请求头配置处理。
            it.addInterceptor(CommonInterceptor())

            interceptors.forEach { interceptor ->
                it.addInterceptor(interceptor)
            }

            //配置线程池
            if (executorService != null)
                it.dispatcher(Dispatcher(executorService))

            configuration?.configOkHttp(application, builder)

        }.build()
    }

    /**
     * 创建 `Retrofit` 对象，并进行域名，适配器配置
     * @param application [Application]
     * @param builder [Retrofit.Builder]
     * @param configuration [RetrofitConfiguration] `Retrofit`扩展配置
     * @param adapterFactory [LiveDataCallAdapterFactory] `ListData`适配器
     * @param converterFactory [GsonConverterFactory] `Gson`转换适配器
     * @param client [OkHttpClient]
     * @param baseUrl [String] 域名
     * @return [Retrofit] 对象
     */
    @Singleton
    @Provides
    fun provideRetrofit(
        application: Application,
        builder: Retrofit.Builder,
        configuration: RetrofitConfiguration?,
        adapterFactory: LiveDataCallAdapterFactory?,
        converterFactory: GsonConverterFactory?,
        client: OkHttpClient,
        baseUrl: String?
    ): Retrofit {
        return builder.also {
            it.baseUrl(baseUrl)
            it.client(client)
            if (adapterFactory != null)
                it.addCallAdapterFactory(adapterFactory)

            if (converterFactory != null)
                it.addConverterFactory(converterFactory)

            configuration?.configRetrofit(application, builder)

        }.build()
    }

    /**
     * 创建 `RxCache` 对象
     * @param application [Application]
     * @param configuration [RxCacheConfiguration] `RxCache`扩展配置
     * @param cacheDirectory [File] 缓存文件
     * @param gson [Gson]
     * @return  [RxCache] 对象
     */
    @Singleton
    @Produce
    fun provideRxCache(
        application: Application, configuration: RxCacheConfiguration?,
        @Named("RxCacheDirectory") cacheDirectory: File, gson: Gson
    ): RxCache {
        val builder = RxCache.Builder()
        val rxCache: RxCache? = configuration?.configRxCache(application, builder)
        if (rxCache != null) {
            return rxCache
        }
        return builder.persistence(cacheDirectory, GsonSpeaker(gson))
    }

    /**
     * 创建 `RxCache` 缓存文件
     * @param cacheDir [File] 缓存文件
     * @return [File] 指定 [cacheDir] 路径,生成文件名 `RxCache` 文件
     */
    @Singleton
    @Produce
    @Named("RxCacheDirectory")
    fun provideRxCacheDirectory(cacheDir: File): File =
        File(FileUtils.makeDirs(cacheDir), "RxCache")

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder = Retrofit.Builder()

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient().newBuilder()

    @Singleton
    @Provides
    fun provideLiveDataCallAdapterFactory(): LiveDataCallAdapterFactory =
        LiveDataCallAdapterFactory()

    @Singleton
    @Provides
    fun provideGSonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    /**
     * [Retrofit] 配置,开发者通过实现 [RetrofitConfiguration] 接口中方法，在调用 [Retrofit.Builder] 对象进行配置，
     *  这样框架内部通过回调，获取配置后对象。
     */
    interface RetrofitConfiguration {
        /**
         * 对 [builder] 参数对象进行配置
         * @param context [Context]
         * @param builder [Retrofit.Builder]
         */
        fun configRetrofit(context: Context, builder: Retrofit.Builder)
    }

    /**
     * [OkHttpClient] 配置,开发者通过实现 [OkhttpConfiguration] 接口中方法，在调用 [OkHttpClient.Builder] 对象进行配置，
     *  这样框架内部通过回调，获取配置后对象。
     */
    interface OkHttpClientConfiguration {
        /**
         * 对 [builder] 参数对象进行配置
         *  @param context [Context]
         *  @param builder [OkHttpClient.Builder]
         */
        fun configOkHttp(context: Context, builder: OkHttpClient.Builder)
    }

    /**
     * [RxCahe] 配置,开发者通过实现 [RxCacheConfiguration] 接口中方法，在调用 [RxCache.Builder] 对象进行配置，
     *  这样框架内部通过回调，获取配置后对象。
     */
    interface RxCacheConfiguration {
        /**
         * 对 [builder] 参数对象进行配置
         * @param context [Context]
         * @param builder [RxCache.Builder]
         * @return RxCache 对象
         */
        fun configRxCache(context: Context, builder: RxCache.Builder): RxCache
    }
}