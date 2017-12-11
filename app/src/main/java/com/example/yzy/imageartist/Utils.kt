package com.example.yzy.imageartist

import android.graphics.Bitmap
import com.example.yzy.imageartist.Utils.fromBitmapToMat
import com.example.yzy.imageartist.Utils.fromMatToBitmap
import org.opencv.android.Utils as CvUtils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Scalar

/**
 * Created by yzy on 12/11/17.
 */
object Utils {
    public fun fromBitmapToMat(image: Bitmap): Mat {
        val mat = Mat(image.height, image.width, CvType.CV_8U, Scalar(4.toDouble()))
        val tmpBitmap = image.copy(Bitmap.Config.ARGB_8888, true)
        CvUtils.bitmapToMat(tmpBitmap, mat)
        return mat
    }

    public fun fromMatToBitmap(mat: Mat): Bitmap {
        val result = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888)
        CvUtils.matToBitmap(mat, result)
        return result
    }

    public fun Bitmap.toMat(): Mat {
        return fromBitmapToMat(this)
    }

    public fun Mat.toBitmap(): Bitmap {
        return fromMatToBitmap(this)
    }
}

