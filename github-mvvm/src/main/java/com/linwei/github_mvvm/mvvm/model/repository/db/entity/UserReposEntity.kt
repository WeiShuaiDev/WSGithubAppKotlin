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
 * @Description: 用户仓库表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "user_repos", primaryKeys = ["user_name", "sort"])
data class UserReposEntity(
    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    var userName: String? = null,

    @ColumnInfo(name = "sort")
    @SerializedName("sort")
    var sort: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)