package com.linwei.github_mvvm.mvvm.model.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.RepositoryDetailReadmeEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.TrendEntity

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
     *  ==================================================================
     * 查询趋势数据
     */
    @Query("SELECT * FROM trend where language_type=:languageType and since=:since")
    fun queryTrend(languageType: String, since: String): LiveData<TrendEntity>

    /**
     * 增加趋势数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTrend(vararg entity: TrendEntity?)

    /**
     * 更新趋势数据
     */
    @Update
    fun updateTrend(vararg entity: TrendEntity?): Int

    /**
     * 删除趋势数据
     */
    @Delete
    fun deleteTrend(vararg entity: TrendEntity?)


    /**
     *  ==================================================================
     * 查询ReadMe数据
     */
    @Query("SELECT * FROM repository_detail_readme where full_name=:fullName and branch=:branch")
    fun queryRepositoryDetailReadme(
        fullName: String,
        branch: String
    ): LiveData<RepositoryDetailReadmeEntity>

    /**
     * 增加ReadMe数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryDetailReadme(vararg entity: RepositoryDetailReadmeEntity?)

    /**
     * 更新ReadMe数据
     */
    @Update
    fun updateRepositoryDetailReadme(vararg entity: RepositoryDetailReadmeEntity?): Int

    /**
     * 删除ReadMe数据
     */
    @Delete
    fun deleteRepositoryDetailReadme(vararg entity: RepositoryDetailReadmeEntity?)
}