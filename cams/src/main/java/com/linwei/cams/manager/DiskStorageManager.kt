package com.linwei.cams.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.gson.JsonObject
import com.linwei.cams.http.cache.CacheProvide
import com.linwei.cams.http.cache.kinds.DiskLruCache
import com.linwei.cams.utils.BitmapUtils
import com.linwei.cams.utils.TimeUtils
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 面向对象6大原则，符合接口隔离原则，不同存储方式，暴露基本操作，都需要实现 {@link CacheProvide} 接口，
 *               这样提高代码扩展性。
 *               存储方式{@link DiskLruCache} 磁盘存储，根据不同业务需求，选择不同存储方式。
 *               存储类型:String、JSONObject、JSONArray、Array[]、Serializable、Bitmap、Drawable
 *-----------------------------------------------------------------------
 */
class DiskStorageManager private constructor (
    cacheDir: File,
    max_size: Long,
    max_count: Int
) : CacheProvide {

    private var mDisLruCache: DiskLruCache

    init {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw RuntimeException("can't make dirs in " + cacheDir.absolutePath)
        }
        mDisLruCache = DiskLruCache(cacheDir, max_size, max_count)
    }

    companion object {
        private val mInstanceMap: MutableMap<String, DiskStorageManager> = mutableMapOf()

        //缓存文件夹
        private const val CACHE_NAME: String = "Github"

        const val TIME_HOUR: Int = 60 * 60
        const val TIME_DAY: Int = TIME_HOUR * 24
        private const val MAX_SIZE: Int = 1000 * 1000 * 50
        private const val MAX_COUNT: Int = Int.MAX_VALUE

        @JvmStatic
        fun getInstance(ctx: Context): DiskStorageManager {
            return getInstance(ctx, CACHE_NAME)
        }

        @JvmStatic
        fun getInstance(ctx: Context, cacheName: String): DiskStorageManager {
            val cacheDir = File(ctx.cacheDir, cacheName)
            return getInstance(cacheDir, MAX_SIZE.toLong(), MAX_COUNT)
        }

        @JvmStatic
        fun getInstance(cacheDir: File): DiskStorageManager {
            return getInstance(cacheDir, MAX_SIZE.toLong(), MAX_COUNT)
        }

        @JvmStatic
        fun getInstance(ctx: Context, max_size: Long, max_count: Int): DiskStorageManager {
            val cacheDir = File(ctx.cacheDir, CACHE_NAME)
            return getInstance(cacheDir, max_size, max_count)
        }

        @JvmStatic
        fun getInstance(cacheDir: File, max_size: Long, max_count: Int): DiskStorageManager {
            val cacheKey = "${cacheDir.absoluteFile}${myPid()}"
            var manager: DiskStorageManager? = mInstanceMap[cacheKey]
            if (manager == null) {
                manager = DiskStorageManager(cacheDir, max_size, max_count)
                mInstanceMap.put(cacheKey, manager)
            }
            return manager
        }

        private fun myPid(): String {
            return "_" + android.os.Process.myPid()
        }
    }

    /**
     * 保存字符串 {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value 字符串数据
     */
    override fun put(key: String, value: String) {
        val file: File = mDisLruCache.newFile(key)
        var bw: BufferedWriter? = null
        try {
            bw = BufferedWriter(FileWriter(file), 1024)
            bw.run { write(value) }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bw?.flush()
                bw?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mDisLruCache.put(file, System.currentTimeMillis())
        }
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
     */
    override fun put(key: String, value: ByteArray) {
        val file: File = mDisLruCache.newFile(key)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(file)
            out.run { write(value) }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                out?.flush()
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mDisLruCache.put(file, System.currentTimeMillis())
        }
    }

    /**
     * 保存字节数组 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value ByteArray
     * @param saveTime 有效期
     */
    override fun put(key: String, value: ByteArray, saveTime: Int) {
        put(key, TimeUtils.newStringWithDateInfo(saveTime, value.toString()))
    }

    /**
     * 保存序列化 {@code value} 数据到 {@code key} 文件中
     * @param key 文件名
     * @param value Serializable
     */
    override fun put(key: String, value: Serializable) {
        put(key, value, -1)
    }

    /**
     * 保存序列化 {@code value} 数据到 {@code key} 文件中，有效期时间 {@code saveTime}
     * @param key 文件名
     * @param value Serializable
     * @param saveTime 有效期
     */
    override fun put(key: String, value: Serializable, saveTime: Int) {
        var oos: ObjectOutputStream? = null
        try {
            val baos = ByteArrayOutputStream()
            oos = ObjectOutputStream(baos)
            oos.writeObject(value)
            val data: ByteArray = baos.toByteArray()
            if (saveTime != -1) {
                put(key, data, saveTime)
            } else {
                put(key, data)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                oos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
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
        put(key, BitmapUtils.bitmap2Bytes(value), saveTime)
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
        val file: File = mDisLruCache.get(key)
        if (!file.exists()) return null

        var br: BufferedReader? = null
        var removeFile = false
        try {
            br = BufferedReader(FileReader(file))
            var readString = ""
            var currentLine: String
            while (br.readLine().also { currentLine = it } != null) readString += currentLine
            return if (!TimeUtils.isDue(readString)) {
                TimeUtils.clearDateInfo(readString)
            } else {
                removeFile = true
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                br?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            //过期数据删除
            if (removeFile) {
                mDisLruCache.remove(file)
            }
        }
    }

    /**
     * 获取 {@code key} 文件中 JSONObject 缓存数据
     * @param key 文件名
     */
    override fun getAsJSONObject(key: String): JSONObject? {
        val json: String? = getAsString(key)
        try {
            if (json != null) {
                return JSONObject(json)
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
        val json: String? = getAsString(key)
        try {
            if (json != null) {
                return JSONArray(json)
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
        val file: File = mDisLruCache.get(key)
        if (!file.exists()) return null

        var raFile: RandomAccessFile? = null
        var removeFile = false
        try {
            raFile = RandomAccessFile(file, "r")
            val byteArray = ByteArray(raFile.length().toInt())
            raFile.read(byteArray)
            return if (!TimeUtils.isDue(byteArray)) {
                TimeUtils.clearDateInfo(byteArray)
            } else {
                removeFile = true
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                raFile?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            //过期数据删除
            if (removeFile) {
                mDisLruCache.remove(file)
            }
        }
    }

    /**
     * 获取 {@code key} 文件中 Serializable 缓存数据
     * @param key 文件名
     */
    override fun getAsObject(key: String): Any? {
        val data: ByteArray? = getAsBinary(key)
        if (data != null) {
            var ois: ObjectInputStream? = null
            try {
                val bais = ByteArrayInputStream(data)
                ois = ObjectInputStream(bais)
                return ois.readObject()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    ois?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return null
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