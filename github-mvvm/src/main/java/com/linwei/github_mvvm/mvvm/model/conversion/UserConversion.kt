package com.linwei.github_mvvm.mvvm.model.conversion

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.linwei.cams.ext.string
import com.linwei.cams.utils.TimeUtils
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.model.api.Api
import com.linwei.github_mvvm.mvvm.model.bean.User
import com.linwei.github_mvvm.mvvm.model.data.UserUIModel

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/27
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
object UserConversion {

    /**
     * [User] 数据转换为 [UserUIModel]
     * @param user [User]
     * @return [UserUIModel]
     */
    fun userToUserUIModel(user: User): UserUIModel {
        val userUIModel = UserUIModel()
        userUIModel.login = user.login
        userUIModel.name = if (user.type == "User") {
            "personal"
        } else {
            "organization"
        }
        userUIModel.avatarUrl = user.avatarUrl
        return userUIModel

    }

    /**
     * 克隆[User] 数据类型为 [UserUIModel]
     * @param context [Context]
     * @param user [User]
     * @param userUIModel [UserUIModel]
     */
    fun cloneDataFromUser(context: Context?, user: User, userUIModel: UserUIModel) {
        userUIModel.login = user.login
        userUIModel.id = user.id
        userUIModel.name = user.name
        userUIModel.avatarUrl = user.avatarUrl
        userUIModel.htmlUrl = user.htmlUrl
        userUIModel.type = user.type
        userUIModel.company = user.company ?: ""
        userUIModel.location = user.location ?: ""
        userUIModel.blog = user.blog ?: ""
        userUIModel.email = user.email

        userUIModel.bio = user.bio
        userUIModel.bioDes = if (user.bio != null) {
            user.bio + "\n" + context?.getString(R.string.user_created_at_text) + TimeUtils.getDate(
                user.createdAt
            )
        } else {
            R.string.user_created_at_text.string() + TimeUtils.getDate(user.createdAt)
        }
        userUIModel.starRepos =
            R.string.user_stared_text.string() + "\n" + getBlankText(user.starRepos)
        userUIModel.honorRepos =
            R.string.user_stared_text.string() + "\n" + getBlankText(user.honorRepos)
        userUIModel.publicRepos =
            R.string.user_repository_text.string() + "\n" + getBlankText(user.publicRepos)
        userUIModel.followers =
            R.string.user_followers_text.string() + "\n" + getBlankText(user.followers)
        userUIModel.following =
            R.string.user_followed_text.string() + "\n" + getBlankText(user.following)
        userUIModel.actionUrl = getUserChartAddress(context, user.login ?: "")

        userUIModel.publicGists = user.publicGists
        userUIModel.createdAt = user.createdAt
        userUIModel.updatedAt = user.updatedAt

    }

    /**
     * 根据 `App` 主题色，获取状态图表地址
     * @param context [Context]
     * @param name [String]
     * @return [String]
     */
    private fun getUserChartAddress(context: Context?, name: String): String {
        val stringBuffer = StringBuffer()
        val color: Int = ContextCompat.getColor(context!!, R.color.colorPrimary)
        stringBuffer.append(Integer.toHexString(Color.red(color)))
        stringBuffer.append(Integer.toHexString(Color.green(color)))
        stringBuffer.append(Integer.toHexString(Color.blue(color)))
        return Api.GRAPHIC_HOST + stringBuffer.toString() + "/" + name
    }

    /**
     * 根据字段 [vlaue] 值，返回默认数据。
     * @param value [Int]
     * @return [String]
     */
    private fun getBlankText(value: Int?): String {
        return value?.toString() ?: "---"
    }
}