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
import android.support.design.widget.FloatingActionButton
import android.view.*
import android.view.Gravity.*
import android.widget.Button
import android.widget.TextView
import java.util.concurrent.TimeoutException
import android.provider.MediaStore
import org.jetbrains.anko.toast
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
   /* private lateinit var mButtonStylize: Button
    private lateinit var mButtonJoint: Button
    private lateinit var mButtonSticker: Button
    private lateinit var mButtonFrame: Button
    private lateinit var mButtonSettings: FloatingActionButton*/
    private val CAMERA: Int = 1
    private val PICTURE: Int = 0
    private lateinit var mTextCamera: TextView
    private lateinit var mTextAlbum: TextView
    private lateinit var inflate: View
    private lateinit var mButtonChoosePhoto: Button
    private lateinit var mButtonTakePhoto: Button
    private lateinit var mButtonCancle: Button
    private lateinit var mDialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle(R.string.app_name)

      /*  mButtonStylize = findViewById(R.id.stylize_button)
        mButtonStylize.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Stylize::class.java)
            startActivity(intent)
        }

        mButtonJoint = findViewById(R.id.joint_button)
        mButtonJoint.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Joint::class.java)
            startActivity(intent)
        }

        mButtonSticker = findViewById(R.id.sticker_button)
        mButtonSticker.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Sticker::class.java)
            startActivity(intent)
        }

        mButtonFrame = findViewById(R.id.frame_button)
        mButtonFrame.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Frame::class.java)
            startActivity(intent)
        }
        mButtonSettings = findViewById(R.id.setting_button)
        mButtonSettings.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, Settings::class.java)
            startActivity(intent)
        }*/
    }
    public fun show(view: View){
        mDialog = Dialog(this, R.style.ActionSheetDialogAnimation)
        inflate = LayoutInflater.from(this).inflate(R.layout.initdialog, null)
        mButtonChoosePhoto = inflate.findViewById(R.id.choosePhoto_button)
        mButtonTakePhoto = inflate.findViewById(R.id.takePhoto_button)
        mButtonCancle = inflate.findViewById(R.id.cancle_button)
        mDialog.setContentView(inflate)
        val dialogWindow :Window = mDialog.window
        dialogWindow.setGravity(Gravity.BOTTOM)
        val lp :WindowManager.LayoutParams = dialogWindow.attributes
        lp.y = -20
        lp.x = 0
        inflate.measure(0,0)
        lp.height = inflate.measuredHeight
        lp.alpha = 9f
        dialogWindow.setAttributes(lp)
        mDialog.show()
        mButtonChoosePhoto.setOnClickListener {
            val album = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(album, PICTURE)
        }
        mButtonTakePhoto.setOnClickListener {
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, CAMERA)
        }
        mButtonCancle.setOnClickListener {
            if (mDialog!=null)
                mDialog.dismiss()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage: Uri = data.data
            val filePathColumns = arrayOf(MediaStore.Images.Media.DATA)
            val c: Cursor = this.contentResolver.query(selectedImage, filePathColumns, null, null, null)
            c.moveToFirst()
            val columnIndex: Int = c.getColumnIndex(filePathColumns[0])
            var picturePath: String = c.getString(columnIndex)
            c.close()
        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && null != data) {
            val sdState: String = Environment.getExternalStorageState()
            if (sdState != Environment.MEDIA_MOUNTED) {
                this.toast("SD card unmount")
                return
            }
            val dateFormat: DateFormat = SimpleDateFormat("yyyyMMdd_hhmmss")
            val name: String = dateFormat.format(Calendar.getInstance(Locale.CHINA)) + "jpg"
            val bundle: Bundle = data.extras
            val bitmap: Bitmap = bundle.get("data") as Bitmap
            val file: File = File("/sdcard/pintu/")
            file.mkdirs()
            val filename: String = file.getPath() + name
            var fout: FileOutputStream = FileOutputStream(filename)
            try {
                fout = FileOutputStream(filename)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fout.flush()
                fout.close()
            }
        }
    }
}
