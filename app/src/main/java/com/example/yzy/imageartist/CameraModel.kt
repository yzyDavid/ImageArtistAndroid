package com.example.yzy.imageartist

import android.Manifest
import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import android.support.v4.content.FileProvider

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
        val fileName = "ImageArtist_" + System.currentTimeMillis()
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                throw RuntimeException("User denies the permission before")
            } else {
                ActivityCompat.requestPermissions(activity, Array(1){Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode)
            }
        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                throw RuntimeException("User denies the permission before")
            } else {
                ActivityCompat.requestPermissions(activity, Array(1){Manifest.permission.CAMERA}, requestCode)
            }
        }

        val image = File.createTempFile(fileName, ".jpg", storageDir)
        photoPath = image.absolutePath

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity.packageManager) == null) {
            throw RuntimeException("NO camera found")
        }
        val photoUri = FileProvider.getUriForFile(activity, "com.example.yzy.imageartist", image)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        activity.startActivityForResult(intent, requestCode)
    }
}