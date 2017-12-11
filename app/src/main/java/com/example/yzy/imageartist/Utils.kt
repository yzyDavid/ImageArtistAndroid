package com.example.yzy.imageartist

import android.graphics.Bitmap
import org.opencv.android.Utils as CvUtils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar

/**
 * Created by yzy on 12/11/17.
 */
object Utils {
    public fun toMat(image: Bitmap): Mat {
        val mat = Mat(image.height, image.width, CvType.CV_8U, Scalar(4.toDouble()))
        val tmpBitmap = image.copy(Bitmap.Config.ARGB_8888, true)
        CvUtils.bitmapToMat(tmpBitmap, mat)
        return mat
    }
}