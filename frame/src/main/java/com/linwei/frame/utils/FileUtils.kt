package com.linwei.frame.utils

import java.io.File

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/19
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: BitmapUtils工具类
 * ---------------------------------------------------------------------
 */
object FileUtils {

    /**
     * 判断 [file] 文件是否存在，如果不存在，则进行创建
     * @param file [File]
     */
    fun makeDirs(file: File): File {
        if (!file.exists()) file.mkdirs()
        return file
    }
}