package com.linwei.cams_mvvm.mvvm

import android.app.Application
import androidx.room.RoomDatabase
import com.linwei.cams.http.repository.IDataRepository
import com.linwei.cams_mvvm.http.DataMvvmRepository
import com.linwei.cams_mvvm.http.IDataMvvmRepository
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description: MVVM架构 `Model` 模块，作为数据源提供多种方式获取数据，包括 `Retrofit` 网络数据源 、`Room` 数据库数据源
 *-----------------------------------------------------------------------
 */
open class BaseModel @Inject constructor(private val dataRepository: DataMvvmRepository) : IModel,
    IDataRepository, IDataMvvmRepository {

    /**
     * 根据 [serviceClass] 从 `Cache` 获取 `Retrofit` 接口对象
     * @param serviceClass [Class]
     * @return [T]
     */
    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T =
        dataRepository.obtainRetrofitService(serviceClass)

    /**
     * 根据 [serviceClass] 从 `RxCache` 获取 `Retrofit` 接口对象
     * @param serviceClass [Class]
     * @return [T]
     */
    override fun <T> obtainRxCacheService(serviceClass: Class<T>): T =
        dataRepository.obtainRxCacheService(serviceClass)

    /**
     * 根据 [databaseClass] 从 `Cache` 获取 `RoomDatabase` 对象
     * @param databaseClass [Class] DAO语句 `Class` 对象
     * @return T [RoomDatabase]
     */
    override fun <T : RoomDatabase> obtainRoomDataBase(databaseClass: Class<T>): T =
        dataRepository.obtainRoomDataBase(databaseClass)

    /**
     * 清除 `RxCache` 中数据
     */
    override fun clearAllRxCache() = dataRepository.clearAllRxCache()

    /**
     * ·Application` 中 `context`对象
     * 返回 [Application]
     */
    override fun fetchApplication(): Application =
        dataRepository.fetchApplication()

    /**
     * 资源回收
     */
    override fun onDestroy() {
    }

}