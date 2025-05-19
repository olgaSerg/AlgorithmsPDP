package com.example.algorithmspdp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.algorithmspdp.algorithm.Algorithm
import com.example.algorithmspdp.algorithm.search.BinarySearch
import com.example.algorithmspdp.algorithm.sorting.BubbleSort
import com.example.algorithmspdp.algorithm.sorting.MergeSort
import com.example.algorithmspdp.algorithm.sorting.QuickSort
import com.example.algorithmspdp.algorithm.tree.TreeAlgorithms
import com.example.algorithmspdp.algorithm.tree.model.TreeNode
import com.example.algorithmspdp.algorithm.tree.utils.TreeUtils
import com.example.algorithmspdp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var array = mutableListOf<Int>()
    private var treeRoot: TreeNode? = null
    private val scope = CoroutineScope(Dispatchers.Main)

    private var selectedAlgorithm = Algorithm.BUBBLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()
        setupButtons()

        generateArray()
    }

    private fun setupSpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            Algorithm.entries.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAlgorithm.adapter = adapter

        binding.spinnerAlgorithm.setSelection(0)
        binding.spinnerAlgorithm.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: android.widget.AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedAlgorithm = Algorithm.entries[position]
                binding.treeView.visibility =
                    if (selectedAlgorithm in listOf(Algorithm.DFS_TREE, Algorithm.BFS_TREE)) View.VISIBLE else View.GONE
                binding.barView.visibility = if (selectedAlgorithm in listOf(
                        Algorithm.BUBBLE,
                        Algorithm.MERGE,
                        Algorithm.QUICK,
                        Algorithm.BINARY_SEARCH
                    )) View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }
    }

    private fun setupButtons() {
        binding.btnGenerate.setOnClickListener {
            generateArray()
        }

        binding.btnSort.setOnClickListener {
            sortArray()
        }
    }

    private fun generateArray() {
        if (selectedAlgorithm == Algorithm.DFS_TREE || selectedAlgorithm == Algorithm.BFS_TREE) {
            treeRoot = TreeUtils.generateRandomTree(15)
            binding.treeView.setTree(treeRoot)
        } else {
            array = MutableList(30) { Random.nextInt(10, 100) }

            if (selectedAlgorithm == Algorithm.BINARY_SEARCH) {
                array.sort()
            }

            binding.barView.setArray(array)
        }
    }

    private fun sortArray() {
        binding.btnSort.isEnabled = false
        binding.btnGenerate.isEnabled = false

        scope.launch {
            try {
                when (selectedAlgorithm) {
                    Algorithm.BUBBLE -> BubbleSort.sort(array, binding.barView)
                    Algorithm.MERGE -> MergeSort.sort(array, binding.barView)
                    Algorithm.QUICK -> QuickSort.sort(array, binding.barView)
                    Algorithm.BINARY_SEARCH -> {
                        val target = array.random()
                        BinarySearch.search(array, binding.barView, target)
                    }
                    Algorithm.DFS_TREE -> TreeAlgorithms.dfs(treeRoot, binding.treeView)
                    Algorithm.BFS_TREE -> TreeAlgorithms.bfs(treeRoot, binding.treeView)
                }
            } finally {
                binding.btnSort.isEnabled = true
                binding.btnGenerate.isEnabled = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}