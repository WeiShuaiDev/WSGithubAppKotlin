package com.linwei.github_mvvm.mvvm.model.bean

import java.io.Serializable


class Page<T> : Serializable {
    var prev: Int = -1

    var next: Int = -1

    var last: Int = -1

    var first: Int = -1

    var result: T? = null
}