package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

class StylizeModel(private val activity: AppCompatActivity, private val requestcode: Int) {
    interface StylizeService {
        @GET("hellp")
        fun getTest(@Header("authorization") credential: String): Call<ResponseBody>
    }
    data class Hello(var string: String)
}
