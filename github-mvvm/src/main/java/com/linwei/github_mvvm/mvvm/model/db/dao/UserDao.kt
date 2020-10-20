package com.linwei.github_mvvm.mvvm.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.db.entity.ReceivedEventEntity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/30
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 用户数据库
 *-----------------------------------------------------------------------
 */
@Dao
interface UserDao {
    /**
     * 查询事件数据
     */
    @Query("SELECT * FROM received_event where id=:id")
    fun queryReceivedEvent(id: Int): LiveData<ReceivedEventEntity>

    /**
     * 增加事件数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReceivedEvent(vararg entity: ReceivedEventEntity?)

    /**
     * 更新事件数据
     */
    @Update
    fun updateReceivedEvent(vararg entity: ReceivedEventEntity?): Int

    /**
     * 删除事件数据
     */
    @Delete
    fun deleteReceivedEvent(vararg entity: ReceivedEventEntity?)

}