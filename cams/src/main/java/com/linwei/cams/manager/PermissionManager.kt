package com.linwei.cams.manager
/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/7/3
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class PermissionManager  private constructor(){

    companion object {
        private var INSTANCE: PermissionManager? = null

        @JvmStatic
        fun getInstance(): PermissionManager {
            return INSTANCE
                ?: PermissionManager().apply {
                    INSTANCE = this
                }
        }
    }
}