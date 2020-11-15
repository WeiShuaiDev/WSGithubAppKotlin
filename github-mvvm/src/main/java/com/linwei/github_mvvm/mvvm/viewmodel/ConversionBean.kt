package com.linwei.github_mvvm.mvvm.viewmodel

import com.linwei.github_mvvm.mvvm.model.bean.*
import com.linwei.github_mvvm.mvvm.model.conversion.EventConversion
import com.linwei.github_mvvm.mvvm.model.conversion.IssueConversion
import com.linwei.github_mvvm.mvvm.model.conversion.ReposConversion
import com.linwei.github_mvvm.mvvm.model.conversion.UserConversion
import com.linwei.github_mvvm.mvvm.model.ui.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/10/30
 * @Contact: linwei9605@gmail.com"d
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
object ConversionBean {

    /**
     * 进行数据转换 'Trend' ->'ReposUIModel'
     * @param list [TrendingRepoModel]
     * @return list [ReposUIModel]
     */
    fun trendConversionByReposUIModel(list: List<TrendingRepoModel>?): MutableList<ReposUIModel> {
        val reposUIModel: MutableList<ReposUIModel> = mutableListOf()
        list?.let {
            for (trend: TrendingRepoModel in it) {
                reposUIModel.add(ReposConversion.trendToReposUIModel(trend))
            }
        }
        return reposUIModel
    }


    /**
     * 进行数据转换 'User' ->'UserUIModel'
     * @param page [User]
     * @return list [UserUIModel]
     */
    fun userConversionByUserUIModel(page: Page<List<User>>?): MutableList<UserUIModel> {
        val userUIList: MutableList<UserUIModel> = mutableListOf()
        page?.apply {
            result?.let {
                for (user: User in it) {
                    userUIList.add(UserConversion.userToUserUIModel(user))
                }
            }
        }
        return userUIList
    }

    /**
     * 进行数据转换 'Event' ->'EventUIModel'
     * @param page [Event]
     * @return list [EventUIModel]
     */
    fun eventConversionByEventUIModel(page: Page<List<Event>>?): MutableList<EventUIModel> {
        val eventUIList: MutableList<EventUIModel> = mutableListOf()
        page?.apply {
            result?.let {
                for (event: Event in it) {
                    eventUIList.add(EventConversion.eventToEventUIModel(event))
                }
            }
        }
        return eventUIList
    }

    /**
     * 进行数据转换 'RepoCommit' ->'CommitUIModel'
     * @param page [RepoCommit]
     * @return list [CommitUIModel]
     */
    fun repoCommitConversionByCommitUIModel(page: Page<List<RepoCommit>>?): MutableList<CommitUIModel> {
        val commitUiModel: MutableList<CommitUIModel> = mutableListOf()
        page?.apply {
            result?.let {
                for (commit: RepoCommit in it) {
                    commitUiModel.add(EventConversion.commitToCommitUIModel(commit))
                }
            }
        }
        return commitUiModel
    }

    /**
     * 进行数据转换 'Issue' ->'IssueUIModel'
     * @param result [Issue]
     * @return list [IssueUIModel]
     */
    fun searchResultIssueConversionByIssueUIModel(result: SearchResult<Issue>?): MutableList<IssueUIModel> {
        val issueUiModel: MutableList<IssueUIModel> = mutableListOf()
        result?.apply {
            items?.let {
                for (issue: Issue in it) {
                    issueUiModel.add(IssueConversion.issueToIssueUIModel(issue))
                }
            }
        }
        return issueUiModel
    }

    /**
     * 进行数据转换 'Issue' ->'IssueUIModel'
     * @param page [Issue]
     * @return list [IssueUIModel]
     */
    fun issueConversionByIssueUIModel(page: Page<List<Issue>>?): MutableList<IssueUIModel> {
        val issueUiModel: MutableList<IssueUIModel> = mutableListOf()
        page?.apply {
            result?.let {
                for (issue: Issue in it) {
                    issueUiModel.add(IssueConversion.issueToIssueUIModel(issue))
                }
            }
        }
        return issueUiModel
    }
}