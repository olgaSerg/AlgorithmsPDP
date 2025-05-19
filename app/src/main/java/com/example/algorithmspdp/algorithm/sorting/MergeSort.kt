package com.example.algorithmspdp.algorithm.sorting

import com.example.algorithmspdp.view.BarView
import kotlinx.coroutines.delay

object MergeSort {

    suspend fun sort(array: MutableList<Int>, barView: BarView) {
        mergeSort(array, 0, array.size - 1, barView)
    }

    private suspend fun mergeSort(arr: MutableList<Int>, left: Int, right: Int, barView: BarView) {
        if (left < right) {
            val mid = (left + right) / 2
            mergeSort(arr, left, mid, barView)
            mergeSort(arr, mid + 1, right, barView)
            merge(arr, left, mid, right, barView)
        }
    }

    private suspend fun merge(
        arr: MutableList<Int>,
        left: Int,
        mid: Int,
        right: Int,
        barView: BarView
    ) {
        val leftList = arr.subList(left, mid + 1).toMutableList()
        val rightList = arr.subList(mid + 1, right + 1).toMutableList()

        barView.setArray(arr, range = left..right, rangeLeft = left..mid, rangeRight = mid + 1..right)
        delay(3000)

        var i = 0
        var j = 0
        var k = left

        while (i < leftList.size && j < rightList.size) {
            arr[k] = if (leftList[i] <= rightList[j]) {
                leftList[i++]
            } else {
                rightList[j++]
            }
            k++
        }

        while (i < leftList.size) {
            arr[k++] = leftList[i++]
        }

        while (j < rightList.size) {
            arr[k++] = rightList[j++]
        }

        barView.setArray(arr, range = left..right, rangeColor2 = true)
        delay(3000)
    }
}