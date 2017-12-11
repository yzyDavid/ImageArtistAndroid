package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Color : AppCompatActivity() {
    public lateinit var mPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        this.setTitle(R.string.choose_color)
        mPhoto = findViewById(R.id.color_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }
}
