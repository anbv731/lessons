package com.example.lessons

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*

class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var heightScreen = 0f
    var widthScreen = 0f
    var radius = 0f
    var secArrowlenght = 0f
    var minArrowLenght = 0f
    var hourArrowLenght = 0f
    var paint = Paint()
    var isInit = true

    private fun initClock() {
        heightScreen = height.toFloat()
        widthScreen = width.toFloat()
        radius = widthScreen / 4
        secArrowlenght = radius - 20
        minArrowLenght = radius * 3 / 4
        hourArrowLenght = radius / 2
        isInit = false
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isInit) {
            initClock()
        }

        drawCircle(canvas)
        drawHoures(canvas)
        drawArrows(canvas)
        postInvalidateDelayed(1000)
        invalidate()
    }

    private fun drawSecArrow(canvas: Canvas?, loc: Float) {
        val angle: Double = Math.PI * loc / 30 - Math.PI / 2
        paint.color = Color.RED
        paint.strokeWidth = 3f
        canvas?.drawLine(
            (widthScreen / 2 - Math.cos(angle) * secArrowlenght / 10).toFloat(),
            (widthScreen / 2 - Math.sin(angle) * secArrowlenght / 10).toFloat(),
            (widthScreen / 2 + Math.cos(angle) * secArrowlenght).toFloat(),
            (widthScreen / 2 + Math.sin(angle) * secArrowlenght).toFloat(),
            paint
        )
    }

    private fun drawMinArrow(canvas: Canvas?, loc: Float) {
        val angle: Double = Math.PI * loc / 30 - Math.PI / 2
        paint.color = Color.BLUE
        paint.strokeWidth = 6f
        canvas?.drawLine(
            (widthScreen / 2 - Math.cos(angle) * minArrowLenght / 10).toFloat(),
            (widthScreen / 2 - Math.sin(angle) * minArrowLenght / 10).toFloat(),
            (widthScreen / 2 + Math.cos(angle) * minArrowLenght).toFloat(),
            (widthScreen / 2 + Math.sin(angle) * minArrowLenght).toFloat(),
            paint
        )
    }

    private fun drawArrows(canvas: Canvas?) {
        val calendar = Calendar.getInstance()
        var houre = calendar.get(Calendar.HOUR_OF_DAY)
        if (houre > 12) houre -= 12
        drawHoureArrow(canvas, (houre + calendar.get(Calendar.MINUTE) / 60f) * 5)
        drawMinArrow(canvas, calendar.get(Calendar.MINUTE).toFloat())
        drawSecArrow(canvas, calendar.get(Calendar.SECOND).toFloat())

    }

    private fun drawHoureArrow(canvas: Canvas?, loc: Float) {
        val angle = Math.PI * loc / 30 - Math.PI / 2
        canvas?.drawLine(
            (widthScreen / 2 - Math.cos(angle) * hourArrowLenght / 10).toFloat(),
            (widthScreen / 2 - Math.sin(angle) * hourArrowLenght / 10).toFloat(),
            (widthScreen / 2 + Math.cos(angle) * hourArrowLenght).toFloat(),
            (widthScreen / 2 + Math.sin(angle) * hourArrowLenght).toFloat(),
            paint
        )
    }

    private fun drawHoures(canvas: Canvas?) {

        for (i in 1..12) {
            val angle: Double = Math.PI * i / 30 * 5f - Math.PI / 2
            canvas?.drawLine(
                (widthScreen / 2 + Math.cos(angle) * radius * 0.9).toFloat(),
                (widthScreen / 2 + Math.sin(angle) * radius * 0.9).toFloat(),
                (widthScreen / 2 + Math.cos(angle) * radius).toFloat(),
                (widthScreen / 2 + Math.sin(angle) * radius).toFloat(),
                paint
            )
        }
    }

    private fun drawCircle(canvas: Canvas?) {
        paint.color = Color.BLACK
        paint.setAntiAlias(true)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        canvas?.drawCircle(widthScreen / 2, heightScreen / 2, radius, paint)
    }
}