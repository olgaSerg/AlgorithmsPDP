package com.example.algorithmspdp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class BarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var array: List<Int> = emptyList()

    private var pivotIndex: Int? = null
    private var range: IntRange? = null
    private var targetIndex: Int? = null
    private var highlightIndices: Set<Int> = emptySet()
    private var swapIndices: Set<Int> = emptySet()

    private val paintBar = Paint().apply { color = Color.BLUE }
    private val paintPivot = Paint().apply { color = Color.GREEN }
    private val paintSwap = Paint().apply { color = Color.RED }
    private val paintCompare = Paint().apply { color = Color.YELLOW }
    private val paintRange = Paint().apply { color = Color.LTGRAY }
    private val paintRange2 = Paint().apply { color = Color.MAGENTA }

    private val paintRangeLeft = Paint().apply { color = Color.CYAN }
    private val paintRangeRight = Paint().apply { color = Color.rgb(255, 128, 0) }
    private val paintTarget = Paint().apply { color = Color.rgb(255, 165, 0) }

    private var rangeColor2: Boolean = false
    private var rangeLeft: IntRange? = null
    private var rangeRight: IntRange? = null

    fun setArray(
        newArray: List<Int>,
        pivot: Int? = null,
        range: IntRange? = null,
        highlights: Set<Int> = emptySet(),
        swaps: Set<Int> = emptySet(),
        rangeColor2: Boolean = false,
        rangeLeft: IntRange? = null,
        rangeRight: IntRange? = null,
        targetIndex: Int? = null
    ) {
        array = newArray.toList()
        pivotIndex = pivot
        highlightIndices = highlights
        swapIndices = swaps
        this.range = range
        this.rangeColor2 = rangeColor2
        this.rangeLeft = rangeLeft
        this.rangeRight = rangeRight
        this.targetIndex = targetIndex
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (array.isEmpty()) return

        val barWidth = width / array.size.toFloat()
        val maxVal = array.maxOrNull() ?: 1

        for (i in array.indices) {
            val barHeight = (array[i] / maxVal.toFloat()) * height
            val left = i * barWidth
            val top = height - barHeight
            val right = left + barWidth * 0.8f

            if (range?.contains(i) == true) {
                var curColor = paintRange
                if (this.rangeColor2) curColor = paintRange2
                canvas.drawRect(left, 0f, left + barWidth * 0.8f, height.toFloat(), curColor)
            }

            val paint = when {
                pivotIndex == i -> paintPivot
                highlightIndices.contains(i) -> paintCompare
                targetIndex == i -> paintTarget
                swapIndices.contains(i) -> paintSwap
                rangeLeft?.contains(i) ?: false -> paintRangeLeft
                rangeRight?.contains(i) ?: false -> paintRangeRight
                else -> paintBar
            }

            canvas.drawRect(left, top, right, height.toFloat(), paint)
        }
    }
}