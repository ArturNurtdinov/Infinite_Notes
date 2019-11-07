package com.infinitevoid.painter_feature

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View


class DrawView(context: Context) : View(context) {
    private val paint = Paint()
    private val path = Path()

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint.strokeWidth = 5.toFloat()
        paint.style = Paint.Style.STROKE;
        paint.strokeJoin = Paint.Join.ROUND;
        paint.strokeCap = Paint.Cap.ROUND;
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN ->
                // Starts a new line in the path
                path.moveTo(event.x, event.y)
            MotionEvent.ACTION_MOVE ->
                // Draws line between last point and this point
                path.lineTo(event.x, event.y)
            else -> return false
        }
        postInvalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(50.toFloat(), 50.toFloat(), 20.toFloat(), paint)
        paint.color = Color.GREEN
        canvas?.drawCircle(50.toFloat(), 150.toFloat(), 20.toFloat(), paint)
        paint.color = Color.BLUE
        canvas?.drawCircle(50.toFloat(), 250.toFloat(), 20.toFloat(), paint)
        canvas?.drawPath(path, paint)
    }
}