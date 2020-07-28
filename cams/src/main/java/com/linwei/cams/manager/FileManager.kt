package com.linwei.cams.manager
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/5/25
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class FileManager  private constructor() {

    companion object {
        private var INSTANCE: FileManager? = null

        @JvmStatic
        fun getInstance(): FileManager {
            return INSTANCE
                ?: FileManager().apply {
                    INSTANCE = this
                }
        }
    }
}