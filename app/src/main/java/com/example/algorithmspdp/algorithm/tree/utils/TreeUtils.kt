package com.example.algorithmspdp.algorithm.tree.utils

import com.example.algorithmspdp.algorithm.tree.model.TreeNode
import kotlin.random.Random

object TreeUtils {

    fun generateRandomTree(size: Int): TreeNode? {
        if (size == 0) return null
        val values = (1..size).map { Random.nextInt(10, 99) }
        var root: TreeNode? = null
        for (v in values) {
            root = insert(root, v)
        }
        resetVisited(root)
        return root
    }

    private fun resetVisited(node: TreeNode?) {
        if (node == null) return
        node.visited = false
        resetVisited(node.left)
        resetVisited(node.right)
    }

    private fun insert(node: TreeNode?, value: Int): TreeNode {
        if (node == null) return TreeNode(value)
        if (value < node.value) {
            node.left = insert(node.left, value)
        } else {
            node.right = insert(node.right, value)
        }
        return node
    }
}