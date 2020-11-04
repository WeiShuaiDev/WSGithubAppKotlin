package com.linwei.github_mvvm.utils

import android.content.Context
import com.linwei.github_mvvm.mvvm.model.ui.EventUIAction
import com.linwei.github_mvvm.mvvm.model.ui.EventUIModel
import com.linwei.github_mvvm.mvvm.ui.module.repos.ReposDetailActivity

/**
 * 事件相关跳转
 */
object EventUtils {
    fun evenAction(context: Context?, eventUIModel: EventUIModel?) {
        when (eventUIModel?.actionType) {
            EventUIAction.Person -> {

            }
            EventUIAction.Repos -> {
                System.out.println("owner${eventUIModel.owner} repositoryName${eventUIModel.repositoryName}")
                ReposDetailActivity.start(context, eventUIModel.owner, eventUIModel.repositoryName)
            }
            EventUIAction.Issue -> {

            }
            EventUIAction.Push -> {

            }
            EventUIAction.Release -> {
            }
        }
    }
}