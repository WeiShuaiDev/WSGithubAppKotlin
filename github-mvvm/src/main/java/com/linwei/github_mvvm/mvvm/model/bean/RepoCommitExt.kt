package com.linwei.github_mvvm.mvvm.model.bean

import java.util.ArrayList


class RepoCommitExt : RepoCommit() {

    val files: ArrayList<CommitFile>? = null
    val stats: CommitStats? = null
}
