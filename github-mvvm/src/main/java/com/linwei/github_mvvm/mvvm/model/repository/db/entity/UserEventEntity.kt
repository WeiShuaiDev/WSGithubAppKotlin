package com.linwei.github_mvvm.mvvm.model.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/26
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 产生事件表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "user_event")
data class UserEventEntity(
    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    @PrimaryKey(autoGenerate = true)
    var userName: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)