package com.linwei.cams_aac.http

import androidx.room.RoomDatabase

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/15
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface IDataAacRepository {

    /**
     * 根据 [databaseClass] 从 `Cache` 获取 `RoomDatabase` 对象
     * @param databaseClass [Class] DAO语句对象
     * @return T [RoomDatabase]
     */
    fun <T : RoomDatabase> obtainRoomDataBase(
        databaseClass: Class<T>
    ): T
}