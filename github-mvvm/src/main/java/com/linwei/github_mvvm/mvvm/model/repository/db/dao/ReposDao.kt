package com.linwei.github_mvvm.mvvm.model.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.*

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
     * 查询仓库ReadMe数据
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

    /**
     *  ==================================================================
     * 查询仓库详情数据
     */
    @Query("SELECT * FROM repository_detail where full_name=:fullName and branch=:branch")
    fun queryRepositoryDetail(
        fullName: String,
        branch: String
    ): LiveData<RepositoryDetailEntity>

    /**
     * 增加仓库详情数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryDetail(vararg entity: RepositoryDetailEntity?)

    /**
     * 更新仓库详情数据
     */
    @Update
    fun updateRepositoryDetail(vararg entity: RepositoryDetailEntity?): Int

    /**
     * 删除仓库详情数据
     */
    @Delete
    fun deleteRepositoryDetail(vararg entity: RepositoryDetailEntity?)


    /**
     *  ==================================================================
     * 查询仓库活跃事件数据
     */
    @Query("SELECT * FROM repository_event where full_name=:fullName")
    fun queryRepositoryEvent(
        fullName: String
    ): LiveData<RepositoryEventEntity>

    /**
     * 增加仓库活跃事件数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryEvent(vararg entity: RepositoryEventEntity?)

    /**
     * 更新仓库活跃事件数据
     */
    @Update
    fun updateRepositoryEvent(vararg entity: RepositoryEventEntity?): Int

    /**
     * 删除仓库活跃事件数据
     */
    @Delete
    fun deleteRepositoryEvent(vararg entity: RepositoryEventEntity?)

    /**
     *  ==================================================================
     * 查询仓库提交信息数据
     */
    @Query("SELECT * FROM repository_commits where full_name=:fullName")
    fun queryRepositoryCommits(
        fullName: String
    ): LiveData<RepositoryCommitsEntity>

    /**
     * 增加仓库提交信息数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryCommits(vararg entity: RepositoryCommitsEntity?)

    /**
     * 更新仓库提交信息数据
     */
    @Update
    fun updateRepositoryCommits(vararg entity: RepositoryCommitsEntity?): Int

    /**
     * 删除仓库提交信息数据
     */
    @Delete
    fun deleteRepositoryCommits(vararg entity: RepositoryCommitsEntity?)


    /**
     *  ==================================================================
     * 查询仓库Issue信息数据
     */
    @Query("SELECT * FROM repository_issue where full_name=:fullName and state=:state")
    fun queryRepositoryIssue(
        fullName: String,
        state: String
    ): LiveData<RepositoryIssueEntity>

    /**
     * 增加仓库Issue信息数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryIssue(vararg entity: RepositoryIssueEntity?)

    /**
     * 更新仓库Issue信息数据
     */
    @Update
    fun updateRepositoryIssue(vararg entity: RepositoryIssueEntity?): Int

    /**
     * 删除仓库Issue信息数据
     */
    @Delete
    fun deleteRepositoryIssue(vararg entity: RepositoryIssueEntity?)

    /**
     *  ==================================================================
     * 查询仓库分支Fork信息数据
     */
    @Query("SELECT * FROM repository_fork where full_name=:fullName")
    fun queryRepositoryFork(
        fullName: String
    ): LiveData<RepositoryForkEntity>

    /**
     * 增加仓库分支Fork信息数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryFork(vararg entity: RepositoryForkEntity?)

    /**
     * 更新仓库分支Fork信息数据
     */
    @Update
    fun updateRepositoryFork(vararg entity: RepositoryForkEntity?): Int

    /**
     * 删除仓库分支Fork信息数据
     */
    @Delete
    fun deleteRepositoryFork(vararg entity: RepositoryForkEntity?)


    /**
     *  ==================================================================
     * 查询仓库用户仓库列表数据
     */
    @Query("SELECT * FROM user_repos where user_name=:userName and sort=:sort")
    fun queryUserRepos(
        userName: String,
        sort: String
    ): LiveData<UserReposEntity>

    /**
     * 增加仓库用户仓库列表数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserRepos(vararg entity: UserReposEntity?)

    /**
     * 更新仓库用户仓库列表数据
     */
    @Update
    fun updateUserRepos(vararg entity: UserReposEntity?): Int

    /**
     * 删除仓库用户仓库列表数据
     */
    @Delete
    fun deleteUserRepos(vararg entity: UserReposEntity?)

    /**
     *  ==================================================================
     * 查询用户收藏数据
     */
    @Query("SELECT * FROM user_stared where user_name=:userName")
    fun queryUserStared(
        userName: String
    ): LiveData<UserStaredEntity>

    /**
     * 增加用户收藏数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserStared(vararg entity: UserStaredEntity?)

    /**
     * 更新用户收藏数据
     */
    @Update
    fun updateUserStared(vararg entity: UserStaredEntity?): Int

    /**
     * 删除用户收藏数据
     */
    @Delete
    fun deleteUserStared(vararg entity: UserStaredEntity?)
}