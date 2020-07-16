package com.linwei.cams.base.global

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/6/3
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 解析 `AndroidManifest.xml` 文件中数据字段
 *-----------------------------------------------------------------------
 */
class ManifestParser private constructor(val mContext: Context) {

    companion object {
        private var INSTANCE: ManifestParser? = null

        @JvmStatic
        fun getInstance(context: Context): ManifestParser {
            return INSTANCE ?: ManifestParser(context).apply {
                INSTANCE = this
            }
        }
    }

    /**
     * 使用反射机制，获取 [className] 类对象，并进行类型判断强转换为 [ConfigModule] 类型
     * @param className [String] 解析ConfigModule对象类名
     * @return instance [ConfigModule]
     */
    private fun parseModule(className: String): ConfigModule? {
        val clazz: Class<*>
        try {
            clazz = Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException(
                "Unable to find ConfigModule implementation",
                e
            )
        }
        val instance: Any
        try {
            instance = clazz.newInstance()
        } catch (e: InstantiationException) {
            throw  RuntimeException(
                "Unable to instantiate ConfigModule implementation for $clazz",
                e
            )
        } catch (e: IllegalAccessException) {
            throw  RuntimeException(
                "Unable to instantiate ConfigModule implementation for $clazz",
                e
            )
        }

        if (instance is ConfigModule)
            return instance

        return null
    }

    /**
     * 解析 `AndroidManifest.xml` 清单文件 `meta-data` 字段，获取 `meta-data` 中 value=`GlobalConfigModule` 数据，
     * 并存储到 [configModuleLists] 并返回
     * @return configModuleLists [MutableList]
     */
    fun obtainConfigModule(): MutableList<ConfigModule> {
        val configModuleLists: MutableList<ConfigModule> = mutableListOf()
        try {
            val appInfo: ApplicationInfo = mContext.packageManager.getApplicationInfo(
                mContext.packageName,
                PackageManager.GET_META_DATA
            )
            appInfo.metaData?.keySet()?.forEach {
                if (ConfigModuleConstant.GLOBAL_CONFIG_MODULE == appInfo.metaData[it]) {
                    val configModule: ConfigModule? = parseModule(it)
                    if (configModule != null) {
                        configModuleLists.add(configModule)
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException("Unable to find metadata to parse ConfigModule", e);
        }

        return configModuleLists
    }

}