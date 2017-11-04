package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Button
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Stylize : AppCompatActivity() {

    private lateinit var mButtonHello: Button
    private val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .build()
    private val service = retrofit.create(StylizeModel.StylizeService::class.java)
    val call = service.getTest("Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP))
    private lateinit var hello: StylizeModel.Hello

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stylize)
        this.setTitle(R.string.stylize)
    }

    fun stylizeShow(view: View) {
        mButtonHello.setOnClickListener {
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                    hello.string = response!!.body()!!.string()
                }

                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    if (call!!.isCanceled) {

                    } else {
                        RuntimeException("No network")
                    }
                }
            })
        }
    }
}
