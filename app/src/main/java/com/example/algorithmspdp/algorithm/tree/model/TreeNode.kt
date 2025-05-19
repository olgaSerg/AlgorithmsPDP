package com.example.algorithmspdp.algorithm.tree.model

data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null,
    var x: Float = 0f,
    var y: Float = 0f,
    var visited: Boolean = false
)