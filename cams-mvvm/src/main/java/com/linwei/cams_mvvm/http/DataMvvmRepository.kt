package com.linwei.cams_mvvm.http

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.http.repository.DataRepository
import com.linwei.cams_mvvm.GlobalConstant
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:  MVVM架构数据处理库，增加 `Room` 数据库处理。
 *-----------------------------------------------------------------------
 */
class DataMvvmRepository @Inject constructor() : DataRepository(), IDataMvvmRepository {

    /**
     * `RoomDataBase` 对象缓存存储
     */
    private lateinit var mDataBaseCache: Cache<String, Any>

    /**
     * 根据 [databaseClass] 从 `Cache` 获取 `RoomDatabase` 对象
     * @param databaseClass [Class] DAO语句对象
     * @return T [RoomDatabase]
     */
    @Suppress("UNCHECKED_CAST")
    @Synchronized
    override fun <T : RoomDatabase> obtainRoomDataBase(databaseClass: Class<T>): T {
        mDataBaseCache = mCacheFactory.build(CacheType.databaseCacheType)

        var roomDatabase: Any? =
            mDataBaseCache.get(
                databaseClass.canonicalName ?: databaseClass.simpleName
            )
        if (roomDatabase == null) {
            val builder: RoomDatabase.Builder<T> =
                Room.databaseBuilder(
                    mApplication, databaseClass, GlobalConstant.ROOM_DATABASE_NAME
                )
            builder.allowMainThreadQueries() //允许在主线程中查询
            //TODO 通过Dagger2扩展进来
            builder.addCallback(object : RoomDatabase.Callback() {
            })
            roomDatabase = builder.build()

            //保存 `RoomDataBase` 对象到 `Cache` 中
            mDataBaseCache.put(
                databaseClass.canonicalName ?: databaseClass.simpleName,
                roomDatabase
            )
        }
        return roomDatabase as T
    }
}