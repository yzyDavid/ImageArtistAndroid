package com.example.yzy.imageartist

import android.Manifest
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
import org.jetbrains.anko.find
import permissions.dispatcher.NeedsPermission
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Stylize : AppCompatActivity() {
    private val PICTURE: Int = 0
    private lateinit var mButtonHello: Button
    public lateinit var mPhoto: ImageView
    private lateinit var mButtonChoosepic: FloatingActionButton
    //private val stylizeModel = StylizeModel(this)
    private val galleryModel = GalleryModel(this, PICTURE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stylize)
        this.setTitle(R.string.stylize)
        mPhoto = findViewById(R.id.stylize_photo)
        mButtonChoosepic = findViewById(R.id.choosepic_button)
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
