package com.example.algorithmspdp.algorithm.tree.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.algorithmspdp.algorithm.tree.model.TreeNode

class TreeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private var root: TreeNode? = null
    private var highlighted: TreeNode? = null

    private val nodePaint = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 40f
        textAlign = Paint.Align.CENTER
        isAntiAlias = true
    }

    private val linePaint = Paint().apply {
        color = Color.DKGRAY
        strokeWidth = 5f
    }

    private val highlightPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val visitedPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }

    fun setTree(root: TreeNode?) {
        this.root = root
        calculateNodePositions()
        invalidate()
    }

    fun highlightNode(node: TreeNode?) {
        highlighted = node
        invalidate()
    }

    private fun calculateNodePositions() {
        val depthMap = mutableMapOf<Int, MutableList<TreeNode>>()
        fun dfs(node: TreeNode?, depth: Int) {
            if (node == null) return
            depthMap.getOrPut(depth) { mutableListOf() }.add(node)
            dfs(node.left, depth + 1)
            dfs(node.right, depth + 1)
        }
        dfs(root, 0)

        val levelHeight = height / (depthMap.size + 1)
        for ((depth, nodes) in depthMap) {
            val y = (depth + 1) * levelHeight.toFloat()
            val spacing = width / (nodes.size + 1)
            for ((i, node) in nodes.withIndex()) {
                node.x = (i + 1) * spacing.toFloat()
                node.y = y
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawTree(canvas, root)
    }

    private fun drawTree(canvas: Canvas, node: TreeNode?) {
        if (node == null) return

        node.left?.let {
            canvas.drawLine(node.x, node.y, it.x, it.y, linePaint)
            drawTree(canvas, it)
        }

        node.right?.let {
            canvas.drawLine(node.x, node.y, it.x, it.y, linePaint)
            drawTree(canvas, it)
        }

        val paint = when {
            node == highlighted -> highlightPaint
            node.visited -> visitedPaint
            else -> nodePaint
        }

        canvas.drawCircle(node.x, node.y, 50f, paint)
        canvas.drawText(node.value.toString(), node.x, node.y + 15f, textPaint)
    }
}