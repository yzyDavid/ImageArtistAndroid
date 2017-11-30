package com.example.yzy.imageartist

import android.app.Dialog
import android.content.Intent
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


class Editor : AppCompatActivity(), Formatter {

    private lateinit var mNumPicker: NumberPicker
    private lateinit var mChooseColorNum: TextView
    private lateinit var mStylizeText: TextView
    private lateinit var mImportText: TextView
    private lateinit var inflate: View
    public lateinit var mPhoto: ImageView
    private lateinit var mDialog: Dialog
    private lateinit var mToolText: TextView
    private lateinit var mColorText: TextView
    private lateinit var mFrameText: TextView
    private lateinit var mModifyText: TextView
    private lateinit var mProgressBar: ProgressBar
    private var mColorNum: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        this.setTitle(R.string.Editor)
        mPhoto = findViewById(R.id.photo)
        mStylizeText = findViewById(R.id.stylize_text)
        mToolText = findViewById(R.id.tool_text)
        mImportText = findViewById(R.id.import_text)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
        mStylizeText.setOnClickListener {
            val intent = Intent(this, Stylize::class.java)
            startActivity(intent)
        }
        mImportText.setOnClickListener {
            val intent = Intent(this, Import::class.java)
            startActivity(intent)
        }

    }

    public fun show(view: View) {
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
