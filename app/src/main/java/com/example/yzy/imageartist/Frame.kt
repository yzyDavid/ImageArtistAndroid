package com.example.yzy.imageartist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Picture
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.view.View
import android.widget.ImageView
import java.net.URI
import android.net.Uri
import android.os.Environment
import android.view.ActionMode
import android.widget.Button
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

class Frame : AppCompatActivity() {
    public lateinit var mPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        this.setTitle(R.string.frame)
      //  mPhoto=findViewById(R.id.frame_photo)
        // WorkspaceManager.bitmap?.let {
          //   mPhoto.setImageBitmap(it)
         //}
    }
}

