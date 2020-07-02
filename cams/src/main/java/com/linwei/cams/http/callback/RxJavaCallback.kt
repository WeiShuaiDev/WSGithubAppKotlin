package com.linwei.cams.http.callback

import com.linwei.cams.http.config.ApiStateConstant
import com.linwei.cams.http.model.BaseResponse
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/16
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: Retrofit+RxKotlin 中根据设置不同适配器 [RxJava2CallAdapterFactory]，请求接口返回数据 `Observable<BaseResponse<Any>>`,
 *              通过使用 `RxJavaCallback` 回调接口，处理网络请求。
 *-----------------------------------------------------------------------
 */
abstract class RxJavaCallback<T> : Observer<BaseResponse<T>> {
    override fun onComplete() {
    }

    override fun onSubscribe(disposable: Disposable) {
        disposable.isDisposed
    }

    override fun onNext(response: BaseResponse<T>) {
        val data: BaseResponse<T> = response
        if (ApiStateConstant.REQUEST_SUCCESS == data.code) {
            onSuccess(data.code, data.result)
        } else {
            onFailure(data.code, data.message)
        }
    }

    override fun onError(throwable: Throwable) {
        throwable.message?.let {
            onFailure(ApiStateConstant.REQUEST_FAILURE, it)
        }
    }


    /**
     * 接口请求成功，回调 [onSuccess] 方法
     * @param code [String] 成功状态码
     * @param data [T] 响应数据
     */
    open fun onSuccess(code: String?, data: T?) {}

    /**
     * 接口请求失败，回调 [onFailure] 方法
     * @param code [String] 失败状态码
     * @param message [String] 失败信息
     */
    open fun onFailure(code: String?, message: String?) {}

}