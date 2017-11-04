package com.example.yzy.imageartist

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import android.support.v4.content.FileProvider
import java.util.*

class CameraModel(private val activity: AppCompatActivity, private val requestCode: Int) {
    private var photoPath: String = ""
    fun getBitmap(resultCode: Int, data: Intent): Bitmap {
        if (resultCode != Activity.RESULT_OK) {
            throw RuntimeException("Not a valid result")
        }
        return BitmapFactory.decodeFile(photoPath)
    }

    @Throws(IOException::class)
    fun startCamera() {
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val timeStamp = SimpleDateFormat("""yyyyMMdd_HHmmss""", Locale.CHINA)
        val fileName = "ImageArtist_" + timeStamp
        val image = File.createTempFile(fileName, ".jpg", storageDir)
        photoPath = image.absolutePath

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) == null) {
            throw RuntimeException("NO camera found")
        }
        val photoUri = FileProvider.getUriForFile(activity, "com.example.android.fileprovider", image)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        activity.startActivityForResult(intent, requestCode)
    }


    }


}
