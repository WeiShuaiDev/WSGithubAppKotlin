package com.linwei.cams_mvvm.http

import androidx.room.RoomDatabase

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: MVVM架构数据处理库接口定义，增加 `Room` 数据库处理。
 *-----------------------------------------------------------------------
 */
interface IDataMvvmRepository {

    /**
     * 根据 [databaseClass] 从 `Cache` 获取 `RoomDatabase` 对象
     * @param databaseClass [Class] DAO语句对象
     * @return T [RoomDatabase]
     */
    fun <T : RoomDatabase> obtainRoomDataBase(
        databaseClass: Class<T>
    ): T
}