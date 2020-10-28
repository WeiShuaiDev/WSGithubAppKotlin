package com.linwei.github_mvvm.mvvm.model.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/28
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Issue 评论表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "issue_comment", primaryKeys = ["full_name", "number"])
data class IssueCommentEntity(

    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    var fullName: String? = null,

    @ColumnInfo(name = "number")
    @SerializedName("number")
    var number: String? = null,

    @ColumnInfo(name = "comment_id")
    @SerializedName("comment_id")
    var commentId: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)