package com.linwei.frame.manager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description: 存储方式:内部提供{@Link LruCache}内存存储、{@Link DiskLruCache}硬件存储，根据不同业务需求，进行信息存储。
 *               存储类型:byte、short、int、long、double、float、String、JSONObject、JSONArray、Array[]、Serializable、Bitmap、Drawable
 *-----------------------------------------------------------------------
 */
class DiskStorageManager {

    companion object {
        private var INSTANCE: DiskStorageManager? = null

        @JvmStatic
        fun getInstance(): DiskStorageManager {
            return INSTANCE ?: DiskStorageManager().apply {
                INSTANCE = this
            }
        }
    }


}