package com.example.yzy.imageartist

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RelativeLayout

class GraffitiActivity : AppCompatActivity() {
    companion object {
        const val RED = Color.RED
        const val BLUE = Color.BLUE
        const val GREEN = Color.GREEN
        const val GRAY = Color.GRAY
        const val BLACK = Color.BLACK
    }

    private lateinit var mPhoto: GraffitiView
    private lateinit var mLayout: RelativeLayout
    private lateinit var mButtonRed: ImageView
    private lateinit var mButtonBlue: ImageView
    private lateinit var mButtonGreen: ImageView
    private lateinit var mButtonGray: ImageView
    private lateinit var mButtonBlack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graffiti)
        this.setTitle(R.string.graffiti)
        mLayout = findViewById(R.id.graffiti_photo_area)
        mButtonRed = findViewById(R.id.color_red)
        mButtonBlue = findViewById(R.id.color_blue)
        mButtonGreen = findViewById(R.id.color_green)
        mButtonGray = findViewById(R.id.color_gray)
        mButtonBlack = findViewById(R.id.color_black)
        WorkspaceManager.bitmap?.let {
            mPhoto = GraffitiView(this, it)
            mPhoto.setPaintSize(100)
            mLayout.addView(mPhoto)
        }
        mButtonRed.setOnClickListener { mPhoto.setPaintColor(RED) }
        mButtonBlue.setOnClickListener { mPhoto.setPaintColor(BLUE) }
        mButtonGreen.setOnClickListener { mPhoto.setPaintColor(GREEN) }
        mButtonGray.setOnClickListener { mPhoto.setPaintColor(GRAY) }
        mButtonBlack.setOnClickListener { mPhoto.reset() }
    }

}
