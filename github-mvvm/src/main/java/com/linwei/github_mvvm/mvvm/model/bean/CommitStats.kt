package com.linwei.github_mvvm.mvvm.model.bean

import java.io.Serializable


class CommitStats : Serializable {

    var total: Int = 0
    var additions: Int = 0
    var deletions: Int = 0
}
