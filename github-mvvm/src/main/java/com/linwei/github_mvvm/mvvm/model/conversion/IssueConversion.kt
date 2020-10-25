package com.linwei.github_mvvm.mvvm.model.conversion

import com.linwei.cams.utils.TimeUtils
import com.linwei.github_mvvm.mvvm.model.bean.Issue
import com.linwei.github_mvvm.mvvm.model.bean.IssueEvent
import com.linwei.github_mvvm.mvvm.model.ui.IssueUIModel

/**
 * Issue相关实体转换
 */
object IssueConversion {

    fun issueToIssueUIModel(issue: Issue): IssueUIModel {
        val issueUIModel = IssueUIModel()
        issueUIModel.username = issue.user?.login ?: ""
        issueUIModel.image = issue.user?.avatarUrl ?: ""
        issueUIModel.action = issue.title ?: ""
        issueUIModel.time = TimeUtils.getDate(issue.createdAt)
        issueUIModel.comment = issue.commentNum.toString()
        issueUIModel.issueNum = issue.number
        issueUIModel.status = issue.state ?: ""
        issueUIModel.content = issue.body ?: ""
        issueUIModel.locked = issue.locked
        return issueUIModel
    }


    fun issueEventToIssueUIModel(issue: IssueEvent): IssueUIModel {
        val issueUIModel = IssueUIModel()
        issueUIModel.username = issue.user?.login ?: ""
        issueUIModel.image = issue.user?.avatarUrl ?: ""
        issueUIModel.action = issue.body ?: ""
        issueUIModel.time = TimeUtils.getDate(issue.createdAt)
        issueUIModel.status = issue.id ?: ""
        return issueUIModel
    }

}