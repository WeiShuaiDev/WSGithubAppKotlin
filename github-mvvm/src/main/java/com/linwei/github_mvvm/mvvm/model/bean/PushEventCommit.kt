package com.linwei.github_mvvm.mvvm.model.bean

import java.io.Serializable

class PushEventCommit : Serializable {

    var sha: String? = null
    //email&name
    var author: User? = null
    var message: String? = null
    var distinct: Boolean = false
    var url: String? = null


}
