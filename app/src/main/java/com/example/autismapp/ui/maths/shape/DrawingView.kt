package com.example.autismapp.ui.maths.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var paintColor = Color.BLACK // Varsayılan kalem rengi
    private val paint = Paint().apply {
        color = paintColor
        isAntiAlias = true
        strokeWidth = 8f
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }
    private val path = Path()
    private val points = mutableListOf<Pair<Float, Float>>()

    init {
        setBackgroundColor(Color.WHITE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                points.clear()
                points.add(Pair(x, y))
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                points.add(Pair(x, y))
            }
            MotionEvent.ACTION_UP -> {}
        }
        invalidate()
        return true
    }

    fun clear() {
        path.reset()
        points.clear()
        invalidate()
    }

    fun setPenColor(@ColorInt color: Int) {
        paint.color = color
        paintColor = color
    }

    fun isDrawingCorrect(shape: String): Boolean {
        return when (shape) {
            "kare" -> isSquare()
            "üçgen" -> isTriangle()
            "dikdörtgen" -> isRectangle()
            "daire" -> isCircle()
            else -> false
        }
    }

    private fun isSquare(): Boolean {
        if (points.size < 4) return false

        val a = points[0]
        val b = points[1]
        val c = points[2]
        val d = points[3]

        // Kareyi tanımlayan özellik: Tüm kenarlar aynı uzunlukta olmalıdır.
        val ab = distanceBetweenPoints(a, b)
        val bc = distanceBetweenPoints(b, c)
        val cd = distanceBetweenPoints(c, d)
        val da = distanceBetweenPoints(d, a)

        // Tolerans değeri belirlenebilir veya eşitlik kontrolü yapılabilir.
        val tolerance = 20f // Tolerans değeri (uygun şekilde ayarlanmalı)

        // Kare için, tüm kenarların uzunluklarının eşit olup olmadığını kontrol et
        return (abs(ab - bc) < tolerance && abs(bc - cd) < tolerance &&
                abs(cd - da) < tolerance && abs(da - ab) < tolerance)
    }


    private fun distanceBetweenPoints(point1: Pair<Float, Float>, point2: Pair<Float, Float>): Float {
        return sqrt((point2.first - point1.first).pow(2) + (point2.second - point1.second).pow(2))
    }

    private fun isTriangle(): Boolean {
        if (points.size < 3) return false

        val a = points[0]
        val b = points[1]
        val c = points[2]

        // Her iki kenarın uzunluklarını hesapla
        val ab = sqrt((b.first - a.first).pow(2) + (b.second - a.second).pow(2))
        val bc = sqrt((c.first - b.first).pow(2) + (c.second - b.second).pow(2))
        val ac = sqrt((c.first - a.first).pow(2) + (c.second - a.second).pow(2))

        // Üçgenin kenar uzunluklarını kontrol et
        return (ab + bc > ac && ab + ac > bc && ac + bc > ab)
    }

    private fun isRectangle(): Boolean {
        if (points.size < 4) return false

        val a = points[0]
        val b = points[1]
        val c = points[2]
        val d = points[3]

        // Dikdörtgeni tanımlayan özellik: Karşılıklı kenarların uzunlukları yaklaşık olarak eşit olmalıdır.
        val ab = distanceBetweenPoints(a, b)
        val bc = distanceBetweenPoints(b, c)
        val cd = distanceBetweenPoints(c, d)
        val da = distanceBetweenPoints(d, a)

        // Tolerans değeri belirlenebilir veya eşitlik kontrolü yapılabilir.
        val tolerance = 20f // Tolerans değeri (uygun şekilde ayarlanmalı)

        // Dikdörtgen için, karşılıklı kenarların uzunluklarının yaklaşık olarak eşit olup olmadığını kontrol et
        return (abs(ab - cd) < tolerance && abs(bc - da) < tolerance)
    }


    private fun isCircle(): Boolean {
        if (points.size < 10) return false

        val centerX = points.map { it.first }.average()
        val centerY = points.map { it.second }.average()

        val radii = points.map { sqrt((it.first - centerX).pow(2) + (it.second - centerY).pow(2)) }
        val averageRadius = radii.average()
        val radiusDeviation = radii.map { abs(it - averageRadius) }.average()

        return radiusDeviation < 20 // İzin verilen sapma
    }

    fun setDrawingColor(@ColorInt color: Int) {
        paint.color = color
        paintColor = color
    }
}
