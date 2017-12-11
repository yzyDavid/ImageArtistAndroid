package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Modify : AppCompatActivity() {
    public lateinit var mPhoto: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        this.setTitle(R.string.modify)
        mPhoto = findViewById(R.id.modify_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }
}
