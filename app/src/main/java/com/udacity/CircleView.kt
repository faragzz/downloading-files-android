package com.udacity

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.jar.Attributes

class CircleView @JvmOverloads constructor(
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
        color = Color.YELLOW
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("",Typeface.BOLD)
    }
    val recF = RectF(0f,0f,100f,100f)
    val offest = 0f
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc( // change the angle
            recF,offest,step.toFloat(),true,paint
        )
    }
}

