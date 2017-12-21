package com.example.yzy.imageartist

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast


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
    private lateinit var mLayout: RelativeLayout
    private var mLayoutWidth = 0.0
    private var mLayoutHeight = 0.0
    private var picWidth = 0.0
    private var picHeight = 0.0
    private var mLayoutPos = IntArray(2)
    private var ratio = 0.0
    private var imgWidth = 0.0
    private var imgHeight = 0.0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graffiti)
        this.setTitle(R.string.graffiti)
        mPhoto = findViewById(R.id.graffiti_photo)
        mLayout = findViewById(R.id.graffiti_photo_area)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }

        /*
        mLayout.setOnTouchListener { view, motion ->
            view.performClick()
            Log.e("hahaha", view.toString())
            return@setOnTouchListener true
        }
        */

        mLayout.setOnTouchListener { view, motion ->
            when (motion.action) {
                MotionEvent.ACTION_MOVE -> drawGraffiti(view, motion)
            }
            return@setOnTouchListener true
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //Log.e("test", event!!.getX(0).toString())
        Log.e("width", mPhoto.width.toString())
        Log.e("me_width", mPhoto.measuredWidth.toString())
        Log.e("height", mPhoto.height.toString())
        Log.e("me_height", mPhoto.measuredHeight.toString())
        Log.e("pic_width", mPhoto.drawable.intrinsicWidth.toString())
        Log.e("pic_height", mPhoto.drawable.intrinsicHeight.toString())
        Log.e("x", event!!.x.toString())
        Log.e("y", event.y.toString())
        val pos = IntArray(2)
        mPhoto.getLocationOnScreen(pos)
        Log.e("pic_x", pos[0].toString())
        Log.e("pic_y", pos[1].toString())
        return super.onTouchEvent(event)
    }

    private fun drawGraffiti(view: View, motion: MotionEvent) {
        if (mLayoutWidth == 0.0) {
            mLayoutWidth = view.width.toDouble()
            mLayoutHeight = view.height.toDouble()
            picWidth = WorkspaceManager.bitmap!!.width.toDouble()
            picHeight = WorkspaceManager.bitmap!!.height.toDouble()
            view.getLocationOnScreen(mLayoutPos)
            if (picWidth / picHeight > mLayoutWidth / mLayoutHeight) {
                ratio = picWidth / mLayoutWidth
                imgWidth = mLayoutWidth
                imgHeight = picHeight / ratio
            } else {
                ratio = picHeight / mLayoutHeight
                imgWidth = picWidth / ratio
                imgHeight = mLayoutHeight
            }
        }
        val x = motion.x
        val y = motion.y
    }
}
