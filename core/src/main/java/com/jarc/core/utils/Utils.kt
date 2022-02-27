package com.jarc.core.utils

import java.security.MessageDigest

object Utils {

    const val TIMESTAMP = "timestamp"
    const val HASH = "hash"

    private fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(
        str.toByteArray(
            Charsets.UTF_8
        )
    )

    private fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

    fun Int.getOffset() = (if (this == 0) 40 else this * 40).toString()

    private fun getTimeStamp() = (System.currentTimeMillis() / 1000).toString()

    fun getTimeStampPlusHash(): Map<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()

        val timestamp = getTimeStamp()

        result[TIMESTAMP] = timestamp
        result[HASH] = md5(timestamp + PRIVATE_KEY + PUBLIC_KEY).toHex()

        return result
    }

    fun getImageUrl(
        path: String,
        extension: String,
        size: AspectRatio.ImageSize,
        origin: AspectRatio.Origin
    ) =
        if (origin == AspectRatio.Origin.LIST) "$path/${StandardAspectRatio.getSize(size)}.$extension" else "$path.$extension"

}