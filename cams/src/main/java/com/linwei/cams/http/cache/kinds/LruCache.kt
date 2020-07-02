package com.linwei.cams.http.cache.kinds

import com.linwei.cams.http.cache.Cache
import javax.inject.Inject

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/26
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 内存缓存模块，{@link LruCache<K,V>} 实现了 {@link Cache<K,V>} 接口, {@link LruCache<K,V>} 核心方法 {@link #trimToSize(size)}
 *               最近最少使用缓存数据进行优化删除。存储缓存 {@link @put()} 出现超过 {@link mMaxSize}最大缓存大小,程序进行旧缓存数据删除，来满足新文件存储。
 *               同时更新最新存储信息。 {@code getItemSize()} 指定每个 {@code item} 所占用的size,默认为1,这个 size 的单位必须和构造函数所传入的@{mMaxSize}一致。
 *              K:表示存储Key,内存存储中必须唯一
 *              V:代表存储Value,存储内容数据
 *-----------------------------------------------------------------------
 */
class LruCache<K, V> @Inject constructor(
    size: Int, var mMaxSize: Int = size,
    var mInitialMaxSize: Int = size
) :
    Cache<K, V> {
    private var mCurrentSize: Int = 0
    private val mCacheMap: LinkedHashMap<K, V> = linkedMapOf()


    /**
     * 返回每个 {@code item} 所占用的 size,默认为1,这个 size 的单位必须和构造函数所传入的 size 一致
     * 子类可以重写这个方法以适应不同的单位,比如说 bytes
     *
     * @param item 每个 {@code item} 所占用的 size
     * @return 单个 item 的 {@code size}
     */
    protected fun getItemSize(): Int {
        return 1
    }

    /**
     * 设置一个系数应用于当时构造函数中所传入的 size, 从而得到一个新的 {@link #maxSize}
     * 并会立即调用 {@link #evict} 开始清除满足条件的条目
     *
     * @param multiplier 系数
     */
    fun setSizeMultiplier(multiplier: Float) {
        if (multiplier < 0) {
            throw IllegalArgumentException("Multiplier must be >= 0")
        }
        mMaxSize = Math.round(mInitialMaxSize * multiplier)
        evict()
    }

    /**
     * 当缓存中已占用的总 size 大于所能允许的最大 size ,会使用  {@link #trimToSize(int)} 开始清除满足条件的条目
     */
    private fun evict() {
        trimToSize(mMaxSize)
    }

    /**
     * 当指定的 size 小于当前缓存已占用的总 size 时,会开始清除缓存中最近最少使用的条目
     *
     * @param size {@code size}
     */
    private fun trimToSize(size: Int) {
        var last: MutableMap.MutableEntry<K, V>
        while (mCurrentSize > size) {
            last = mCacheMap.entries.iterator().next()
            mCurrentSize -= getItemSize()
            mCacheMap.remove(last.key)
            onItemEvicted(last.key, last.value)
        }
    }

    /**
     * 当缓存中有被驱逐的条目时,会回调此方法,默认空实现,子类可以重写这个方法
     *
     * @param key   被驱逐条目的 {@code key}
     * @param value 被驱逐条目的 {@code value}
     */
    protected fun onItemEvicted(key: K, value: V) {

    }

    /**
     * 返回当前缓存已占用的总 size
     *
     * @return {@code size}
     */
    override fun size(): Int = mCurrentSize

    /**
     * 返回当前缓存所能允许的最大 size
     *
     * @return {@code maxSize}
     */
    override fun getMaxSize(): Int = mMaxSize

    /**
     * 返回这个 {@code key} 在缓存中对应的 {@code value}, 如果返回 {@code null} 说明这个 {@code key} 没有对应的 {@code value}
     *
     * @param key 用来映射的 {@code key}
     * @return {@code value}
     */
    override fun get(key: K): V? = mCacheMap.get(key)

    /**
     * 将 {@code key} 和 {@code value} 以条目的形式加入缓存,如果这个 {@code key} 在缓存中已经有对应的 {@code value}
     * 则此 {@code value} 被新的 {@code value} 替换并返回,如果为 {@code null} 说明是一个新条目
     * <p>
     * 如果 {@link #getItemSize(value)} 增加到内存中返回的 size 大于或等于缓存所能允许的最大 maxsize, 则启动优化缓存数据
     * 此时会回调 {@link #onItemEvicted(Object, Object)} 通知此方法当前被驱逐的条目。
     *
     * @param key   通过这个 {@code key} 添加条目
     * @param value 需要添加的 {@code value}
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value}, 则返回之前的 {@code value} 否则返回 {@code null}
     */
    override fun put(key: K, value: V): V? {
        mCurrentSize += getItemSize()
        if (mCurrentSize >= mMaxSize) {
            evict()
        }

        val result: V? = mCacheMap.put(key, value)
        if (result != null) {
            mCurrentSize -= getItemSize()
        }
        return value
    }

    /**
     * 移除缓存中这个 {@code key} 所对应的条目,并返回所移除条目的 {@code value}
     * 如果返回为 {@code null} 则有可能时因为这个 {@code key} 对应的 {@code value} 为 {@code null} 或条目不存在
     *
     * @param key 使用这个 {@code key} 移除对应的条目
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value} 并且删除成功则返回删除的 {@code value}, 否则返回 {@code null}
     */
    override fun remove(key: K): V? {
        val value: V? = mCacheMap.remove(key)
        if (value != null) {
            mCurrentSize -= getItemSize()
        }
        return value
    }

    /**
     * 如果这个 {@code key} 在缓存中有对应的 {@code value} 并且不为 {@code null},则返回 true
     *
     * @param key 用来映射的 {@code key}
     * @return {@code true} 为在容器中含有这个 {@code key}, 否则为 {@code false}
     */
    override fun containsKey(key: K): Boolean = mCacheMap.containsKey(key)

    /**
     * 返回当前缓存中含有的所有 {@code key}
     *
     * @return {@code keySet}
     */
    override fun keySet(): Set<K>? = mCacheMap.keys

    /**
     * 清除缓存中所有的内容
     */
    override fun clear() {
        trimToSize(0)
    }

}