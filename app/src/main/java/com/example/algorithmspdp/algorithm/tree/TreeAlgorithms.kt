package com.example.algorithmspdp.algorithm.tree

import com.example.algorithmspdp.algorithm.tree.model.TreeNode
import com.example.algorithmspdp.algorithm.tree.view.TreeView
import kotlinx.coroutines.delay

object TreeAlgorithms {

    suspend fun dfs(node: TreeNode?, treeView: TreeView) {
        if (node == null) return
        treeView.highlightNode(node)
        delay(500)

        node.visited = true
        treeView.invalidate()
        delay(300)

        dfs(node.left, treeView)
        dfs(node.right, treeView)
    }

    suspend fun bfs(root: TreeNode?, treeView: TreeView) {
        if (root == null) return
        val queue = ArrayDeque<TreeNode>()
        queue.add(root)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            treeView.highlightNode(node)
            delay(500)

            node.visited = true
            treeView.invalidate()
            delay(300)

            node.left?.let { queue.add(it) }
            node.right?.let { queue.add(it) }
        }
    }
}