package com.infinitevoid.painter_feature

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class CanvasView(internal var context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mbitmap: Bitmap? = null
    private var mCanvas: Canvas? = null
    private var mPath = Path()
    private var mPaint = Paint()
    private var mx = 0.toFloat()
    private var my = 0.toFloat()

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4f
    }

    companion object {
        private const val TOLERANCE = 5f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawPath(mPath, mPaint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mbitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mbitmap!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y
        when (event.action){
            MotionEvent.ACTION_DOWN -> {
                onStartTouchEvent(x, y)
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                onMoveTouchEvent(x, y)
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                upTouchEvent()
                invalidate()
            }
        }

        return true
    }

    private fun onStartTouchEvent(x: Float, y: Float) {
        mPath.moveTo(x, y)
        mx = x
        my = y
    }

    private fun onMoveTouchEvent(x: Float, y: Float) {
        val dx = abs(x - mx)
        val dy = abs(y - my)

        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mx, my, (x + mx) / 2, (y + my) / 2)
            mx = x
            my = y
        }
    }

    private fun upTouchEvent(){
        mPath.reset()
        invalidate()
    }
}