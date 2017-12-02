package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.Menu
import android.view.MenuItem
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

    lateinit var mPhoto: ImageView
    // private val stylizeModel = StylizeModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_stylize)
        this.setTitle(R.string.stylize)
        mPhoto = findViewById(R.id.stylize_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_style, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        return true
    }
}
