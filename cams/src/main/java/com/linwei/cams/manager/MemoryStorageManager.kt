package com.linwei.cams.manager

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.gson.JsonObject
import com.linwei.cams.http.cache.CacheProvide
import com.linwei.cams.http.cache.kinds.MapCache
import com.linwei.cams.utils.BitmapUtils
import com.linwei.cams.utils.TimeUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.Serializable

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 面向对象6大原则，符合接口隔离原则，不同存储方式，暴露基本操作，都需要实现 {@link CacheProvide} 接口，
 *               这样提高代码扩展性。
 *               存储方式{@link MapCache} 内存存储，根据不同业务需求，选择不同存储方式。
 *               存储类型:String、JSONObject、JSONArray、Array[]、Serializable、Bitmap、Drawable
 *-----------------------------------------------------------------------
 */
class MemoryStorageManager private constructor() : CacheProvide {

    private val mMapCache: MapCache = MapCache()

    companion object {
        private var INSTANCE: MemoryStorageManager? = null

        @JvmStatic
        fun getInstance(): MemoryStorageManager {
            return INSTANCE ?: MemoryStorageManager().apply {
                INSTANCE = this
            }
        }
    }

    /**
     * 保存字符串 {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value 字符串数据
     */
    override fun put(key: String, value: String) {
        mMapCache.put(key, value)
    }

    /**
     * 保存字符串 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value 字符串数据
     * @param saveTime 有效期
     */
    override fun put(key: String, value: String, saveTime: Int) {
        put(key, TimeUtils.newStringWithDateInfo(saveTime, value))
    }

    /**
     * 保存Json对象 {@code value} 数据到 {@code key} 文件中，
     * @param key 文件名
     * @param value JsonObject对象
     */
    override fun put(key: String, value: JsonObject) {
        put(key, value.toString())
    }

    /**
     * 保存Json对象 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value JsonObject对象
     * @param saveTime 有效期
     */
    override fun put(key: String, value: JSONObject, saveTime: Int) {
        put(key, TimeUtils.newStringWithDateInfo(saveTime, value.toString()))
    }

    /**
     * 保存Json数组 {@code value} 数据到 {@code key} 文件中，
     * @param key 文件名
     * @param value JSONArray
     */
    override fun put(key: String, value: JSONArray) {
        put(key, value.toString())
    }

    /**
     * 保存Json数组 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value JSONArray
     * @param saveTime 有效期
     */
    override fun put(key: String, value: JSONArray, saveTime: Int) {
        put(key, TimeUtils.newStringWithDateInfo(saveTime, value.toString()))
    }

    /**
     * 保存字节数组 {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value ByteArray
     * @param saveTime 有效期
     */
    override fun put(key: String, value: ByteArray) {
        put(key, value.toString())
    }

    /**
     * 保存字节数组 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value ByteArray
     * @param saveTime 有效期
     */
    override fun put(key: String, value: ByteArray, saveTime: Int) {
        put(key, TimeUtils.newStringWithDateInfo(saveTime, value).toString())
    }

    /**
     * 保存序列化 {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value Serializable
     * @param saveTime 有效期
     */
    override fun put(key: String, value: Serializable) {
        put(key, value.toString())
    }

    /**
     * 保存序列化 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value Serializable
     * @param saveTime 有效期
     */
    override fun put(key: String, value: Serializable, saveTime: Int) {
        put(key, TimeUtils.newStringWithDateInfo(saveTime, value.toString()))
    }

    /**
     * 保存Bitmap {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value Bitmap
     */
    override fun put(key: String, value: Bitmap) {
        put(key, BitmapUtils.bitmap2Bytes(value))
    }

    /**
     * 保存Bitmap {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value Bitmap
     * @param saveTime 有效期
     */
    override fun put(key: String, value: Bitmap, saveTime: Int) {
        put(
            key,
            TimeUtils.newStringWithDateInfo(saveTime, BitmapUtils.bitmap2Bytes(value))
        )
    }

    /**
     * 保存Drawable {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value Bitmap
     */
    override fun put(key: String, value: Drawable) {
        val drawable2Bitmap: Bitmap? = BitmapUtils.drawable2Bitmap(value)
        if (drawable2Bitmap != null) {
            put(key, drawable2Bitmap)
        }
    }

    /**
     * 保存Drawable {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value Drawable
     * @param saveTime 有效期
     */
    override fun put(key: String, value: Drawable, saveTime: Int) {
        val drawable2Bitmap: Bitmap? = BitmapUtils.drawable2Bitmap(value)
        if (drawable2Bitmap != null) {
            put(key, drawable2Bitmap, saveTime)
        }
    }


    /**
     * 获取 {@code key} 文件中字符串缓存数据
     * @param key 文件名
     */
    override fun getAsString(key: String): String? {
        return mMapCache.get(key)
    }

    /**
     * 获取 {@code key} 文件中 JSONObject 缓存数据
     * @param key 文件名
     */
    override fun getAsJSONObject(key: String): JSONObject? {
        val value: String? = mMapCache.get(key)
        try {
            if (value != null) {
                return JSONObject(value)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return null
    }

    /**
     * 获取 {@code key} 文件中 JSONArray 缓存数据
     * @param key 文件名
     */
    override fun getAsJSONArray(key: String): JSONArray? {
        val value: String? = mMapCache.get(key)
        try {
            if (value != null) {
                return JSONArray(value)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return null
    }

    /**
     * 获取 {@code key} 文件中 ByteArray 缓存数据
     * @param key 文件名
     */
    override fun getAsBinary(key: String): ByteArray? {
        return mMapCache.get(key)?.toByteArray()
    }

    /**
     * 获取 {@code key} 文件中 Serializable 缓存数据
     * @param key 文件名
     */
    override fun getAsObject(key: String): Any? {
        return mMapCache.get(key)
    }

    /**
     * 获取 {@code key} 文件中 Bitmap 缓存数据
     * @param key 文件名
     */
    override fun getAsBitmap(key: String): Bitmap? {
        val byteArray: ByteArray? = getAsBinary(key)
        return BitmapUtils.bytes2Bitmap(byteArray)
    }

    /**
     * 获取 {@code key} 文件中 Drawable 缓存数据
     * @param key 文件名
     */
    override fun getAsDrawable(key: String): Drawable? {
        val byteArray: ByteArray? = getAsBinary(key)
        return BitmapUtils.bitmap2Drawable(BitmapUtils.bytes2Bitmap(byteArray))
    }
}