package com.example.yzy.imageartist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView

class Stylize : AppCompatActivity() {

    lateinit var mPhoto: ImageView
    private val PICTURE = 0
    private val gallery = GalleryModel(this, PICTURE)
    private val stylizeModel = StylizeModel(mPhoto)

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
        when (item?.itemId) {
            R.id.menu_choose_style -> {
                gallery.startGallery()
            }
            R.id.menu_transfer -> {
                WorkspaceManager.bitmap?.let { that ->
                    WorkspaceManager.style?.let {
                        stylizeModel.uploadImage(that)
                        stylizeModel.uploadStyle(it)
                        stylizeModel.getTransfer()
                    }
                }
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            PICTURE -> {
                data?.let {
                    WorkspaceManager.style = gallery.getBitmap(resultCode, it)
                }
            }
        }
    }
}
