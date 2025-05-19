package com.example.algorithmspdp.algorithm.sorting

import com.example.algorithmspdp.view.BarView
import kotlinx.coroutines.delay

object QuickSort {

    suspend fun sort(array: MutableList<Int>, barView: BarView) {
        quickSort(array, 0, array.size - 1, barView)
    }

    private suspend fun quickSort(arr: MutableList<Int>, low: Int, high: Int, barView: BarView) {
        if (low < high) {
            val pi = partition(arr, low, high, barView)
            quickSort(arr, low, pi, barView)
            quickSort(arr, pi + 1, high, barView)
        }
    }

    private suspend fun partition(arr: MutableList<Int>, low: Int, high: Int, barView: BarView): Int {
        var pivotIndex = (low + high) / 2
        val pivot = arr[pivotIndex]
        var i = low
        var j = high

        while (true) {
            while (arr[i] < pivot) {
                i++
                visualize(arr, barView, pivotIndex, low, high, i, j)
            }

            while (arr[j] > pivot) {
                j--
                visualize(arr, barView, pivotIndex, low, high, i, j)
            }

            if (i >= j) {
                barView.setArray(
                    arr,
                    pivot = pivotIndex,
                    range = low..high,
                    rangeColor2 = true,
                    rangeLeft = low..j,
                    rangeRight = j + 1..high
                )
                delay(3000)
                return j
            }

            if (pivotIndex == i) pivotIndex = j else if (pivotIndex == j) pivotIndex = i
            arr.swap(i, j)
            visualize(arr, barView, pivotIndex, low, high, i, j, swap = true)
            i++
            j--
        }
    }

    private suspend fun visualize(
        arr: List<Int>,
        barView: BarView,
        pivotIndex: Int,
        low: Int,
        high: Int,
        i: Int,
        j: Int,
        swap: Boolean = false
    ) {
        barView.setArray(
            arr,
            pivot = pivotIndex,
            range = low..high,
            highlights = setOf(i, j),
            swaps = if (swap) setOf(i, j) else emptySet(),
            rangeLeft = low..i,
            rangeRight = j..high
        )
        delay(3000)
    }

    private fun MutableList<Int>.swap(i: Int, j: Int) {
        val temp = this[i]
        this[i] = this[j]
        this[j] = temp
    }
}