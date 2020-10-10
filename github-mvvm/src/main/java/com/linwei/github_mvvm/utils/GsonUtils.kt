package com.linwei.github_mvvm.utils

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.linwei.cams.ext.isNotNullOrEmpty
import java.util.*

/**
 * GSON序列化工具类
 */
object GsonUtils {

    fun getNoteJsonString(jsonString: String?, note: String): String {
        if (!jsonString.isNotNullOrEmpty()) {
            throw RuntimeException("getNoteJsonString jsonString empty")
        }
        if (!note.isNotNullOrEmpty()) {
            throw RuntimeException("getNoteJsonString note empty")
        }
        val element: JsonElement = JsonParser().parse(jsonString)
        if (element.isJsonNull) {
            throw RuntimeException("getNoteJsonString element empty")
        }
        return element.asJsonObject.get(note).toString()
    }


    fun <T> parserJsonToArrayBeans(jsonString: String, note: String, beanClazz: Class<T>): List<T> {
        val noteJsonString: String = getNoteJsonString(jsonString, note)
        return parserJsonToArrayBeans(noteJsonString, beanClazz)
    }


    fun <T> parserJsonToArrayBeans(jsonString: String?, beanClazz: Class<T>): List<T> {
        if (!jsonString.isNotNullOrEmpty()) {
            throw RuntimeException("parserJsonToArrayBeans jsonString empty")
        }
        val jsonElement: JsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parserJsonToArrayBeans jsonElement empty")
        }
        if (!jsonElement.isJsonArray) {
            throw RuntimeException("parserJsonToArrayBeans jsonElement is not JsonArray")
        }
        val jsonArray: JsonArray = jsonElement.asJsonArray
        val beans = ArrayList<T>()
        for (jsonElement2: JsonElement in jsonArray) {
            val bean: T = Gson().fromJson(jsonElement2, beanClazz)
            beans.add(bean)
        }
        return beans
    }


    fun <T> parserJsonToBean(jsonString: String?, clazzBean: Class<T>): T {
        if (!jsonString.isNotNullOrEmpty()) {
            throw RuntimeException("parserJsonToArrayBean jsonString empty")
        }
        val jsonElement: JsonElement = JsonParser().parse(jsonString)
        if (jsonElement.isJsonNull) {
            throw RuntimeException("parserJsonToArrayBean jsonElement empty")
        }
        if (!jsonElement.isJsonObject) {
            throw RuntimeException("parserJsonToArrayBean is not object")
        }
        return Gson().fromJson(jsonElement, clazzBean)
    }


    fun <T> parserJsonToBean(jsonString: String?, note: String, clazzBean: Class<T>): T {
        val noteJsonString: String = getNoteJsonString(jsonString, note)
        return parserJsonToBean(noteJsonString, clazzBean)
    }


    fun toJsonString(obj: Any?): String {
        return if (obj != null) {
            Gson().toJson(obj)
        } else {
            throw RuntimeException("obj could not be empty")
        }
    }
}

