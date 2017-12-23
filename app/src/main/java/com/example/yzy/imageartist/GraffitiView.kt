package com.example.yzy.imageartist

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import com.example.yzy.imageartist.Utils.toMat
import com.example.yzy.imageartist.Utils.toBitmap
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

/**
 * Created by poiii on 2017/12/23.
 */
class GraffitiView(private val activity: GraffitiActivity, private var mBitmap: Bitmap) : View(activity) {
    companion object {
        const val NOTHING = 0
        const val RED = Color.RED
        const val BLUE = Color.BLUE
        const val GREEN = Color.GREEN
        const val GRAY = Color.GRAY
        const val WHITE = Color.WHITE
    }

    private lateinit var mCanvas: Canvas
    //private lateinit var mCanvas: Canvas
    private val mBitmapPaint = Paint(Paint.DITHER_FLAG)
    private var mPaint: Paint = Paint()
    private var mPath: Path? = null
    private var mBitmapPath: Path? = null
    private var mX = 0.0f
    private var mY = 0.0f
    private final val TOUCH_TOLERANCE = 4
    private var isResized = false
    private var left = 0.0f
    private var right = 0.0f
    private var top = 0.0f
    private var bottom = 0.0f

    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.isAntiAlias = true
        mPaint.isDither = true
    }

    private fun Bitmap.resize(): Bitmap {
        val imageWidth = width.toDouble()
        val imageHeight = height.toDouble()
        val canvasWidth = this@GraffitiView.measuredWidth.toDouble()
        val canvasHeight = this@GraffitiView.measuredHeight.toDouble()
        val mat = this.toMat()
        if (imageWidth / imageHeight > canvasWidth / canvasHeight) {
            Imgproc.resize(mat, mat, Size(canvasWidth, canvasWidth / imageWidth * imageHeight))
        } else {
            Imgproc.resize(mat, mat, Size(canvasHeight / imageHeight * imageWidth, canvasHeight))
        }
        return mat.toBitmap()
    }

    override fun onDraw(canvas: Canvas?) {
        // TODO: should modify left and top

        if (!isResized) {
            mBitmap = mBitmap.resize()
            left = (width - mBitmap.width) / 2.0f
            right = mBitmap.width + left
            top = (height - mBitmap.height) / 2.0f
            bottom = mBitmap.height + top
            mCanvas = Canvas(mBitmap)
            WorkspaceManager.bitmap = mBitmap
            isResized = true
        }

        canvas!!.drawBitmap(mBitmap, left, top, mBitmapPaint)
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
        mBitmapPath = Path()
        mPath!!.moveTo(x, y)
        mBitmapPath!!.moveTo(x - left, y - top)
        mX = x
        mY = y
    }

    private fun touchUp() {
        mPath!!.lineTo(mX, mY)
        mBitmapPath!!.lineTo(mX - left, mY - top)
        mCanvas.drawPath(mBitmapPath, mPaint)
        mPath = null
        mBitmapPath = null
        mX = 0.0f
        mY = 0.0f
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= TOUCH_TOLERANCE && dy >= TOUCH_TOLERANCE) {
            mPath!!.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mBitmapPath!!.quadTo(mX - left, mY - top, (x + mX) / 2 - left, (y + mY) / 2 - top)
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