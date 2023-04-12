package com.example.practico2.ui.models

data class Note(
    private var title: String,
    private var taskList: ArrayList<Task>,
    private var id: Int
) : java.io.Serializable {

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getCheckBoxList(): ArrayList<Task> {
        return taskList
    }

    fun setCheckBoxList(taskList: ArrayList<Task>) {
        this.taskList = taskList
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun addTask(task : Task) {
        taskList.add(task)
    }

}