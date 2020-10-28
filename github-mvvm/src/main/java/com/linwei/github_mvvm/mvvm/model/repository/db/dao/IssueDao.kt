package com.linwei.github_mvvm.mvvm.model.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.IssueCommentEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.IssueDetailEntity
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.TrendEntity

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/28
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
interface IssueDao {

    /**
     * ==================================================================
     * 查询Issue详情数据
     */
    @Query("SELECT * FROM issue_detail where full_name=:fullName and number=:number")
    fun queryIssueDetail(fullName: String, number: String): LiveData<IssueDetailEntity>

    /**
     * 增加Issue详情数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssueDetail(vararg entity: IssueDetailEntity?)

    /**
     * 更新Issue详情数据
     */
    @Update
    fun updateIssueDetail(vararg entity: IssueDetailEntity?): Int

    /**
     * 删除Issue详情数据
     */
    @Delete
    fun deleteIssueDetail(vararg entity: IssueDetailEntity?)

    /**
     * ==================================================================
     * 查询Issue评论数据
     */
    @Query("SELECT * FROM issue_comment where full_name=:fullName and number=:number")
    fun queryIssueComment(fullName: String, number: String): LiveData<IssueCommentEntity>

    /**
     * 增加Issue评论数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIssueComment(vararg entity: IssueCommentEntity?)

    /**
     * 更新Issue评论数据
     */
    @Update
    fun updateIssueComment(vararg entity: IssueCommentEntity?): Int

    /**
     * 删除Issue评论数据
     */
    @Delete
    fun deleteIssueComment(vararg entity: IssueCommentEntity?)


}