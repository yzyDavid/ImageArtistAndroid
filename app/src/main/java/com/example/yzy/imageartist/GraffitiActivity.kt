package com.example.yzy.imageartist

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import org.jetbrains.anko.find


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
    private lateinit var mButtonRed: ImageView
    private lateinit var mButtonBlue: ImageView
    private lateinit var mButtonGreen: ImageView
    private lateinit var mButtonGray: ImageView
    private lateinit var mButtonWhite: ImageView
    private val image = Bitmap.createBitmap(WorkspaceManager.bitmap!!.width, WorkspaceManager.bitmap!!.height, Bitmap.Config.ARGB_8888)
    private var mLayoutWidth = 0.0
    private var mLayoutHeight = 0.0
    private var picWidth = 0.0
    private var picHeight = 0.0
    private var mLayoutPos = IntArray(2)
    private var ratio = 0.0
    private var imgWidth = 0.0
    private var imgHeight = 0.0
    private var circle = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graffiti)
        this.setTitle(R.string.graffiti)
        mPhoto = findViewById(R.id.graffiti_photo)
        mLayout = findViewById(R.id.graffiti_photo_area)
        mButtonRed = findViewById(R.id.color_red)
        mButtonBlue = findViewById(R.id.color_blue)
        mButtonGreen = findViewById(R.id.color_green)
        mButtonGray = findViewById(R.id.color_gray)
        mButtonWhite = findViewById(R.id.color_white)
        mButtonRed.setOnClickListener { mode = RED }
        mButtonBlue.setOnClickListener { mode = BLUE }
        mButtonGreen.setOnClickListener { mode = GREEN }
        mButtonGray.setOnClickListener { mode = GRAY }
        mButtonWhite.setOnClickListener { mode = WHITE }
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
        for (i in 0 until WorkspaceManager.bitmap!!.width)
            for (j in 0 until WorkspaceManager.bitmap!!.height) {
                image.setPixel(i, j, WorkspaceManager.bitmap!!.getPixel(i, j))
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
                MotionEvent.ACTION_DOWN -> return@setOnTouchListener true
                MotionEvent.ACTION_MOVE -> {
                    drawGraffiti(view, motion)
                    return@setOnTouchListener true
                }
                else -> return@setOnTouchListener false
            }
            return@setOnTouchListener false
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
            circle = (circle * ratio).toInt()
        }
        if (mode == NOTHING) return
        val x = (motion.x - mLayoutPos[0] - (mLayoutWidth - imgWidth) / 2) * ratio
        val y = (motion.y - mLayoutPos[1] - (mLayoutHeight - imgHeight) / 2) * ratio
        var i: Int
        var j: Int
        //val image = Bitmap.createBitmap(WorkspaceManager.bitmap!!.width, WorkspaceManager.bitmap!!.height, Bitmap.Config.ARGB_8888)
        for (i in -circle..circle) {
            (-circle..circle)
                    .filter { x + i in 0.0..picWidth && y + it in 0.0..picHeight && i * i + it * it <= circle * circle }
                    .forEach {
                        image.setPixel((x + i).toInt(), (y + it).toInt(), when (mode) {
                            RED -> Color.RED
                            BLUE -> Color.BLUE
                            GREEN -> Color.GREEN
                            GRAY -> Color.GRAY
                            WHITE -> Color.WHITE
                            else -> WorkspaceManager.bitmap!!.getPixel((x + i).toInt(), (y + it).toInt())
                        })
                    }
        }
        mPhoto.setImageBitmap(image)
    }
}
