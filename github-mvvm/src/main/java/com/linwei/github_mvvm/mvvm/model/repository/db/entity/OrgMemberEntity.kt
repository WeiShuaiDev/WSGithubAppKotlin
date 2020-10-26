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
 * @Description: 关注表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "org_member")
data class OrgMemberEntity(
    @ColumnInfo(name = "org")
    @SerializedName("org")
    @PrimaryKey(autoGenerate = true)
    var org: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)