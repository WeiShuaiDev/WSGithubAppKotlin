package com.linwei.cams.ext

import com.linwei.cams.http.callback.RxJavaCallback
import com.linwei.cams.http.model.BaseResponse
import com.linwei.cams.manager.DeviceManager
import com.linwei.cams.utils.PreferencesUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.reflect.jvm.jvmName

fun <T> Observable<BaseResponse<T>>.execute(
    subscriber: RxJavaCallback<T>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(subscriber)
}

/**
 * SharedPreference
 */
inline fun <reified R, T> R.pref(default: T) = PreferencesUtils(ctx, "", default, R::class.jvmName)

/**
 * 设备信息 [DeviceManager]
 */
fun deviceManager() = DeviceManager.getInstance()






