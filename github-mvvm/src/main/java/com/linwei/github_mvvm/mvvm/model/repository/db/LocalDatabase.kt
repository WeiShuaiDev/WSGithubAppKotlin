package com.linwei.github_mvvm.mvvm.model.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.IssueDao
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.ReposDao
import com.linwei.github_mvvm.mvvm.model.repository.db.dao.UserDao
import com.linwei.github_mvvm.mvvm.model.repository.db.entity.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/9/30
 * @Contact: linwei9605@gmail.com
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
@Database(
    entities = [IssueCommentEntity::class, IssueDetailEntity::class, OrgMemberEntity::class,
        ReceivedEventEntity::class, RepositoryCommitsEntity::class, RepositoryDetailEntity::class,
        RepositoryDetailReadmeEntity::class, RepositoryEventEntity::class, RepositoryForkEntity::class,
        RepositoryIssueEntity::class, TrendEntity::class, UserEventEntity::class,RepositoryStarEntity::class,
        UserReposEntity::class, UserStaredEntity::class,UserFollowerEntity::class,UserFollowedEntity::class,
        RepositoryWatcherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    /**
     * 用户数据库
     */
    abstract fun userDao(): UserDao

    /**
     * 仓库数据库
     */
    abstract fun reposDao(): ReposDao

    /**
     * 问题数据库
     */
    abstract fun issueDao(): IssueDao

}