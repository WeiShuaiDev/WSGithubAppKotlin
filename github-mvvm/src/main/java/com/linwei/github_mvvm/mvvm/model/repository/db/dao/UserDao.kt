package com.linwei.github_mvvm.mvvm.model.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.OrgMemberEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.ReceivedEventEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.UserEventEntity

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


}