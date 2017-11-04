package com.example.yzy.imageartist

import android.app.Dialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.NumberPicker.Formatter
import android.widget.NumberPicker.OnValueChangeListener
import java.io.File
import java.io.FileOutputStream
import android.widget.ProgressBar


class Editor : AppCompatActivity () ,Formatter{

    private lateinit var mNumPicker: NumberPicker
    private lateinit var mChooseColorNum: TextView
    private lateinit var inflate: View
    private lateinit var mPhoto: ImageView
    private lateinit var mDialog: Dialog
    private lateinit var mProgressBar: ProgressBar
    private  var mColorNum: Int = 1
    private val stylizeModel = StylizeModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        this.setTitle(R.string.Editor)
        mChooseColorNum = findViewById(R.id.choosenum_text)
        mPhoto = findViewById(R.id.photo)

        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }

    }

    public fun show(view: View) {
        mDialog = Dialog(this, R.style.ActionSheetDialogAnimation)
        inflate = LayoutInflater.from(this).inflate(R.layout.choosecolornum, null)
        mDialog.setContentView(inflate)
        val dialogWindow: Window = mDialog.window
        dialogWindow.setGravity(Gravity.CENTER)
        val lp: WindowManager.LayoutParams = dialogWindow.attributes
        lp.alpha=9f
        inflate.measure(0, 0)
        lp.height = inflate.measuredHeight
        lp.width = inflate.measuredWidth
        dialogWindow.attributes = lp
        mDialog.show()
        mNumPicker = inflate.findViewById(R.id.numberpicker)
        mNumPicker.setFormatter(this)
        mNumPicker.maxValue=5
        mNumPicker.minValue=1
        mColorNum = mNumPicker.value
        mNumPicker.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            mColorNum = newVal
        })
        mNumPicker.setOnClickListener{
            val fileName = "ImageArtist_" + System.currentTimeMillis()
            val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File.createTempFile(fileName, ".jpg", storageDir)
            val os = FileOutputStream(image)
            WorkspaceManager.bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            stylizeModel.getThemeColor(image, mColorNum)
            mDialog.dismiss()
            mDialog = Dialog(this, R.style.ActionSheetDialogAnimation)
            inflate = LayoutInflater.from(this).inflate(R.layout.progressbar, null)
            mDialog.setContentView(inflate)
            val dialogWindow: Window = mDialog.window
            dialogWindow.setGravity(Gravity.CENTER)
            val lp: WindowManager.LayoutParams = dialogWindow.attributes
            lp.alpha=9f
            inflate.measure(0, 0)
            lp.height = inflate.measuredHeight
            lp.width = inflate.measuredWidth
            dialogWindow.attributes = lp
            mDialog.show()
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
