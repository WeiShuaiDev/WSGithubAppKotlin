package com.linwei.frame.manager

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.gson.JsonObject
import com.linwei.frame.http.cache.CacheProvide
import com.linwei.frame.http.cache.kinds.MapCache
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 面向对象6大原则，符合接口隔离原则，不同存储方式，暴露基本操作，都需要实现 {@link CacheProvide} 接口，
 *               这样提高代码扩展性。
 *               存储方式{@link LruCache}、{@link MapCache} 内存存储，根据不同业务需求，选择不同存储方式。
 *               存储类型:String、JSONObject、JSONArray、Array[]、Serializable、Bitmap、Drawable
 *-----------------------------------------------------------------------
 */
class MemoryStorageManager(

) : CacheProvide {

    private val mMapCache: MapCache<String, String> = MapCache()

    companion object {
        private var INSTANCE: MemoryStorageManager? = null

        @JvmStatic
        fun getInstance(): MemoryStorageManager {
            return INSTANCE ?: MemoryStorageManager().apply {
                INSTANCE = this
            }
        }
    }

    override fun put(key: String, value: String) {
        mMapCache.put(key, value)
    }

    override fun put(key: String, value: String, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: JsonObject) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: JSONObject, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: JSONArray) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: JSONArray, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: ByteArray, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: Serializable) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: Serializable, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: Bitmap) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: Bitmap, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: Drawable) {
        TODO("Not yet implemented")
    }

    override fun put(key: String, value: Drawable, saveTime: Int) {
        TODO("Not yet implemented")
    }

    override fun getAsString(key: String): String? {
        TODO("Not yet implemented")
    }

    override fun getAsJSONObject(key: String): JSONObject? {
        TODO("Not yet implemented")
    }

    override fun getAsJSONArray(key: String): JSONArray? {
        TODO("Not yet implemented")
    }

    override fun getAsBinary(key: String): ByteArray? {
        TODO("Not yet implemented")
    }

    override fun getAsObject(key: String): Any? {
        TODO("Not yet implemented")
    }

    override fun getAsBitmap(key: String): Bitmap? {
        TODO("Not yet implemented")
    }

    override fun getAsDrawable(key: String): Drawable? {
        TODO("Not yet implemented")
    }


}