package com.linwei.github_mvvm.mvvm.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
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
@Dao
interface UserDao {
    /**
     * 查询事件数据
     */
    @Query("SELECT * FROM event")
    fun queryEvent(): LiveData<EventEntity>

}