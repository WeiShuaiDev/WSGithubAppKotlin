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
 * @Description: 用户收藏表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "user_stared")
data class UserStaredEntity(
    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    @PrimaryKey
    var userName: String? = null,

    @ColumnInfo(name = "sort")
    @SerializedName("sort")
    var sort: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)