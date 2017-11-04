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
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

class Frame : AppCompatActivity() {
    private val CAMERA: Int = 1
    private val PICTURE: Int = 0
    private lateinit var mTextCamera: TextView
    private lateinit var mTextAlbum: TextView
    lateinit var mImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        this.setTitle(R.string.frame)
        mTextAlbum = findViewById(R.id.album_text)
        mTextAlbum.setOnClickListener {
            val album = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(album, PICTURE)
        }
        mTextCamera = findViewById(R.id.camera_text)
        mTextCamera.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage: Uri = data.data
            val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
            val c: Cursor = this.contentResolver.query(selectedImage, filePathColumns, null, null, null)
            c.moveToFirst()
            val columnIndex: Int = c.getColumnIndex(filePathColumns[0])
            var picturePath: String = c.getString(columnIndex)
            c.close()
        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && null != data) {
            val sdState: String = Environment.getExternalStorageState()
            if (sdState != Environment.MEDIA_MOUNTED) {
                this.toast("SD card unmount")
                return
            }
            val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd_hhmmss")
            val name: String = dateFormat.format(Calendar.getInstance(Locale.CHINA)) + "jpg"
            val bundle: Bundle = data.extras
            val bitmap: Bitmap = bundle.get("data") as Bitmap
            val file: File = File("/sdcard/pintu/")
            file.mkdirs()
            val filename: String = file.getPath() + name
            var fout: FileOutputStream = FileOutputStream(filename)
            try {
                fout = FileOutputStream(filename)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fout.flush()
                fout.close()
            }
        }
    }
}
