package com.linwei.github_mvvm.utils

import android.content.Context
import com.linwei.github_mvvm.mvvm.model.ui.EventUIAction
import com.linwei.github_mvvm.mvvm.model.ui.EventUIModel


object EventUtils {

    fun evenAction(context: Context?, eventUIModel: EventUIModel?) {
//        when (eventUIModel?.actionType) {
//            EventUIAction.Person -> {
//                PersonActivity.gotoPersonInfo(eventUIModel.owner)
//            }
//            EventUIAction.Repos -> {
//                ReposDetailActivity.gotoReposDetail(eventUIModel.owner, eventUIModel.repositoryName)
//            }
//            EventUIAction.Issue -> {
//                IssueDetailActivity.gotoIssueDetail(
//                    eventUIModel.owner,
//                    eventUIModel.repositoryName,
//                    eventUIModel.IssueNum
//                )
//            }
//            EventUIAction.Push -> {
//                if (eventUIModel.pushSha.size == 1) {
//                    PushDetailActivity.gotoPushDetail(
//                        eventUIModel.owner,
//                        eventUIModel.repositoryName,
//                        eventUIModel.pushSha[0]
//                    )
//                } else {
////                    context?.showOptionSelectDialog(eventUIModel.pushShaDes, OnItemClickListener { dialog, _, _, position ->
////                        dialog.dismiss()
////                        PushDetailActivity.gotoPushDetail(eventUIModel.owner, eventUIModel.repositoryName, eventUIModel.pushSha[position])
////                    })
//                }
//            }
//            EventUIAction.Release -> {
//            }
//        }
    }
}