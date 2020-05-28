package com.linwei.frame.http.cache

import org.json.JSONObject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:  Cache缓存提供一些常用类型增加数据源，获取数据源操作
 *   存储类型:byte、short、int、long、double、float、String、JSONObject、JSONArray、Array[]、Serializable、Bitmap、Drawable
 *-----------------------------------------------------------------------
 */
interface CacheMethod {

    fun put(key: String, value: String, saveTime: Int)

    fun getAsString(key: String):String

    fun put(key: String, value: JSONObject, saveTime: Int)

    fun getAsJSONObject(key:String):JSONObject
}