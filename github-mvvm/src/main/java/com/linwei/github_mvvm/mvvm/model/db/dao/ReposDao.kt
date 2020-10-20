package com.linwei.github_mvvm.mvvm.model.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.db.entity.TrendEntity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 仓库数据库
 *-----------------------------------------------------------------------
 */
interface ReposDao {

    /**
     * 查询趋势数据
     */
    @Query("SELECT * FROM trend where language_type=:languageType and since=:since")
    fun queryReceivedEvent(languageType: String, since: String): LiveData<TrendEntity>

    /**
     * 增加趋势数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReceivedEvent(vararg entity: TrendEntity?)

    /**
     * 更新趋势数据
     */
    @Update
    fun updateReceivedEvent(vararg entity: TrendEntity?): Int

    /**
     * 删除趋势数据
     */
    @Delete
    fun deleteReceivedEvent(vararg entity: TrendEntity?)
}