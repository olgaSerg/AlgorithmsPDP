package com.example.algorithmspdp.algorithm.search

import com.example.algorithmspdp.view.BarView
import kotlinx.coroutines.delay

object BinarySearch {

    suspend fun search(array: List<Int>, barView: BarView, target: Int) {
        var left = 0
        var right = array.size - 1
        val targetIndex = array.indexOf(target)

        while (left <= right) {
            val mid = (left + right) / 2

            barView.setArray(
                array,
                highlights = setOf(mid),
                range = left..right,
                targetIndex = targetIndex
            )
            delay(3000)

            when {
                array[mid] == target -> {
                    barView.setArray(
                        array,
                        highlights = setOf(mid),
                        range = left..right,
                        pivot = mid,
                        targetIndex = targetIndex
                    )
                    delay(3000)
                    return
                }
                array[mid] < target -> {
                    left = mid + 1
                }
                else -> {
                    right = mid - 1
                }
            }
        }
    }
}