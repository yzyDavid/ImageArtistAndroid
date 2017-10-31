package com.example.yzy.imageartist

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class CameraModel {
    fun bitmap(resultCode: Int, data: Intent): Bitmap {
        if (resultCode != Activity.RESULT_OK) {
            throw RuntimeException("Not a valid result")
        }
        val bitmap = BitmapFactory.decodeFile("") // FIXME
        return bitmap
    }
}