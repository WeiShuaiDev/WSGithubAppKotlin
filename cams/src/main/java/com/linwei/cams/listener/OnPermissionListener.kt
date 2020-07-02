package com.linwei.cams.listener

/**
 * @Author: WeiShuai
 * @Time: 2019/10/14
 * @Description: 权限申请回调
 */
interface OnPermissionListener {
    fun onGranted()
    fun onDenied(deniedPermissions: List<String>)
}