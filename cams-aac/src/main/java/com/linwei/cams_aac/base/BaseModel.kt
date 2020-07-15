package com.linwei.cams_aac.base

import android.app.Application
import androidx.room.RoomDatabase
import com.linwei.cams.http.repository.IDataRepository
import com.linwei.cams_aac.aac.IModel
import com.linwei.cams_aac.http.DataAacRepository
import com.linwei.cams_aac.http.IDataAacRepository
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com
 * @Github: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class BaseModel() : IModel, IDataRepository, IDataAacRepository {
    lateinit var mDataRepository: DataAacRepository

    @Inject
    constructor(dataRepository: DataAacRepository) : this() {
        this.mDataRepository = dataRepository
    }


    override fun <T> obtainRetrofitService(serviceClass: Class<T>): T =
        mDataRepository.obtainRetrofitService(serviceClass)


    override fun <T> obtainRxCacheService(serviceClass: Class<T>): T =
        mDataRepository.obtainRxCacheService(serviceClass)


    override fun <T : RoomDatabase> obtainRoomDataBase(databaseClass: Class<T>, dbName: String): T =
        mDataRepository.obtainRoomDataBase(databaseClass, dbName)


    override fun clearAllRxCache() = mDataRepository.clearAllRxCache()


    override fun fetchApplication(): Application =
        mDataRepository.fetchApplication()


    override fun onDestroy() {
    }

}