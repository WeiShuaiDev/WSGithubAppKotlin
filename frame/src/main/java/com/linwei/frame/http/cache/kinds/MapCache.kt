package com.linwei.frame.http.cache.kinds

import com.linwei.frame.http.cache.Cache
import java.lang.ref.SoftReference
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/26
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 内存缓存模块，{@link LruCache<K,V>} 实现了 {@link Cache<K,V>} 接口, {@link LruCache<K,V>} 会根据内存最大存储大小，
 *               对内存数据进行优化删除。{@code mMaxSize}指定内存大小, {@code getItemSize()} 指定每个 {@code item} 所占用的size,
 *               默认为1,这个 size 的单位必须和构造函数所传入的@{mMaxSize}一致
 *              K:表示存储Key,内存存储中必须唯一
 *              V:代表存储Value,存储内容数据
 *-----------------------------------------------------------------------
 */
class MapCache<K, V> : Cache<K, V> {
    /**
     * {@link mMapCache} 是一个线程安全，同时使用 {@code SoftReference}软引用包裹数据源，在内存不足出现报警，GC会进行数据回收
     * 一般在使用HashMap充当内存存储器，保存 {@code Bitmap}数据源，容易出现OOM
     */
    private val mMapCache: MutableMap<K, SoftReference<V>> = Collections.synchronizedMap(
        mutableMapOf()
    )

    /**
     * {@link mMapCache}内存存储器当前存储 size
     */
    override fun size(): Int = mMapCache.size

    /**
     * {@link mMapCache}内存存储器最大存储大小,默认设置 {@link Int.MAX_VALUE} Int最大值
     */
    override fun getMaxSize(): Int = Int.MAX_VALUE

    /**
     * {@link mMapCache}内存存储器，根据 {@code key} 查找返回 {@code value} 值
     */
    override fun get(key: K): V? = mMapCache[key]?.get()


    /**
     * {@link mMapCache}内存存储器，根据 {@code key} 保存对应{@code value} 值。在存储 {@code value} 新值，
     * 先判断 {@code key} 在 {@link mMapCache}是否存在旧值。
     * 存在，返回旧值 {@code oldValue} ，并根据 {@code key},保存新值{@code value}
     * 不存在，返回空，并根据 {@code key},保存新值{@code value}
     */
    override fun put(key: K, value: V): V? {
        val oldValue: V? = mMapCache[key]?.get()
        mMapCache[key] = SoftReference<V>(value)
        return oldValue
    }

    /**
     * {@link mMapCache}内存存储器，根据 {@code key} 删除 {@link mMapCache} 中对应 {@code value}
     */
    override fun remove(key: K): V? = mMapCache.remove(key)?.get()

    /**
     * {@link mMapCache}内存存储器，根据 {@code key} 判断 {@link mMapCache} 中是否存在对应 {@code value}
     */
    override fun containsKey(key: K): Boolean = mMapCache.containsKey(key)

    /**
     * {@link mMapCache}内存存储器，返回 {@link mMapCache} 中所有 {@code key} 值，并以Set数据结构返回
     */
    override fun keySet(): Set<K>? = mMapCache.keys

    /**
     * 删除 {@link mMapCache} 内存存储器中所有数据
     */
    override fun clear() {
        mMapCache.clear()
    }
}