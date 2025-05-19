package com.example.algorithmspdp.algorithm.sorting

import com.example.algorithmspdp.view.BarView
import kotlinx.coroutines.delay

object BubbleSort {

    suspend fun sort(array: MutableList<Int>, barView: BarView) {
        for (i in 0 until array.size - 1) {
            for (j in 0 until array.size - i - 1) {
                if (array[j] > array[j + 1]) {
                    array.swap(j, j + 1)
                    visualizeSwap(array, barView, j, j + 1)
                }
            }
        }
    }

    private suspend fun visualizeSwap(array: List<Int>, barView: BarView, i: Int, j: Int) {
        barView.setArray(array, swaps = setOf(i, j))
        delay(100)
    }

    private fun MutableList<Int>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}