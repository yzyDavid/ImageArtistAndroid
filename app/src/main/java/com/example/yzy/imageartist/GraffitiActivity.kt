package com.example.yzy.imageartist

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.widget.ImageView

import kotlinx.android.synthetic.main.activity_graffiti.*

class GraffitiActivity : AppCompatActivity() {
    companion object {
        const val NOTHING = 0
        const val RED = 1
        const val BLUE = 2
        const val GREEN = 3
        const val GRAY = 4
        const val WHITE = 5
    }
    private var mode = NOTHING
    private lateinit var mPhoto: ImageView
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graffiti)
        this.setTitle(R.string.graffiti)
        mPhoto = findViewById(R.id.graffiti_photo)


    }

}
