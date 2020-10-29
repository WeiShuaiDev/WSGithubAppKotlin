package com.linwei.github_mvvm.mvvm.model.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/29
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 仓库提交信息表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "repository_commits")
data class RepositoryCommitsEntity(
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    @PrimaryKey
    var fullName: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)