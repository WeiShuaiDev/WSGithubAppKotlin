package com.linwei.cams.http.glide.loader

import com.bumptech.glide.load.Key
import java.security.MessageDigest

/**
 * ---------------------------------------------------------------------
 * @Author: WeiShuai
 * @Time: 2020/8/30
 * @Contact linwei9605@gmail.com
 * @Follow https://github.com/WeiShuaiDev
 * @Description:
 *-----------------------------------------------------------------------
 */
class GlideCacheKey constructor(private val id: String, private val signature: Key) : Key {

    override fun equals(o: Any?): Boolean {
        if (this === o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val that = o as GlideCacheKey?

        return id == that!!.id && signature == that.signature
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + signature.hashCode()
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(id.toByteArray(charset(Key.STRING_CHARSET_NAME)))
        signature.updateDiskCacheKey(messageDigest)
    }
}