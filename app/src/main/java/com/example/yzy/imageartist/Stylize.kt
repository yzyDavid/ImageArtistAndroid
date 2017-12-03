package com.example.yzy.imageartist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Stylize : AppCompatActivity() {
    private val PICTURE: Int = 0
    private lateinit var mButtonHello: Button
    public lateinit var mPhoto: ImageView
    private lateinit var mChoosepicButton: FloatingActionButton
    private val galleryModel = GalleryModel(this, PICTURE)
    //private val stylizeModel = StylizeModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stylize)
        this.setTitle(R.string.stylize)
        mPhoto = findViewById(R.id.stylize_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
        mChoosepicButton = findViewById(R.id.choosepic_button)
        mChoosepicButton.setOnClickListener{
            galleryModel.startGallery()
        }
    }

    fun stylizeShow(view: View) {
        mButtonHello.setOnClickListener {
            //stylizeModel.getHello()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            PICTURE -> WorkspaceManager.style = galleryModel.getBitmap(resultCode, data!!)
        }

    }

}
