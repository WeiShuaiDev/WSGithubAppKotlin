package com.linwei.github_mvvm.mvvm.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linwei.github_mvvm.mvvm.model.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.db.entity.EventEntity
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/30
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}