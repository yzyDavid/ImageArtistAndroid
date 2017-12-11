package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView

class Modify : AppCompatActivity() {
    lateinit var mPhoto: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        this.setTitle(R.string.modify)
        mPhoto = findViewById(R.id.modify_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
