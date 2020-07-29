package com.linwei.cams.ext

import com.linwei.cams.http.callback.RxJavaCallback
import com.linwei.cams.http.model.BaseResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> Observable<BaseResponse<T>>.execute(
    subscriber: RxJavaCallback<T>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)
}







