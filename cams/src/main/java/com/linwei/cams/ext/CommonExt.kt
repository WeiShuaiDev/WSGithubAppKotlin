package com.linwei.cams.ext

import com.linwei.cams.http.model.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import rx.Observable


////Kotlin通用扩展
//fun <T> Observable<BaseResponse<T>>.excute(subscriber: BaseSubscriber<T>, lifecycleProvider: LifecycleProvider<*>) {
//    this.convert().subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .compose(lifecycleProvider.bindToLifecycle())
//            .subscribe(subscriber)
//}

/*
//    扩展数据转换
// */
//fun <T> Observable<BaseResponse<T>>.convert(): Observable<T> {
//    return this.flatMap(BaseFunc())
//}
//
///*
//    扩展Boolean类型数据转换
// */
//fun <T> Observable<BaseResponse<T>>.convertBoolean(): Observable<Boolean> {
//    return this.flatMap(BaseFuncBoolean())
//}



