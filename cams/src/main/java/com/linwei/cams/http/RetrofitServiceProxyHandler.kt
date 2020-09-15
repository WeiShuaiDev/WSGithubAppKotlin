package com.linwei.cams.http

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/1
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class RetrofitServiceProxyHandler<T>(
    private val retrofit: Retrofit,
    private val serviceClass: Class<T>
) : InvocationHandler {

    private var mRetrofitService: T? = null


    override fun invoke(proxy: Any?, method: Method?, args: Array<Any>?): Any? {
        var params: Array<Any>? = args
        if (params == null) {
            params = emptyArray()
        }

        // 加一层 defer 由外部去控制耗时方法以及网络请求所处线程，
        method?.returnType?.let {
            when (it) {
                Observable::class.java -> {
                    return Observable.defer {
                        method.invoke(create(), *params) as Observable<*>
                    }
                }
                Single::class.java -> {
                    return Single.defer {
                        method.invoke(create(), *params) as Single<*>
                    }
                }
                LiveData::class.java -> {
                    return method.invoke(create(), *params) as LiveData<*>
                }
                else -> {
                    return method.invoke(create(), *params)
                }
            }
        }
        return method?.invoke(create(), *params)
    }


    /**
     * 根据 [service] `Class`对象，获取具体实例化对象。
     * @return [Any]
     */
    fun create(): T? {

        if (mRetrofitService != null) {
            return mRetrofitService
        }

        synchronized(this) {
            if (mRetrofitService != null) {
                return mRetrofitService
            }
            mRetrofitService = retrofit.create(serviceClass)
        }

        return mRetrofitService
    }


}