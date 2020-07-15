package com.linwei.cams_aac.http

import androidx.room.Room
import androidx.room.RoomDatabase
import com.linwei.cams.http.cache.Cache
import com.linwei.cams.http.cache.CacheType
import com.linwei.cams.http.repository.DataRepository

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class DataAacRepository : DataRepository(), IDataAacRepository {

    /**
     * `RoomDataBase` 对象缓存存储
     */
    private var mDataBaseCache: Cache<String, Any> =
        mCacheFactory.build(CacheType.databaseCacheType)


    @Suppress("UNCHECKED_CAST")
    @Synchronized
    override fun <T : RoomDatabase> obtainRoomDataBase(databaseClass: Class<T>, dbName: String): T {
        var roomDatabase: Any? =
            mDataBaseCache.get(
                databaseClass.canonicalName ?: databaseClass.simpleName
            )
        if (roomDatabase == null) {
            val builder: RoomDatabase.Builder<T> =
                Room.databaseBuilder(mApplication, databaseClass, dbName)
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