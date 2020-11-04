package com.linwei.github_mvvm.mvvm.model.conversion

import android.content.Context
import com.linwei.cams.utils.TimeUtils
import com.linwei.github_mvvm.R
import com.linwei.github_mvvm.mvvm.model.bean.Event
import com.linwei.github_mvvm.mvvm.model.bean.Notification
import com.linwei.github_mvvm.mvvm.model.bean.RepoCommit
import com.linwei.github_mvvm.mvvm.model.ui.CommitUIModel
import com.linwei.github_mvvm.mvvm.model.ui.EventUIAction
import com.linwei.github_mvvm.mvvm.model.ui.EventUIModel

/**
 * 事件相关实体转换
 */
object EventConversion {

    fun eventToEventUIModel(event: Event): EventUIModel {
        var actionStr: String? = ""
        var des: String? = ""
        val eventUIModel = EventUIModel()
        eventAction(event, eventUIModel)
        when (event.type) {
            "CommitCommentEvent" -> {
                actionStr = "Commit comment at " + event.repo?.name
            }
            "CreateEvent" -> {
                actionStr = if (event.payload?.refType == "repository") {
                    "Created repository " + event.repo?.name
                } else {
                    "Created " + event.payload?.refType + " " + event.payload?.ref + " at " + event.repo?.name
                }
            }
            "DeleteEvent" -> {
                actionStr =
                    "Delete " + event.payload?.refType + " " + event.payload?.ref + " at " + event.repo?.name
            }
            "ForkEvent" -> {
                val oriRepo:String? = event.repo?.name
                val newRepo:String = event.actor?.login + "/" + event.repo?.name
                actionStr = "Forked $oriRepo to $newRepo"

            }
            "GollumEvent" -> {
                actionStr = event.actor?.login + " a wiki page "
            }

            "InstallationEvent" -> {
                actionStr = event.payload?.action + " an GitHub App "
            }
            "InstallationRepositoriesEvent" -> {
                actionStr = event.payload?.action + " repository from an installation "
            }
            "IssueCommentEvent" -> {
                actionStr =
                    event.payload?.action + " comment on issue " + event.payload?.issue?.number.toString() + " in " + event.repo?.name
                des = event.payload?.comment?.body
            }
            "IssuesEvent" -> {
                actionStr =
                    event.payload?.action + " issue " + event.payload?.issue?.number.toString() + " in " + event.repo?.name
                des = event.payload?.issue?.title
            }

            "MarketplacePurchaseEvent" -> {
                actionStr = event.payload?.action + " marketplace plan "
            }
            "MemberEvent" -> {
                actionStr = event.payload?.action + " member to " + event.repo?.name
            }
            "OrgBlockEvent" -> {
                actionStr = event.payload?.action + " a user "
            }
            "ProjectCardEvent" -> {
                actionStr = event.payload?.action + " a project "
            }
            "ProjectColumnEvent" -> {
                actionStr = event.payload?.action + " a project "
            }

            "ProjectEvent" -> {
                actionStr = event.payload?.action + " a project "
            }
            "PublicEvent" -> {
                actionStr = "Made " + event.repo?.name + " public"
            }
            "PullRequestEvent" -> {
                actionStr = event.payload?.action + " pull request " + event.repo?.name
            }
            "PullRequestReviewEvent" -> {
                actionStr = event.payload?.action + " pull request review at" + event.repo?.name
            }
            "PullRequestReviewCommentEvent" -> {
                actionStr =
                    event.payload?.action + " pull request review comment at" + event.repo?.name

            }
            "PushEvent" -> {
                var ref: String? = event.payload?.ref
                ref = ref?.substring(ref.lastIndexOf("/") + 1)
                actionStr = "Push to " + ref + " at " + event.repo?.name
                des = ""
                var descSpan: String? = ""
                val count:Int = event.payload?.commits?.size!!
                val maxLines = 4
                val max:Int = if (count > maxLines) {
                    maxLines - 1
                } else {
                    count
                }
                for (i:Int in 0 until max) {
                    val commit = event.payload?.commits!![i]
                    if (i != 0) {
                        descSpan += ("\n")
                    }
                    val sha = commit.sha?.substring(0, 7)
                    descSpan += sha
                    descSpan += " "
                    descSpan += commit.message
                }
                if (count > maxLines) {
                    descSpan = "$descSpan\n..."
                }
            }
            "ReleaseEvent" -> {
                actionStr =
                    event.payload?.action + " release " + event.payload?.release?.tagName + " at " + event.repo?.name
            }
            "WatchEvent" -> {
                actionStr = event.payload?.action + " " + event.repo?.name

            }
        }
        eventUIModel.username = event.actor?.login ?: ""
        eventUIModel.action = actionStr ?: ""
        eventUIModel.des = des ?: ""
        eventUIModel.image = event.actor?.avatarUrl ?: ""
        eventUIModel.time = TimeUtils.getNewsTime(event.createdAt)
        return eventUIModel
    }

