package com.example.algorithmspdp.algorithm

enum class Algorithm(val label: String) {
    BUBBLE("Bubble Sort"),
    MERGE("Merge Sort"),
    QUICK("Quick Sort"),
    BINARY_SEARCH("Binary Search"),
    DFS_TREE("DFS"),
    BFS_TREE("BFS");

    override fun toString(): String = label
}