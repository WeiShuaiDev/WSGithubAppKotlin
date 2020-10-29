package com.linwei.github_mvvm.mvvm.model.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/29
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 仓库Issue表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "repository_issue", primaryKeys = ["full_name", "state"])
data class RepositoryIssueEntity(
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    var fullName: String? = null,

    @ColumnInfo(name = "state")
    @SerializedName("state")
    var state: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)