package com.linwei.cams_aac.http

import androidx.room.RoomDatabase

interface IAccDataRepository {

    /**
     * 根据 [databaseClass] 从 `Cache` 获取 `RoomDatabase` 对象
     * @param databaseClass [Class] DAO语句对象
     * @param dbName [String] 数据库名
     * @return T [RoomDatabase]
     */
    fun <T : RoomDatabase> obtainRoomDataBase(databaseClass: Class<T>, dbName: String): T
}