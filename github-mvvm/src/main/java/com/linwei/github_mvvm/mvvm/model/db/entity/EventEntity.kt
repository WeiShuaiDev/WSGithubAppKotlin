package com.linwei.github_mvvm.mvvm.model.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.linwei.github_mvvm.mvvm.model.bean.EventPayload
import com.linwei.github_mvvm.mvvm.model.bean.Repository
import com.linwei.github_mvvm.mvvm.model.bean.User
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/30
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "event")
data class EventEntity(
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @PrimaryKey
    var id: String? = null,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String? = null,

    @ColumnInfo(name = "actor")
    @SerializedName("actor")
    var actor: User? = null,

    @ColumnInfo(name = "repo")
    @SerializedName("repo")
    var repo: Repository? = null,

    @ColumnInfo(name = "org")
    @SerializedName("org")
    var org: User? = null,

    @ColumnInfo(name = "payload")
    @SerializedName("payload")
    var payload: EventPayload? = null,

    @ColumnInfo(name = "public")
    @SerializedName("public")
    var isPublic: Boolean = false,

    @ColumnInfo(name = "created_at")
    @SerializedName("created_at")
    var createdAt: Date? = null

)