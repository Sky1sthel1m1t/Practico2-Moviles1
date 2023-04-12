package com.example.practico2.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practico2.R
import com.example.practico2.ui.adapters.TaskAdapter
import com.example.practico2.ui.models.Note
import com.example.practico2.ui.models.Task

class OpenNoteActivity : AppCompatActivity() {

    private lateinit var note: Note
    private lateinit var title: EditText
    private lateinit var btnAdd: ImageButton
    private lateinit var btnCancel: Button
    private lateinit var btnSave: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_note)

        title = findViewById(R.id.txtTitle)
        btnAdd = findViewById(R.id.btnAddWork)
        btnCancel = findViewById(R.id.btnCancelNote)
        btnSave = findViewById(R.id.btnSaveNote)
        recyclerView = findViewById(R.id.noteOpenRecyclerView)

        setupEventListeners()
        note = intent.getSerializableExtra("note") as Note
        loadNote()
    }

    private fun setupEventListeners() {
        btnAdd.setOnClickListener { addCheckBox() }
        btnCancel.setOnClickListener { finish() }
        btnSave.setOnClickListener { saveNote() }
    }

    private fun loadNote(){
        title.setText(note.getTitle())
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(note.getCheckBoxList(), this)
        recyclerView.adapter = taskAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun saveNote() {

        note = Note(
            title.text.toString(),
            note.getCheckBoxList(),
            note.getId()
        )

        intent.putExtra("note", note)
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun addCheckBox() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Ingrese el nombre de la tarea")
        val input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("Añadir") { dialog, which ->
            taskAdapter.addTask(Task(input.text.toString(), false))
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }
        builder.show()
    }
}