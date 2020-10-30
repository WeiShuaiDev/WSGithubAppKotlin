package com.linwei.github_mvvm.mvvm.model.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.*

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
     * ==================================================================
     * 查询接收事件数据
     */
    @Query("SELECT * FROM received_event where id=:id")
    fun queryReceivedEvent(id: Int): LiveData<ReceivedEventEntity>

    /**
     * 增加接收事件数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReceivedEvent(vararg entity: ReceivedEventEntity?)

    /**
     * 更新接收事件数据
     */
    @Update
    fun updateReceivedEvent(vararg entity: ReceivedEventEntity?): Int

    /**
     * 删除接收事件数据
     */
    @Delete
    fun deleteReceivedEvent(vararg entity: ReceivedEventEntity?)


    /**
     * ==================================================================
     * 查询关注数据
     */
    @Query("SELECT * FROM org_member where org=:org")
    fun queryOrgMember(org: String): LiveData<OrgMemberEntity>

    /**
     * 增加关注数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrgMember(vararg entity: OrgMemberEntity?)

    /**
     * 更新关注数据
     */
    @Update
    fun updateOrgMember(vararg entity: OrgMemberEntity?): Int

    /**
     * 删除关注数据
     */
    @Delete
    fun deleteOrgMember(vararg entity: OrgMemberEntity?)

    /**
     * ==================================================================
     * 查询产生事件数据
     */
    @Query("SELECT * FROM user_event where user_name=:userName")
    fun queryUserEvent(userName: String): LiveData<UserEventEntity>

    /**
     * 增加产生事件数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserEvent(vararg entity: UserEventEntity?)

    /**
     * 更新产生事件数据
     */
    @Update
    fun updateUserEvent(vararg entity: UserEventEntity?): Int

    /**
     * 删除产生事件数据
     */
    @Delete
    fun deleteUserEvent(vararg entity: UserEventEntity?)

    /**
     * ==================================================================
     * 查询用户粉丝数据
     */
    @Query("SELECT * FROM user_follower where user_name=:userName")
    fun queryUserFollower(userName: String): LiveData<UserFollowerEntity>

    /**
     * 增加用户粉丝数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFollower(vararg entity: UserFollowerEntity?)

    /**
     * 更新用户粉丝数据
     */
    @Update
    fun updateUserFollower(vararg entity: UserFollowerEntity?): Int

    /**
     * 删除用户粉丝数据
     */
    @Delete
    fun deleteUserFollower(vararg entity: UserFollowerEntity?)

    /**
     * ==================================================================
     * 查询用户关注数据
     */
    @Query("SELECT * FROM user_followed where user_name=:userName")
    fun queryUserFollowed(userName: String): LiveData<UserFollowedEntity>

    /**
     * 增加用户关注数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFollowed(vararg entity: UserFollowedEntity?)

    /**
     * 更新用户关注数据
     */
    @Update
    fun updateUserFollowed(vararg entity: UserFollowedEntity?): Int

    /**
     * 删除用户关注数据
     */
    @Delete
    fun deleteUserFollowed(vararg entity: UserFollowedEntity?)

    /**
     * ==================================================================
     * 查询仓库收藏数据
     */
    @Query("SELECT * FROM repository_star where full_name=:fullName")
    fun queryRepositoryStar(fullName: String): LiveData<RepositoryStarEntity>

    /**
     * 增加仓库收藏数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryStar(vararg entity: RepositoryStarEntity?)

    /**
     * 更新仓库收藏数据
     */
    @Update
    fun updateRepositoryStar(vararg entity: RepositoryStarEntity?): Int

    /**
     * 删除仓库收藏数据
     */
    @Delete
    fun deleteRepositoryStar(vararg entity: RepositoryStarEntity?)

    /**
     * ==================================================================
     * 查询仓库订阅数据
     */
    @Query("SELECT * FROM repository_watcher where full_name=:fullName")
    fun queryRepositoryWatcher(fullName: String): LiveData<RepositoryWatcherEntity>

    /**
     * 增加仓库订阅数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryWatcher(vararg entity: RepositoryWatcherEntity?)

    /**
     * 更新仓库订阅数据
     */
    @Update
    fun updateRepositoryWatcher(vararg entity: RepositoryWatcherEntity?): Int

    /**
     * 删除仓库订阅数据
     */
    @Delete
    fun deleteRepositoryWatcher(vararg entity: RepositoryWatcherEntity?)


}