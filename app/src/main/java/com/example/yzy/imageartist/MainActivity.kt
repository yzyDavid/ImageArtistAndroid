package com.example.yzy.imageartist

import android.app.Activity
import android.app.Dialog
import android.content.Intent
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
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val CAMERA: Int = 1
    private val PICTURE: Int = 0

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
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle(R.string.app_name)
        mImage=findViewById(R.id.imageView)

    }

    public fun show(view: View) {
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

            galleryModel.startGallery()
        }
        mButtonTakePhoto.setOnClickListener {
            cameraModel.startCamera()
        }
        mButtonCancel.setOnClickListener {
            mDialog.dismiss()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        data?.let {
            when (requestCode) {
                PICTURE -> bitmap = galleryModel.getBitmap(resultCode, it)
                CAMERA -> bitmap = cameraModel.getBitmap(resultCode, it)
            }

        }
        mImage.setImageBitmap(bitmap)
    }
}

