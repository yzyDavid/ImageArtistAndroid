package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class ColorActivity : AppCompatActivity() {
    public lateinit var mPhoto: ImageView
    public lateinit var mProgressBar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)
        this.setTitle(R.string.choose_color)
        mPhoto = findViewById(R.id.color_photo)

        mProgressBar = findViewById(R.id.progressbar_color)

        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }
}
