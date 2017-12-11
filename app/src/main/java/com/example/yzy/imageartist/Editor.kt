package com.example.yzy.imageartist

import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.*
import android.widget.*
import android.widget.NumberPicker.Formatter
import java.io.File
import java.io.FileOutputStream
import com.example.yzy.imageartist.WorkspaceManager.bitmap
import java.io.FileNotFoundException
import java.io.IOException


class Editor : AppCompatActivity(), Formatter {

    private lateinit var mStylizeText: TextView
    private lateinit var mExportText: TextView
    private lateinit var inflate: View
    private lateinit var mPhoto: ImageView
    private lateinit var mDialog: Dialog
    private lateinit var mToolText: TextView
    private lateinit var mColorText: TextView
    private lateinit var mFrameText: TextView
    private lateinit var mModifyText: TextView
    private lateinit var mShareText: TextView
    private lateinit var mSaveText: TextView
    private var photoPath: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        this.setTitle(R.string.Editor)
        mPhoto = findViewById(R.id.photo)
        mStylizeText = findViewById(R.id.stylize_text)
        mToolText = findViewById(R.id.tool_text)
        mExportText = findViewById(R.id.export_text)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
        mStylizeText.setOnClickListener {
            val intent = Intent(this, Stylize::class.java)
            startActivity(intent)
        }
        mToolText.setOnClickListener { show(it) }
        mExportText.setOnClickListener { export(it) }
    }

    private fun export(view: View) {
        mDialog = Dialog(this, R.style.ActionSheetDialogAnimation)
        inflate = LayoutInflater.from(this).inflate(R.layout.layout_export, null)
        mShareText = inflate.findViewById(R.id.share_text)
        mSaveText = inflate.findViewById(R.id.save_text)
        mDialog.setContentView(inflate)
        val dialogWindow: Window = mDialog.window
        dialogWindow.setGravity(Gravity.BOTTOM)
        val lp: WindowManager.LayoutParams = dialogWindow.attributes
        lp.alpha = 9f
        inflate.measure(0, 0)
        lp.height = 600
        dialogWindow.attributes = lp
        mDialog.show()
        mShareText.setOnClickListener {
            val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val fileName = "ImageArtist_" + System.currentTimeMillis()
            val image = File.createTempFile(fileName, ".jpg", storageDir)
            photoPath = image.absolutePath
            Log.i("saver", photoPath)
            val fos = FileOutputStream(image)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            fos.close()

            val photoUri = FileProvider.getUriForFile(this, "com.example.yzy.imageartist", image)

            Intent(Intent.ACTION_SEND).let {
                it.type = "image/jpeg"
                it.putExtra(Intent.EXTRA_STREAM, photoUri)
                startActivity(Intent.createChooser(it, "分享"))
            }
            mDialog.dismiss()
        }
        mSaveText.setOnClickListener {
            val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val fileName = "ImageArtist_" + System.currentTimeMillis()
            val image = File.createTempFile(fileName, ".jpg", storageDir)
            photoPath = image.absolutePath
            Log.i("saver", photoPath)
            val fos = FileOutputStream(image)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 90, fos)
            fos.flush()
            fos.close()
            Toast.makeText(this, R.string.success_saved, Toast.LENGTH_SHORT).show()
            mDialog.dismiss()
        }
    }

    // do not modify the name below!
    private fun show(view: View) {
        mDialog = Dialog(this, R.style.ActionSheetDialogAnimation)
        inflate = LayoutInflater.from(this).inflate(R.layout.tool_selection, null)
        mColorText = inflate.findViewById(R.id.color_text)
        mFrameText = inflate.findViewById(R.id.frame_text)
        mModifyText = inflate.findViewById(R.id.modify_text)
        mDialog.setContentView(inflate)
        val dialogWindow: Window = mDialog.window
        dialogWindow.setGravity(Gravity.CENTER)
        val lp: WindowManager.LayoutParams = dialogWindow.attributes
        lp.alpha = 9f
        inflate.measure(0, 0)
        lp.height = inflate.measuredHeight
        lp.width = inflate.measuredWidth
        dialogWindow.attributes = lp
        mDialog.show()
        mColorText.setOnClickListener {
            val intent = Intent(this, Color::class.java)
            startActivity(intent)
            mDialog.dismiss()
        }
        mFrameText.setOnClickListener {
            val intent = Intent(this, Frame::class.java)
            startActivity(intent)
            mDialog.dismiss()
        }
        mModifyText.setOnClickListener {
            val intent = Intent(this, Modify::class.java)
            startActivity(intent)
            mDialog.dismiss()
        }
    }

    override fun format(value: Int): String {
        var tmpStr = value.toString()
        if (value < 10) {
            tmpStr = tmpStr
        }
        return tmpStr
    }
}