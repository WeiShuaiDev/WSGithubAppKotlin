package com.linwei.cams.ext

/**
 * sealed: 声明一个密封类（限制子类化的类）
 * out   : 将类型参数标记为型变
 */
sealed class BooleanExp<out T>

/**
 * 声明一个类及其实例,相当于声明了一个单例
 */
object Otherwise : BooleanExp<Nothing>()

class WithData<T>(val data: T) : BooleanExp<T>()

inline fun <T> Boolean.yes(block: () -> T) =
    //kotlin 中的 when 表达式
    when {
        this -> {
            WithData(block())
        }
        else -> {
            Otherwise
        }
    }


inline fun <T> Boolean.no(block: () -> T) =
    when {
        this -> Otherwise
        else
        -> {
            WithData(block())
        }

    }

inline fun <T> BooleanExp<T>.otherwise(block: () -> T): T =
    when (this) {
        is Otherwise -> block();
        is WithData -> this.data

    }