package com.linwei.cams.ext

import java.util.*

/**
 * [ResId] 转换为字符串 [String]
 * @param [ResId]
 * @return [String]
 */
fun Any.string(): String {
    return if (this is Int) {
        ctx.resources.getString(this)
    } else {
        this.toString()
    }
}

/**
 * [ResId] 转换为字符串 [String]
 * @param [ResId] 字符串Id, [args] 字符串格式化参数
 * @return [String]
 */
fun Any.string(vararg args: String): String {
    return if (this is Int) {
        ctx.resources.getString(this, *args)
    } else {
        this.toString()
    }
}

/**
 * [ResId] 转换为字符串数组 [String]
 * @param [ResId]
 * @return [Array]
 */
fun Int.stringArray(): Array<String> {
    return ctx.resources.getStringArray(this)
}


/**
 * 判断字符串是否为空
 * @param params [Array]
 */
fun isEmptyParameter(vararg params: String?): Boolean {
    for (p: String? in params)
        if (p.isNullOrEmpty() || p == "null" || p == "NULL") {
            return true
        }
    return false
}

/**
 * 判断字符串数组是否为空
 */
fun isEmptyArraysParameter(params: Array<out String?>): Boolean {
    for (p: String? in params)
        if (p.isNullOrEmpty() || p == "null" || p == "NULL") {
            return true
        }
    return false
}

/**
 * 判断字符串是否为空
 * @param params [String]
 */
fun String?.isNotNullOrEmpty(): Boolean {
    this?.let {
        if (it.isNotEmpty()) {
            if (it.toUpperCase(Locale.getDefault()) != "NULL") {
                return true
            }
        }
    }
    return false
}

/**
 * 判断集合是否为空，数据为0
 */
fun List<Any>?.isNotNullOrSize(): Boolean {
    this?.let {
        if (it.isNotEmpty()) {
            return true
        }
    }
    return false
}

