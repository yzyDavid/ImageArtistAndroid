package com.example.yzy.imageartist

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

/**
 * Created by poiii on 2017/12/23.
 */
class GraffitiView(private val activity: GraffitiActivity, private val mBitmap: Bitmap) : View(activity) {
    companion object {
        const val NOTHING = 0
        const val RED = Color.RED
        const val BLUE = Color.BLUE
        const val GREEN = Color.GREEN
        const val GRAY = Color.GRAY
        const val WHITE = Color.WHITE
    }

    private var mCanvas = Canvas(mBitmap)
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)
    private var mPaint: Paint = Paint()
    private var mPath: Path? = null
    private var mX = 0.0f
    private var mY = 0.0f
    private final val TOUCH_TOLERANCE = 4

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.isAntiAlias = true
        mPaint.isDither = true
    }

    override fun onDraw(canvas: Canvas?) {
        // TODO: should modify left and top
        canvas!!.drawBitmap(mBitmap, 0.0f, 0.0f, mBitmapPaint)
        if (mPath != null) {
            canvas.drawPath(mPath, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchDown(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                performClick()
                touchUp()
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(x, y)
                invalidate()
            }
        }
        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return false
    }

    private fun touchDown(x: Float, y: Float) {
        mPath = Path()
        mPath!!.moveTo(x, y)
        mX = x
        mY = y
    }

    private fun touchUp() {
        mPath!!.lineTo(mX, mY)
        mCanvas.drawPath(mPath, mPaint)
        mPath = null
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE && dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    fun setPaintSize(width: Int) {
        mPaint.strokeWidth = width.toFloat()
    }

    fun setPaintColor(color: Int) {
        mPaint.color = color
    }
}