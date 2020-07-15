package com.linwei.cams_aac.acc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 生产 [ViewModel]工厂类，通过 `Dagger2` 注入ViewModel对象，把 `Provider<ViewModel>` 存储到 Map集合中 `value`，
 *               `Class<in ViewModel>` 存储到 Map 集合中 `key`
 *-----------------------------------------------------------------------
 */
class ViewModelFactory(
    private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            run breaking@{
                creators.forEach { (key: Class<out ViewModel>, value: Provider<ViewModel>) ->
                    if (modelClass.isAssignableFrom(key)) {
                        creator = value
                        return@breaking
                    }
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("Unknown model class $modelClass")
        }
        try {
            return creator?.get() as T
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }
}