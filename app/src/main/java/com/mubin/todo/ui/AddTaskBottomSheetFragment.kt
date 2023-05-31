package com.mubin.todo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mubin.todo.R
import com.mubin.todo.databinding.FragmentAddTaskBottomSheetBinding
import com.mubin.todo.db.tables.TaskInfo
import com.mubin.todo.helper.DatePickerFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class AddTaskBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentAddTaskBottomSheetBinding

    var onCreateClick: ((task: TaskInfo)->Unit)? = null

    companion object {
        val tag: String = BottomSheetDialogFragment::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL,R.style.BottomSheetDialogThemeTopRoundedDraggable)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog?
        dialog?.setCanceledOnTouchOutside(true)
        val bottomSheet: FrameLayout? = dialog?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            thread {
                activity?.runOnUiThread {
                    val dynamicHeight = binding.parentLayout.height
                    behavior.peekHeight = dynamicHeight
                }
            }
            behavior.skipCollapsed = true

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentAddTaskBottomSheetBinding.inflate(layoutInflater,container,false).also {
            binding = it
        }.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
    }

    private fun initClick() {
        binding.taskDateLL.setOnClickListener {
            val dialogFragment = DatePickerFragment { currentDateInLong ,currentDateInString ->
                //currentDate = currentDateInLong
                //currentDateString = currentDateInString
                binding.taskDateTV.text = currentDateInString
            }
            dialogFragment.show(childFragmentManager,"DatePicker")
        }

        binding.createTaskBTN.setOnClickListener {

            if (binding.taskNameET.text.toString().isNotEmpty() &&
                binding.taskTypeET.text.toString().isNotEmpty() &&
                binding.taskDateTV.text.toString().isNotEmpty()) {

                val cal = Calendar.getInstance()

                val taskInfo = TaskInfo(
                    taskTitle = binding.taskNameET.text.toString(),
                    taskDetails = binding.taskTypeET.text.toString(),
                    taskDate = cal.time,
                    isDone = false
                )

                onCreateClick?.invoke(taskInfo)

                binding.taskNameET.text.clear()
                binding.taskTypeET.text.clear()
                binding.taskTypeET.text.clear()

                dismiss()


            } else {
                Toast.makeText(
                    requireContext(),
                    "Fields are mandatory",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

}