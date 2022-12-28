package com.udacity

import android.content.Context
import android.graphics.*
import android.provider.CalendarContract
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

class BarView @JvmOverloads constructor(
    context: Context,
    attrs:AttributeSet?=null,
    defStyleAttr:Int = 0
):View(context,attrs,defStyleAttr) {
    var step=0
        set(value){
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(8,132,132)
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("",Typeface.BOLD)
    }
    val recF = RectF(0f,0f,70f,70f)
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRect(
            0f,
            0f,
            step.toFloat(),//1100
            290f,
            paint
        )
    }
}

