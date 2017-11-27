package com.example.yzy.imageartist

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.provider.MediaStore

import android.widget.ImageView

import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

import org.jetbrains.anko.toast
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@RuntimePermissions
class MainActivity : AppCompatActivity() {
    private val CAMERA: Int = 1
    private val PICTURE: Int = 0

    private val STORAGE: Int = 2
    private val CAMERA_INTENT: Int = 3

    private lateinit var mTextCamera: TextView
    private lateinit var mTextAlbum: TextView
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
        lp.y = -20
        lp.x = 0
        inflate.measure(0, 0)
        lp.height = inflate.measuredHeight
        lp.alpha = 9f
        dialogWindow.attributes = lp
        mDialog.show()
        mButtonChoosePhoto.setOnClickListener {
            //selectPhoto(PICTURE)
            selectPhotoWithPermissionCheck(PICTURE)
        }
        mButtonTakePhoto.setOnClickListener {
            //selectPhoto(CAMERA)
            selectPhotoWithPermissionCheck(CAMERA)
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

        var intent = Intent(this, Editor::class.java)
        startActivity(intent)

    }
}

