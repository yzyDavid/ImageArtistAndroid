package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView

class Editor : AppCompatActivity() {

    private lateinit var mNumPicker: NumberPicker
    private lateinit var mChooseColorNum: TextView
    private lateinit var mPicker: ImageView
    private lateinit var mPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        this.setTitle(R.string.Editor)

        mPhoto = findViewById(R.id.photo)

        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }

    }

    public fun show(view: View) {

    }
}
