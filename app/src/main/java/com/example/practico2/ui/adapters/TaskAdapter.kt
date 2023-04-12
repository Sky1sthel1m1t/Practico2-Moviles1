package com.example.practico2.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.practico2.R
import com.example.practico2.ui.models.Task

class TaskAdapter(
    private val taskList: ArrayList<Task>,
    private val context: Context
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = taskList[position]
        holder.checkBox.text = task.title
        holder.checkBox.isChecked = task.check

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.check = isChecked
        }

        holder.btnEdit.setOnClickListener {
            editTask(task, holder.checkBox, position)
        }

        holder.btnDelete.setOnClickListener {
            deleteTaskAt(task)
        }
    }

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.size)
    }

    private fun editTask(task: Task, checkBox: CheckBox, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Ingrese el nuevo nombre de la tarea")
        val input = EditText(context)
        builder.setView(input)
        builder.setPositiveButton("Guardar") { dialog, which ->
            task.title = input.text.toString()
            checkBox.text = input.text.toString()
            notifyItemChanged(position)
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    private fun deleteTaskAt(task : Task) {
        val position = taskList.indexOf(task)
        taskList.removeAt(position)
        notifyItemRemoved(position)
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)
        val btnEdit = itemView.findViewById<ImageButton>(R.id.btnEditTask)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btnDeleteTask)
    }

}