package com.example.yzy.imageartist

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Base64
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.view.*
import okhttp3.*
import org.opencv.android.Utils
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.io.File
import java.io.FileOutputStream

class StylizeModel(private val activity: StylizeActivity) {
    interface StylizeService {
        @POST("upload_image")
        fun uploadImage(@Header("authorization") credential: String, @Body body: RequestBody): Call<ResponseBody>

        @POST("upload_style")
        fun uploadStyle(@Header("authorization") credential: String, @Body body: RequestBody): Call<ResponseBody>

        @POST("transfer")
        fun getTransfer(@Header("authorization") credential: String, @Body body: RequestBody): Call<ResponseBody>
    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(Config.baseUrl)
            .client(
                    OkHttpClient.Builder()
                            .readTimeout(Config.timeout, Config.timeoutUnit)
                            .writeTimeout(Config.timeout, Config.timeoutUnit)
                            .connectTimeout(Config.timeout, Config.timeoutUnit)
                            .build()
            ).build()
    private val service = retrofit.create(StylizeService::class.java)
    private val credential = "Basic " + Base64.encodeToString("minami:kotori".toByteArray(), Base64.NO_WRAP)
    private var imageText: String? = null
    private var styleText: String? = null
    private var imagePath: String? = null
    private var stylePath: String? = null

    fun uploadImage(image: Bitmap) {
        val imageFile = toImageFile(image)
        val resizedImage = BitmapFactory.decodeFile(imageFile.path)
        val width = resizedImage.width
        val height = resizedImage.height

        imageText = null
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.name, RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                .addFormDataPart("width", width.toString())
                .addFormDataPart("height", height.toString())
                .build()
        val callUploadImage = service.uploadImage(credential, requestBody)
        callUploadImage.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val file = File(imagePath)
                file.delete()
                imagePath = null
                imageText = response!!.body()!!.string()
                getTransfer()
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                val file = File(imagePath)
                file.delete()
                imagePath = null
                Toast.makeText(activity, t!!.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun uploadStyle(image: Bitmap) {
        val imageFile = toImageFile(image)
        val resizedImage = BitmapFactory.decodeFile(imageFile.path)
        val width = resizedImage.width
        val height = resizedImage.height

        styleText = null
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.name, RequestBody.create(MediaType.parse("image/jpeg"), imageFile))
                .addFormDataPart("width", width.toString())
                .addFormDataPart("height", height.toString())
                .build()
        val callUploadStyle = service.uploadStyle(credential, requestBody)
        callUploadStyle.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val file = File(stylePath)
                file.delete()
                stylePath = null
                styleText = response!!.body()!!.string()
                getTransfer()
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                val file = File(stylePath)
                file.delete()
                stylePath = null
                Toast.makeText(activity, t!!.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getTransfer() {
        if (imageText == null || styleText == null) return
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("img", imageText!!)
                .addFormDataPart("style", styleText!!)
                .build()
        val callGetTransfer = service.getTransfer(credential, requestBody)
        callGetTransfer.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                val bytes = response!!.body()!!.bytes()
                WorkspaceManager.bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                activity.mPhoto.setImageBitmap(WorkspaceManager.bitmap)
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Toast.makeText(activity, t!!.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun toImageFile(image: Bitmap): File {
        val width = image.width
        val height = image.height
        val filename = "ImageArtist_" + System.currentTimeMillis()
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(filename, ".jpg", storageDir)
        val os = FileOutputStream(imageFile)
        val ratio = if (height > width) height.toDouble() / 640 else width.toDouble() / 640
        val matImage = Mat(height, width, CvType.CV_8UC3)
        Utils.bitmapToMat(image, matImage)
        Imgproc.resize(matImage, matImage, Size(width.toDouble() / ratio, height.toDouble() / ratio))
        val resizedImage = Bitmap.createBitmap(matImage.cols(), matImage.rows(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(matImage, resizedImage)
        resizedImage.compress(Bitmap.CompressFormat.JPEG, 100, os)
        os.flush()
        os.close()
        return imageFile
    }
}