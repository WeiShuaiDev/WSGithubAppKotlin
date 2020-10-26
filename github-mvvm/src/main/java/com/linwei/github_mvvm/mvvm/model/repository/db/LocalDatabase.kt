package com.linwei.github_mvvm.mvvm.model.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.ReposDao
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.OrgMemberEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.TrendEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.UserEventEntity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/30
 * @Contact: linwei9605@gmail.com
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Database(
    entities = [ReceivedEventEntity::class, TrendEntity::class, OrgMemberEntity::class, UserEventEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    /**
     * 用户数据库
     */
    abstract fun userDao(): UserDao

    /**
     * 仓库数据库
     */
    abstract fun reposDao(): ReposDao


}