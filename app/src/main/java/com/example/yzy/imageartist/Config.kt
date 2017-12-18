package com.example.yzy.imageartist

import android.annotation.SuppressLint
import java.util.concurrent.TimeUnit

object Config {
    const val baseUrl = "https://cluster.yuzhenyun.me/api/"

    const val timeout: Long = 20
    val timeoutUnit = TimeUnit.SECONDS

    const val contrastAlphaIncrease = 1.1
    const val contrastAlphaDecrease = 0.9

    const val lightBetaIncrease = 10.toDouble()
    const val lightBetaDecrease = (-10).toDouble()

    const val colorPixelLimit = 640
}
