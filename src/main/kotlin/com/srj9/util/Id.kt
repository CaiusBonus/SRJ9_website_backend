package com.srj9.util

import java.io.Serializable

class Id : Serializable {

    companion object {

        private const val prefix = "uuid-"

        private val dictionaryEncode = mapOf(
                "-" to "fa33",
                "0" to "cfbc",
                "1" to "rjqu",
                "2" to "myed",
                "3" to "oywr",
                "4" to "ukvc",
                "5" to "a9by",
                "6" to "aq23",
                "7" to "qe2w",
                "8" to "4gia",
                "9" to "6874")


        private val dictionaryDecode by lazy {
            dictionaryEncode.map { (k,v) -> v to k }.toMap()
        }
    }

    private val id: Long
    private val uuid: String

    constructor(id: Long) {
        this.id = id
        this.uuid = encode(id)
    }

    constructor(uuid: String) {
        this.id = decode(uuid)
        this.uuid = uuid
    }

    private fun encode(id: Long): String {
        return id.run {
            "$prefix${toString()
                    .toCharArray()
                    .map { dictionaryEncode[it.toString()] }
                    .joinToString("-")}"
        }
    }

    private fun decode(uuid: String): Long {
        return uuid.run {
            val string = removePrefix(prefix)
                    .split("-")
                    .map { dictionaryDecode[it] }
                    .joinToString("")
            if (string.isBlank()) {
                return -1L
            }
            string.toLong()
        }
    }

    fun id() = id

    fun uuid() = uuid

    override fun toString(): String = "Id($id, $uuid)"
}