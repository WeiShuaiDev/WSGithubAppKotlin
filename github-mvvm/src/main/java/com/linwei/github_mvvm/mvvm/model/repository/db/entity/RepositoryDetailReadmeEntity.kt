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
 * @Description: 仓库Readme文件表
 *-----------------------------------------------------------------------
 */
@Entity(tableName = "repository_detail_readme", primaryKeys = ["full_name", "branch"])
data class RepositoryDetailReadmeEntity(
    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    var fullName: String? = null,

    @ColumnInfo(name = "branch")
    @SerializedName("branch")
    var branch: String? = null,

    @ColumnInfo(name = "data")
    @SerializedName("data")
    var data: String? = null
)