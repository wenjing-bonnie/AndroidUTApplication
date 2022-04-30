package com.wj.ut

import kotlinx.coroutines.delay

/**
 * create by wenjing.liu at 2022/4/30
 */
class HttpApi {
    /**
     * @param password "123456" -> 0, "12345678" -> 1, "123" -> 401
     */
    suspend fun login(userName: String, password: String): Int {
        println("start to login .....")
        delay(1000)
        println("finish to login .....")
        return when (password) {
            "123456" -> 0
            "12345678" -> 1
            "123" -> 401
            else -> 10000
        }
    }
}