package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.util.Base64
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File

class StylizeModel(private val activity: AppCompatActivity) {
    interface StylizeService {
        @GET("hello")
        fun getTest(@Header("authorization") credential: String): Call<ResponseBody>

        @Multipart
        @POST("theme_color")
        fun getThemeColor(@Header("authorization") credential: String, @Part("image") image: RequestBody): Call<ResponseBody>//, @Part("count") count: Int): Call<ThemeColorImage>
    }

    data class Hello(var string: String)
    data class ThemeColorImage(val image: File)

    private val retrofit = Retrofit.Builder()
            //.addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.baseUrl)
            .build()
    private val service = retrofit.create(StylizeService::class.java)

    private lateinit var callHello: Call<ResponseBody>
    private lateinit var callThemeColor: Call<ResponseBody>

    private lateinit var hello: Hello
    private lateinit var themeColorImage: ThemeColorImage


    fun getHello() {
        callHello = service.getTest("Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP))
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

    fun getThemeColor(image: File, count: Int) {
        val imageBody = RequestBody.create(MediaType.parse("image/jpeg"), image)
        //val countBody = RequestBody.create(MediaType.parse("text/plain"), count.toString())
        callThemeColor = service.getThemeColor("Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP), imageBody)//, count)
        callThemeColor.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val themeColorImage = response!!.body()!!
                // TODO: activity stop the progress bar and show the image
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                RuntimeException(t!!.message)
            }
        })
        // TODO: activity create a progress bar to wait network response
    }
}
