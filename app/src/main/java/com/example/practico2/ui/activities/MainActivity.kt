package com.example.practico2.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practico2.R
import com.example.practico2.ui.adapters.NoteAdapter
import com.example.practico2.ui.models.Note
import com.example.practico2.ui.models.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteAdapter.NoteListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddNote : FloatingActionButton
    private lateinit var adapter: NoteAdapter

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val note = result.data?.getSerializableExtra("note") as Note
                if(note.getId() == -1) {
                    createNote(note)
                } else {
                    saveNote(note)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.lstNotes)
        fabAddNote = findViewById(R.id.fabAddNote)

        setupRecyclerView()

        fabAddNote.setOnClickListener {
            val newNote = Note("Titulo", ArrayList(), -1)
            openNoteActivity(newNote)
        }

        for (note in generateNote()){
            adapter.addNote(note)
        }
    }

    private fun generateNote() : ArrayList<Note>{
        val notes = ArrayList<Note>()
        for (i in 0..5){
            val tasks = ArrayList<Task>()
            for (j in 0..10){
                val task = Task("Tarea 1", false)
                tasks.add(task)
            }
            notes.add(Note("Titulo $i", tasks, -1))
        }
        return notes
    }

    private fun setupRecyclerView() {
        recyclerView.adapter = NoteAdapter(ArrayList(),this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = recyclerView.adapter as NoteAdapter
    }

    private fun openNoteActivity(note : Note){
        val intent = Intent(this, OpenNoteActivity::class.java)
        intent.putExtra("note", note)
        resultLauncher.launch(intent)
    }

    private fun createNote(note : Note) {
        adapter.addNote(note)
    }

    private fun saveNote(note : Note){
        adapter.saveNote(note)
    }

    override fun onNoteClicked(note: Note) {
        openNoteActivity(note)
    }

    override fun onNoteDeleted(note : Note) {
        adapter.deleteNoteAt(note)
    }
}