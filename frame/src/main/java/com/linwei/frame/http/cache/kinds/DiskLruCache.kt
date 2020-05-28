package com.linwei.frame.http.cache.kinds

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 内存缓存模块，{@link LruCache<K,V>} 实现了 {@link Cache<K,V>} 接口, {@link LruCache<K,V>} 会根据内存最大存储大小，
 *               对内存数据进行优化删除。{@code mMaxSize}指定内存大小, {@code getItemSize()} 指定每个 {@code item} 所占用的size,
 *               默认为1,这个 size 的单位必须和构造函数所传入的@{mMaxSize}一致
 *              K:表示存储Key,内存存储中必须唯一
 *              V:代表存储Value,存储内容数据
 *-----------------------------------------------------------------------
 */
class DiskLruCache {


}