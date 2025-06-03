package com.example.marvel.utils

import java.math.BigInteger
import java.security.MessageDigest

object ApiUtils {
    private const val publicKey = "9fd781519872e8b621d56bb604f014ac"
    private const val privateKey = "0862f342a0807a61f5733e539decef4c3d94f4d4"

    fun getTimestamp(): String = System.currentTimeMillis().toString()

    fun getApiKey(): String = publicKey

    fun getHash(ts: String): String {
        val input = ts + privateKey + publicKey
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}
