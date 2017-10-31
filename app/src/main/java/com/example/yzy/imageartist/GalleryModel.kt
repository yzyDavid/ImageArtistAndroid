package com.example.yzy.imageartist

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity

class GalleryModel(private val activity: AppCompatActivity, private val requestCode: Int) {
    fun getBitmap(resultCode: Int, data: Intent): Bitmap {
        if (resultCode != Activity.RESULT_OK) {
            throw RuntimeException("Not a valid result")
        }
        val selectedImage = data.data
        val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
        val c = activity.contentResolver.query(selectedImage, filePathColumns, null, null, null)
        c.moveToFirst()
        val columnIndex = c.getColumnIndex(filePathColumns[0])
        val picturePath = c.getString(columnIndex)
        c.close()
        return BitmapFactory.decodeFile(picturePath)
    }

    fun startGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(intent, requestCode)
    }
}