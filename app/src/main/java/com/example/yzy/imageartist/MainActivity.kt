package com.example.yzy.imageartist

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button

import android.widget.ImageView

import android.util.Log

import org.opencv.android.BaseLoaderCallback
import org.opencv.android.LoaderCallbackInterface
import org.opencv.android.OpenCVLoader
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MainActivity : AppCompatActivity() {
    companion object {
        const val CAMERA: Int = 1
        const val PICTURE: Int = 0
    }

    private lateinit var inflate: View
    private lateinit var mButtonChoosePhoto: Button
    private lateinit var mButtonTakePhoto: Button
    private lateinit var mButtonCancel: Button
    private lateinit var mDialog: Dialog
    private lateinit var mImage: ImageView
    private val galleryModel = GalleryModel(this, PICTURE)
    private val cameraModel = CameraModel(this, CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle(R.string.app_name)
        mImage = findViewById(R.id.imageView)
    }

    override fun onResume() {
        super.onResume()

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, loaderCallback)
    }

    inner class LoaderCallback : BaseLoaderCallback(this) {
        override fun onManagerConnected(status: Int) {
            Log.i("OpenCV", if (status == BaseLoaderCallback.SUCCESS) "Success" else "Failed")
            if (status != LoaderCallbackInterface.SUCCESS) {
                super.onManagerConnected(status)
            }
        }
    }

    private val loaderCallback = LoaderCallback()

    fun show(view: View) {
        mDialog = Dialog(this, R.style.ActionSheetDialogAnimation)
        inflate = LayoutInflater.from(this).inflate(R.layout.initdialog, null)
        mButtonChoosePhoto = inflate.findViewById(R.id.choosePhoto_button)
        mButtonTakePhoto = inflate.findViewById(R.id.takePhoto_button)
        mButtonCancel = inflate.findViewById(R.id.cancle_button)
        mDialog.setContentView(inflate)
        val dialogWindow: Window = mDialog.window
        dialogWindow.setGravity(Gravity.BOTTOM)
        val lp: WindowManager.LayoutParams = dialogWindow.attributes
        inflate.measure(0, 0)
        lp.height = 650
        lp.alpha = 9f
        dialogWindow.attributes = lp
        mDialog.show()
        mButtonChoosePhoto.setOnClickListener {
            selectPhotoWithPermissionCheck(PICTURE)
            mDialog.dismiss()
        }
        mButtonTakePhoto.setOnClickListener {
            selectPhotoWithPermissionCheck(CAMERA)
            mDialog.dismiss()
        }
        mButtonCancel.setOnClickListener {
            mDialog.dismiss()
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun selectPhoto(requestCode: Int) {
        when (requestCode) {
            PICTURE -> galleryModel.startGallery()
            CAMERA -> cameraModel.startCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            PICTURE -> WorkspaceManager.bitmap = galleryModel.getBitmap(resultCode, data!!)
            CAMERA -> WorkspaceManager.bitmap = cameraModel.getBitmap(resultCode)
        }

        val intent = Intent(this, EditorActivity::class.java)
        startActivity(intent)

    }
}

