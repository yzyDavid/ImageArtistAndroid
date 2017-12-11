package com.example.yzy.imageartist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.ImageView
import org.jetbrains.anko.toast
import org.opencv.core.Mat

class Modify : AppCompatActivity() {
    private val NOTHING = 0
    private val CONTRAST = 1
    private val LIGHT = 2
    private var mode = NOTHING

    lateinit var mPhoto: ImageView
    private lateinit var gestureDetector: GestureDetector
    private var lastE1: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        this.setTitle(R.string.modify)
        mPhoto = findViewById(R.id.modify_photo)
        WorkspaceManager.bitmap?.let {
            mPhoto.setImageBitmap(it)
        }
        gestureDetector = GestureDetector(this, object : GestureDetector.OnGestureListener {
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return false
            }

            override fun onDown(e: MotionEvent?): Boolean {
                return false
            }

            override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
                return false
            }

            override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
                if (e1?.downTime == lastE1) {
                    return true
                }
                lastE1 = e1?.downTime ?: 0
                if (distanceX < 0) {
                    when (mode) {
                        CONTRAST -> contrastIncrease()
                        LIGHT ->lightIncrease()
                    }
                } else {
                    when (mode) {
                        CONTRAST -> contrastDecrease()
                        LIGHT ->lightDecrease()
                    }
                }
                return true
            }

            override fun onLongPress(e: MotionEvent?) {
            }

            override fun onShowPress(e: MotionEvent?) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_modify, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        when (item?.itemId) {
            R.id.menu_change_contrast -> setContrastMode()
            R.id.menu_change_light -> setLightMode()
        }
        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event)
    }

    private fun contrastIncrease() {
        WorkspaceManager.bitmap?.let {
            val mat = it.toMat()
            val target = Mat.zeros(mat.size(), mat.type())
            mat.convertTo(target, mat.type(), Config.contrastAlphaIncrease, 0.toDouble())
            val result = target.toBitmap()
            WorkspaceManager.bitmap = result
            mPhoto.setImageBitmap(result)
        }
    }

    private fun contrastDecrease() {
        WorkspaceManager.bitmap?.let {
            val mat = it.toMat()
            val target = Mat.zeros(mat.size(), mat.type())
            mat.convertTo(target, mat.type(), Config.contrastAlphaDecrease, 0.toDouble())
            val result = target.toBitmap()
            WorkspaceManager.bitmap = result
            mPhoto.setImageBitmap(result)
        }
    }

    private fun lightIncrease() {
        WorkspaceManager.bitmap?.let {
            val mat = it.toMat()
            val target = Mat.zeros(mat.size(), mat.type())
            mat.convertTo(target, mat.type(), 1.toDouble(), Config.lightBetaIncrease)
            val result = target.toBitmap()
            WorkspaceManager.bitmap = result
            mPhoto.setImageBitmap(result)
        }
    }

    private fun lightDecrease() {
        WorkspaceManager.bitmap?.let {
            val mat = it.toMat()
            val target = Mat.zeros(mat.size(), mat.type())
            mat.convertTo(target, mat.type(), 1.toDouble(), Config.lightBetaDecrease)
            val result = target.toBitmap()
            WorkspaceManager.bitmap = result
            mPhoto.setImageBitmap(result)
        }
    }

    private fun setContrastMode() {
        mode = CONTRAST
        toast(R.string.slide_hint)
    }

    private fun setLightMode() {
        mode = LIGHT
        toast(R.string.slide_hint)
    }

}
