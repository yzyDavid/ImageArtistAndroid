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
    public var CAMERA: Int = 1
    public var PICTURE: Int = 0
    private lateinit var mTextCamera: TextView
    private lateinit var mTextAlbum: TextView
    lateinit var mImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frame)
        title = "边框"
        mTextAlbum = findViewById(R.id.album_text)
        mTextAlbum.setOnClickListener {
            var album = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(album, PICTURE)
        }
        mTextCamera = findViewById(R.id.camera_text)
        mTextCamera.setOnClickListener {
            var camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, CAMERA)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && null != data) {
            var selectedImage: Uri = data.getData()
            var filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
            var c: Cursor = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null)
            c.moveToFirst()
            var columnIndex: Int = c.getColumnIndex(filePathColumns[0])
            var picturePath: String = c.getString(columnIndex)
            c.close()
        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && null != data) {
            var sdState: String = Environment.getExternalStorageState()
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                this.toast("SD card unmount")
                return
            }
            var dateformat: DateFormat = SimpleDateFormat("yyyyMMdd_hhmmss")
            var name: String = dateformat.format(Calendar.getInstance(Locale.CHINA)) + "jpg"
            var bundle: Bundle = data.getExtras()
            var bitmap: Bitmap = bundle.get("data") as Bitmap
            var file: File = File("/sdcard/pintu/")
            file.mkdirs()
            var filename: String = file.getPath() + name
            var fout: FileOutputStream = FileOutputStream(filename)
            try {
                fout = FileOutputStream(filename)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    fout.flush()
                    fout.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }


    }


}
