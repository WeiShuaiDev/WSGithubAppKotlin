package com.linwei.cams.http.cache

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: Cache内存接口，提供一些常用内存存储操作。
 * Cache<K,V>   K:代表KEY  V:代表Value
 *-----------------------------------------------------------------------
 */
interface Cache<K, V> {

    /**
     * 返回当前缓存已占用的总 size
     *
     * @return {@code size}
     */
    fun size(): Int

    /**
     * 返回当前缓存所能允许的最大 size
     *
     * @return {@code maxSize}
     */
    fun getMaxSize(): Int

    /**
     * 返回这个{@code key}在缓存中对应的 {@code value}, 如果返回{@code null} 说明这个 {@code key} 没有对应的 {@code value}
     *
     * @param key key
     * @return value
     */
    fun get(key: K): V?

    /**
     * 将 {@code key}和 {@code value} 以条目的形式加入缓存,如果这个 {@code key} 在缓存中已经有对应的 {@code value}
     * 则此 {@code value} 被新的 {@code value} 替换并返回,如果为 null 说明是一个新条目
     *
     * @param key   key
     * @param value value
     * @return 如果这个 key 在容器中已经储存有 {@code value}, 则返回之前的 {@code value} 否则返回 {@code null}
     */
    fun put(key: K, value: V): V?

    /**
     * 移除缓存中这个 {@code key} 所对应的条目,并返回所移除条目的 {@code value}
     * 如果返回为 null 则有可能时因为这个 {@code key} 对应的 {@code value} 为 {@code null} 或条目不存在
     *
     * @param key key
     * @return 如果这个 {@code key} 在容器中已经储存有 {@code value} 并且删除成功则返回删除的  {@code value}, 否则返回 {@code null}
     */
    fun remove(key: K): V?

    /**
     * 如果这个 {@code key} 在缓存中有对应的  {@code value} 并且不为 {@code null}, 则返回true
     *
     * @param key key
     * @return true 为在容器中含有这个 {@code key} , 否则为 false
     */
    fun containsKey(key: K): Boolean

    /**
     * 返回当前缓存中含有的所有 {@code key}
     *
     * @return keySet
     */
    fun keySet(): Set<K>?

    /**
     * 清除缓存中所有的内容
     */
    fun clear()

    interface Factory {
        /**
         * @param type 框架中需要缓存的模块类型
         * @return [Cache]
         */
        fun <K, V> build(type: CacheType): Cache<K, V>
    }
}