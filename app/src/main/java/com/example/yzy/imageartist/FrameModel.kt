package com.example.yzy.imageartist

import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class FrameModel(private val activiy: FrameActivity) {
    fun appendFrame(image: Bitmap, frame: Bitmap) {
        val matImage = Mat(image.height, image.width, CvType.CV_8UC4)
        Utils.bitmapToMat(image, matImage)
        val matFrame = Mat(frame.height, frame.width, CvType.CV_8UC4)
        Utils.bitmapToMat(frame, matFrame)
        Imgproc.resize(matFrame, matFrame, Size(image.width.toDouble(), image.height.toDouble()))

    }
}
