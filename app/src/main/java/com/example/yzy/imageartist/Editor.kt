package com.example.yzy.imageartist

import android.app.Dialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView

class Editor : AppCompatActivity() {

    private lateinit var mNumPicker: NumberPicker
    private lateinit var mChooseColorNum: TextView
    private lateinit var inflate: View
    private lateinit var mPhoto: ImageView
    private lateinit var mDialog: Dialog

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
    }
}