    ///跳转
    private fun eventAction(event: Event, eventUIModel: EventUIModel) {
        if (event.repo == null) {
            eventUIModel.owner = event.actor?.login ?: ""
            eventUIModel.actionType = EventUIAction.Person
            return
        }
        val owner = event.repo?.name?.split("/")?.get(0)
        val repositoryName = event.repo?.name?.split("/")?.get(1)
        val fullName = "$owner/$repositoryName"
        eventUIModel.owner = owner ?: ""
        eventUIModel.repositoryName = repositoryName ?: ""
        when (event.type) {
            "ForkEvent" -> {
                eventUIModel.actionType = EventUIAction.Repos
                eventUIModel.owner = event.actor?.login!!
            }
            "PushEvent" -> {
                when {
                    event.payload?.commits == null -> {
                        eventUIModel.actionType = EventUIAction.Repos
                    }
                    event.payload?.commits?.size == 1 -> {
                        eventUIModel.actionType = EventUIAction.Push
                        eventUIModel.pushSha.clear()
                        eventUIModel.pushSha.add(event.payload?.commits?.get(0)?.sha ?: "")
                    }
                    else -> {
                        eventUIModel.actionType = EventUIAction.Push
                        eventUIModel.pushSha.clear()
                        event.payload?.commits?.apply {
                            forEach {
                                eventUIModel.pushSha.add(it.sha ?: "")
                                eventUIModel.pushShaDes.add(it.message ?: "")
                            }
                        }
                    }
                }
            }
            "ReleaseEvent" -> {
                val url:String? = event.payload?.release?.tarballUrl
                eventUIModel.actionType = EventUIAction.Release
                eventUIModel.releaseUrl = url ?: ""
                /*CommonUtils.launchWebView(context, repositoryName, url);*/
            }
            "IssueCommentEvent", "IssuesEvent" -> {
                eventUIModel.actionType = EventUIAction.Issue
                eventUIModel.IssueNum = event.payload?.issue?.number ?: 0
            }
            else -> {
                eventUIModel.actionType = EventUIAction.Repos
            }
        }
    }

    fun notificationToEventUIModel(context: Context, notification: Notification): EventUIModel {
        val eventUIModel = EventUIModel()
        eventUIModel.time = TimeUtils.getNewsTime(notification.updateAt)
        eventUIModel.username = notification.repository?.fullName ?: ""
        val type:String = notification.subject?.type ?: ""
        val status:String = if (notification.unread) {
            context.getString(R.string.unread)
        } else {
            context.getString(R.string.readed)
        }
        eventUIModel.des =
            notification.reason + " " + "${context.getString(R.string.notify_type)}：$type，${context.getString(
                R.string.notify_status
            )}：$status"
        eventUIModel.action = notification.subject?.title ?: ""
        eventUIModel.actionType = if (notification.subject?.type == "Issue") {
            EventUIAction.Issue
        } else {
            EventUIAction.Person
        }

        eventUIModel.owner = notification.repository?.owner?.login ?: ""
        eventUIModel.repositoryName = notification.repository?.name ?: ""

        val url:String? = notification.subject?.url
        url?.apply {
            val tmp:Array<String> = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val number:String = tmp[tmp.size - 1]
            eventUIModel.IssueNum = number.toInt()
        }
        eventUIModel.threadId = notification.id ?: ""

        return eventUIModel
    }

    fun commitToCommitUIModel(repoCommit: RepoCommit): CommitUIModel {
        val commitUIModel = CommitUIModel()
        commitUIModel.time = TimeUtils.getNewsTime(repoCommit.commit?.committer?.date)
        commitUIModel.userName = repoCommit.commit?.committer?.name ?: ""
        commitUIModel.sha = repoCommit.sha ?: ""
        commitUIModel.des = repoCommit.commit?.message ?: ""
        return commitUIModel
    }
}