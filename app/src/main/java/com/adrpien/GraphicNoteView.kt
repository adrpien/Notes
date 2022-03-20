package com.adrpien

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

// Use attrs if you want to pass view attributes via XML file
class GraphicNoteView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

    val dotArray: ArrayList<Dot> = arrayListOf()
    private lateinit var dot: Dot

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {

            // Saving coordinates of touch event
            val touchPointF = PointF(event?.x, event?.y)

            // Setting size of dot
            val size = 10.00F

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Adding touch event coordinates to array
                    dot = Dot(touchPointF, size)
                    dotArray.add(dot)
                    // Forces redrawing of view
                    invalidate()
                }
                MotionEvent.ACTION_MOVE -> {
                    dot = Dot(touchPointF, size)
                    dotArray.add(dot)
                    invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    dot = Dot(touchPointF, size)
                    dotArray.add(dot)
                    invalidate()
                }
            }


        }
    return true
    }

    // Override onDraw
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.GRAY
        for (dot in dotArray){
            canvas?.drawCircle(dot.point.x, dot.point.y, 2.00F, paint)
        }
    }
}