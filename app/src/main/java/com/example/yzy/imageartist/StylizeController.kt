package com.example.yzy.imageartist

import android.graphics.Bitmap
import android.util.Base64
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Header

interface StylizeService {
    @GET("hello")
    fun getTest(@Header("authorization") credential: String): Call<ResponseBody>
}

class StylizeController {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .build()

    private val service = retrofit.create(StylizeService::class.java)

    fun getTest() {
        val call = service.getTest("Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP))
        var res: String
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                res = response!!.body()!!.string()

            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}