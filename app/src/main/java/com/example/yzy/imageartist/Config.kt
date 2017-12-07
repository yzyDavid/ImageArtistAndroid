package com.example.yzy.imageartist

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit

object Config {
    @SuppressLint("AuthLeak")
    val baseUrl = "https://cluster.yuzhenyun.me/api/"

    val timeout: Long = 20
    val timeoutUnit = TimeUnit.SECONDS
}
