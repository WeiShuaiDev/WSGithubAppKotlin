package com.linwei.github_mvvm.mvvm.model.repository.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/20
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 收到事件表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "trend")
data class TrendEntity(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "language_type")
    @SerializedName("language_type")
    var languageType: String? = null,

    @ColumnInfo(name = "since")
    @SerializedName("since")
    var since: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)