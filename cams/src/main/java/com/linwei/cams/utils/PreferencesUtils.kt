package com.linwei.cams.utils

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/5/19
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: `SharedPreferences` 数据存储
 *-----------------------------------------------------------------------
 */
class PreferencesUtils<T>(
    var context: Context,
    val name: String,
    val default: T,
    val prefName: String = "github"
) :
    ReadWriteProperty<Any?, T> {

    private val prefs:SharedPreferences by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }


    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(findProperName(property));
    }

    /**
     * 根据 `key` 标识获取 `value` 数据
     * @param key [String] 存储标识
     */
    private fun findPreference(key: String): T {
        return when (default) {
            is Long -> prefs.getLong(key, default)
            is Int -> prefs.getInt(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            is String -> prefs.getString(key, default)
            else -> throw IllegalAccessException("Unsupported type.")
        } as T
    }

    /**
     * 查询数据存储标识 `Key`
     * @param property
     */
    private fun findProperName(property: KProperty<*>): String =
        if (name.isEmpty()) property.name else name

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(findProperName(property), value);
    }

    /**
     * 根据 `key` 标识存储 `value` 数据
     * @param key [String] 存储标识
     * @param val [T] 存储数据
     */
    private fun putPreference(key: String, value: T) {
        //定义 with 代表内部通过 this能够调用到 prefs.edit 返回的对象
        with(prefs.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                else -> throw IllegalAccessException("Unsupported type.")
            }.apply()

        }
    }
}