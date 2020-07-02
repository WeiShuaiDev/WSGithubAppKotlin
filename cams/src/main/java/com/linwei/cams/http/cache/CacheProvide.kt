package com.linwei.cams.http.cache

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description:  Cache缓存提供一些常用类型增加数据源，获取数据源操作
 *   存储类型:String、JSONObject、JSONArray、Array[]、Serializable、Bitmap、Drawable
 *-----------------------------------------------------------------------
 */
interface CacheProvide {

    fun put(key: String, value: String)

    fun put(key: String, value: String, saveTime: Int)

    fun getAsString(key: String): String?

    fun put(key: String, value: JsonObject)

    fun put(key: String, value: JSONObject, saveTime: Int)

    fun getAsJSONObject(key: String): JSONObject?

    fun put(key: String, value: JSONArray)

    fun put(key: String, value: JSONArray, saveTime: Int)

    fun getAsJSONArray(key: String): JSONArray?

    fun put(key: String, value: ByteArray)

    fun put(key: String, value: ByteArray, saveTime: Int)

    fun getAsBinary(key: String): ByteArray?

    fun put(key: String, value: Serializable)

    fun put(key: String, value: Serializable, saveTime: Int)

    fun getAsObject(key: String): Any?

    fun put(key: String, value: Bitmap)

    fun put(key: String, value: Bitmap, saveTime: Int)

    fun getAsBitmap(key: String): Bitmap?

    fun put(key: String, value: Drawable)

    fun put(key: String, value: Drawable, saveTime: Int)

    fun getAsDrawable(key: String): Drawable?

}
