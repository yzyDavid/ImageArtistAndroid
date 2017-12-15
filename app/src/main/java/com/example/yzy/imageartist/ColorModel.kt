package com.example.yzy.imageartist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.view.View
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.io.File
import java.io.FileOutputStream
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.android.Utils
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class ColorModel(private val activity: ColorActivity) {
    interface ColorService {
        @GET("hello")
        fun getTest(@Header("authorization") credential: String): Call<ResponseBody>

        @POST("theme_color_count")
        fun getThemeColor(@Header("authorization") credential: String, @Body body: RequestBody): Call<ResponseBody>
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .build()
    private val service = retrofit.create(ColorService::class.java)
    private val credential = "Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP)

    fun getHello() {
        val callHello = service.getTest(credential)
        callHello.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                response!!.body()!!.string()
                activity.mProgressBar.visibility = View.INVISIBLE
                // TODO: activity stop the progress bar and show the text
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                RuntimeException(t!!.message)
            }
        })
        // TODO: activity create a progress bar to wait network response
        activity.mProgressBar.visibility = View.VISIBLE
    }

    fun getThemeColor(image: Bitmap, count: Int) {
        val imageFile = toImageFile(image)
        val size = imageFile.length()
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.name, RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                .addFormDataPart("count", count.toString())
                .build()
        val callThemeColor = service.getThemeColor(credential, requestBody)
        callThemeColor.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val themeColorImage = response!!.body()!!
                val bytes = themeColorImage.bytes()
                WorkspaceManager.bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                activity.mProgressBar.visibility = View.VISIBLE
                activity.mPhoto.setImageBitmap(WorkspaceManager.bitmap)
                // TODO: activity stop the progress bar and show the image

            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                RuntimeException(t!!.message)
            }
        })
        // TODO: activity create a progress bar to wait network response
        activity.mProgressBar.visibility = View.INVISIBLE
    }

    private fun toImageFile(image: Bitmap): File {
        val height = image.height
        val width = image.width
        val filename = "ImageArtist_" + System.currentTimeMillis()
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        var imageFile = File.createTempFile(filename, ".jpg", storageDir)
        var os = FileOutputStream(imageFile)
        image.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        var size = imageFile.length()
        if (size > 1024 * 1024) {
            val ratio = (size / 1000 / 1000).toInt()
            val matImage = Mat(height, width, CvType.CV_8UC4)
            Utils.bitmapToMat(image, matImage)
            Imgproc.resize(matImage, matImage, Size(width.toDouble() / ratio, height.toDouble() / ratio))
            val resizedImage = Bitmap.createBitmap(matImage.cols(), matImage.rows(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(matImage, resizedImage)
            os.close()
            imageFile.delete()
            imageFile = File.createTempFile(filename, ".jpg", storageDir)
            os = FileOutputStream(imageFile)
            resizedImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            size = imageFile.length()
        }
        os.close()
        return imageFile
    }
}
