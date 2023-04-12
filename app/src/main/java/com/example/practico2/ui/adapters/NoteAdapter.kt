package com.example.practico2.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.allViews
import androidx.recyclerview.widget.RecyclerView
import com.example.practico2.R
import com.example.practico2.ui.models.Note
import com.example.practico2.ui.models.Task

class NoteAdapter(
    private val noteList: ArrayList<Note>,
    private val noteListener: NoteListener
) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.noteTitle.text = note.getTitle()

        holder.noteTasks.removeAllViews()

        for (task in note.getCheckBoxList()) {
            val checkBox = CheckBox(holder.noteTasks.context)
            checkBox.text = task.title
            checkBox.isChecked = task.check
            checkBox.isClickable = false
            checkBox.setBackgroundColor(Color.TRANSPARENT)
            holder.noteTasks.addView(checkBox)
        }

        holder.itemView.setOnClickListener {
            noteListener.onNoteClicked(note)
        }

        holder.btnDelete.setOnClickListener {
            noteListener.onNoteDeleted(note)
        }
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun addNote(note: Note) {
        note.setId(getLastId() + 1)
        noteList.add(note)
        notifyItemInserted(noteList.size)
    }

    fun saveNote(note: Note) {
        val index = noteList.indexOfFirst { it.getId() == note.getId() }
        noteList[index] = note
        notifyItemChanged(index)
    }

    fun deleteNoteAt(note : Note) {
        val position = noteList.indexOfFirst { it.getId() == note.getId() }
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getLastId(): Int {
        return if (noteList.size == 0) 0 else noteList[noteList.size - 1].getId()
    }


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle = itemView.findViewById<TextView>(R.id.lbTitlePreview)
        val noteTasks = itemView.findViewById<LinearLayout>(R.id.taskLayout)
        val btnDelete = itemView.findViewById<Button>(R.id.btnDeleteNote)
    }

    interface NoteListener {
        fun onNoteClicked(note: Note)
        fun onNoteDeleted(note : Note)
    }


}
