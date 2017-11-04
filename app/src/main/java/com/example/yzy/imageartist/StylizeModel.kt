package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.util.Base64
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header

class StylizeModel(private val activity: AppCompatActivity) {
    interface StylizeService {
        @GET("hellp")
        fun getTest(@Header("authorization") credential: String): Call<ResponseBody>
    }
    data class Hello(var string: String)

    private val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .build()
    private val service = retrofit.create(StylizeService::class.java)
    private val callHello = service.getTest("Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP))
    private lateinit var hello: Hello

    fun getHello() {
        callHello.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                hello.string = response!!.body()!!.string()
                // TODO: activity stop the progress bar and show the text
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                RuntimeException(t!!.message)
            }
        })
        // TODO: activity create a progress bar to wait network response
    }
}
