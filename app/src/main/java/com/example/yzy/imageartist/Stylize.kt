package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Stylize : AppCompatActivity() {

    private lateinit var mButtonHello: Button
    public lateinit var mPhoto: ImageView
    //private val stylizeModel = StylizeModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stylize)
        this.setTitle(R.string.stylize)

        mPhoto = findViewById(R.id.stylize_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }

    fun stylizeShow(view: View) {
        mButtonHello.setOnClickListener {
            //stylizeModel.getHello()
        }
    }
}
