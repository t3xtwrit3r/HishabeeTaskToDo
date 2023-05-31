package com.mubin.todo.ui

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mubin.todo.R
import com.mubin.todo.databinding.ItemViewTaskBinding
import com.mubin.todo.db.tables.TaskInfo


class TaskAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: List<TaskInfo> = listOf()

    var onChecked: ((task: TaskInfo, isDone: Boolean)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskViewHolder(ItemViewTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TaskViewHolder) {
            holder.bind(dataList[position], position)
        }
    }

    inner class TaskViewHolder(private val binding : ItemViewTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(taskInfo: TaskInfo, position: Int) {

            binding.taskName.text = taskInfo.taskTitle
            binding.taskCheckBox.isChecked = taskInfo.isDone == true

            if (taskInfo.isDone == true) {
                binding.taskName.setTextColor(context.getColor(R.color.grey))
                binding.taskName.paintFlags = binding.taskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.taskName.setTextColor(context.getColor(R.color.black))
                binding.taskName.paintFlags =  0
            }

            binding.taskCheckBox.setOnClickListener {
                if (binding.taskCheckBox.isChecked) {
                    onChecked?.invoke(taskInfo, true)
                } else {
                    onChecked?.invoke(taskInfo, false)
                }
            }

        }
    }

    fun setData(newList: List<TaskInfo>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtilCallback(dataList, newList))
        dataList = newList
        diffResult.dispatchUpdatesTo(this)
    }

}

class MyItemDiffCallback : DiffUtil.ItemCallback<TaskInfo>() {
    override fun areItemsTheSame(oldItem: TaskInfo, newItem: TaskInfo): Boolean {
        // Return true if the items have the same ID
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskInfo, newItem: TaskInfo): Boolean {
        // Return true if the items' contents are the same
        return oldItem == newItem
    }
}

class MyDiffUtilCallback(private val oldList: List<TaskInfo>, private val newList: List<TaskInfo>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return MyItemDiffCallback().areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return MyItemDiffCallback().areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}
