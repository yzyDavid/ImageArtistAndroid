package com.example.yzy.imageartist

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit

object Config {
    val baseUrl = "https://cluster.yuzhenyun.me/api/"

    val timeout: Long = 20
    val timeoutUnit = TimeUnit.SECONDS

    val contrastAlphaIncrease = 1.1
    val contrastAlphaDecrease = 0.9

    val lightBetaIncrease = 10.toDouble()
    val lightBetaDecrease = (-10).toDouble()
}
