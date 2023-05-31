package com.mubin.todo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mubin.todo.databinding.FragmentTaskListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskListFragment : Fragment() {

    private lateinit var binding: FragmentTaskListBinding

    private lateinit var taskAdapter: TaskAdapter

    private val tag = AddTaskBottomSheetFragment().tag

    private val taskAddBottomSheet = AddTaskBottomSheetFragment()

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return FragmentTaskListBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initClicks()
        initData()

    }

    private fun initViews() {

        taskAdapter = TaskAdapter(requireContext())
        binding.taskListRv.apply {

            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = taskAdapter

        }

        //taskAdapter.addAllTask(listOf(TaskInfo(0, "Kaj Nai","akjbskjbas",null,"High",1,false), TaskInfo(1, "Kaj Nai abar ache","akjbskjbas",null,"High",1,true)))

    }

    private fun initClicks() {

        binding.fab.setOnClickListener {

            taskAddBottomSheet.show(childFragmentManager, tag)

        }

        taskAddBottomSheet.onCreateClick = { data ->
            viewModel.insertTask(taskInfo = data).observe(viewLifecycleOwner) {
                initData()
            }
        }

        taskAdapter.onChecked = { taskInfo, isChecked ->

            Log.d("TaskInfo", "${taskInfo.id}")

            if (isChecked) {
                taskInfo.id?.let {
                    viewModel.updateTask(it, true).observe(viewLifecycleOwner) {
                        initData()
                    }
                }
            } else {
                taskInfo.id?.let {
                    viewModel.updateTask(it, false).observe(viewLifecycleOwner) {
                        initData()
                    }
                }
            }

        }



    }

    private fun initData() {

        viewModel.getAllTasks().observe(viewLifecycleOwner) { data ->

            Log.d("DataSize","${data.size}")

            if(data.isEmpty()) {
                binding.noDataTv.visibility = View.VISIBLE
                binding.taskListRv.visibility = View.GONE
            } else {
                binding.noDataTv.visibility = View.GONE
                binding.taskListRv.visibility = View.VISIBLE
                taskAdapter.setData(data)
            }

        }

    }

}