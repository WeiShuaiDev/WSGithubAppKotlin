package com.linwei.github_mvvm.mvvm.model.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/30
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 用户粉丝表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "user_follower")
data class UserFollowerEntity(
    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    @PrimaryKey
    var userName: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)