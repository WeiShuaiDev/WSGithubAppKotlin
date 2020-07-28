package com.linwei.cams.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import com.linwei.cams.ext.isNotNullOrEmpty
import java.util.*

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2019/5/19
 * @Contact: linwei9605@gmail.com"
 * @Follow: https://github.com/WeiShuaiDev
 * @Description: 国际化处理
 *-----------------------------------------------------------------------
 */
object AppLanguageUtils {
    /**
     * 系统语言类型
     */
    private val mLanguageTypes: MutableMap<String, Locale> =
        mutableMapOf(
            "zh" to Locale.SIMPLIFIED_CHINESE,
            "en" to Locale.ENGLISH
        )

    /**
     * 增加系统语言类型
     */
    fun addLanguageType(language: String, local: Locale) {
        mLanguageTypes[language] = local
    }

    fun setLanguage(context: Context, language: String) {
        val resources: Resources = context.applicationContext.resources
        val config: Configuration = resources.configuration

        val locale: Locale? = getLocaleByLanguage(language)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
        }
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }


    fun attachBaseContext(context: Context, language: String): Context {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            return updateResources(context, getLocaleByLanguage(language))
        }
        return context
    }

    /**
     * 根据language key获取Locale
     */
    private fun getLocaleByLanguage(language: String?): Locale? {
        if (language.isNotNullOrEmpty()) {
            if (mLanguageTypes.containsKey(language)) {
                return mLanguageTypes[language]
            }
        }
        return Locale.getDefault()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, locale: Locale?): Context {
        val resources: Resources = context.resources
        val config: Configuration = resources.configuration
        config.setLocale(locale)
        config.setLocales(LocaleList(locale))
        return context.createConfigurationContext(config)
    }
}