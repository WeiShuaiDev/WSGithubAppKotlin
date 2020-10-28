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
 * @Description:Issue 详情表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "issue_detail", primaryKeys = ["full_name", "number"])
data class IssueDetailEntity(

    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    var fullName: String? = null,

    @ColumnInfo(name = "number")
    @SerializedName("number")
    var number: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)