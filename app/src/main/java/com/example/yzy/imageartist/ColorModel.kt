package com.example.yzy.imageartist

import android.graphics.BitmapFactory
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

class ColorModel(private val activity: Editor) {
    interface ColorService {
        @GET("hello")
        fun getTest(@Header("authorization") credential: String): Call<ResponseBody>

        @POST("theme_color_count")
        fun getThemeColor(@Header("authorization") credential: String, @Body body: RequestBody): Call<ResponseBody>
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .build()
    private val service = retrofit.create(ColorService::class.java)
    private val credential = "Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP)

    fun getHello() {
        val callHello = service.getTest(credential)
        callHello.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                response!!.body()!!.string()
                // TODO: activity stop the progress bar and show the text
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                RuntimeException(t!!.message)
            }
        })
        // TODO: activity create a progress bar to wait network response
    }

    fun getThemeColor(image: File, count: Int) {
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", image.name, RequestBody.create(MediaType.parse("image/jpeg"), image))
                .addFormDataPart("count", count.toString())
                .build()
        val callThemeColor = service.getThemeColor(credential, requestBody)
        callThemeColor.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val themeColorImage = response!!.body()!!
                val bytes = themeColorImage.bytes()
                WorkspaceManager.bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                activity.mPhoto.setImageBitmap(WorkspaceManager.bitmap)
                // TODO: activity stop the progress bar and show the image
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                RuntimeException(t!!.message)
            }
        })
        // TODO: activity create a progress bar to wait network response
    }
}
