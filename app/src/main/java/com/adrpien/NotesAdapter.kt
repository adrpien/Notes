package com.adrpien

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView


// RecyclerView Adapter implementation
class NotesAdapter(val context: Context, val db: SQLiteDatabase, val notes: ArrayList<Note>): RecyclerView.Adapter<NotesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // Fun inflate of layoutInflater class creates object View using XML file
        val view = layoutInflater.inflate(R.layout.note_cell, parent, false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {

        // Why I had to use findViewById??
        val noteCardView: CardView = holder.view.findViewById<CardView>(R.id.noteCardView)
        val title: TextView = holder.view.findViewById<TextView>(R.id.titleTextView)
        val description: TextView = holder.view.findViewById<TextView>(R.id.descriptionTextView)

        title.setText(notes[holder.adapterPosition].title)
        description.setText(notes[holder.adapterPosition].description)

        // Edit when noteCardView clicked
        noteCardView.setOnClickListener {
            val intent = Intent(it.context, AddNoteActivity::class.java)
            intent.putExtra(NotesDataBaseInfo.TABLE_COLUMN_TITLE, notes[holder.adapterPosition].title)
            intent.putExtra(NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION, notes[holder.adapterPosition].description)
            intent.putExtra(BaseColumns._ID, notes[holder.adapterPosition].id.toString())
            it.context.startActivity(intent)
        }

        noteCardView.setOnLongClickListener(object: View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {

                /*//Handling ClipboardManager
                var cm = context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                var cd = ClipData.newPlainText("Copy text", "Text : ${notes[holder.adapterPosition].title}" +
                                                "Description : ${notes[holder.adapterPosition].description}")
                cm.setPrimaryClip(cd)*/


                // Removing note
                val delete = db.delete(
                    NotesDataBaseInfo.TABLE_NAME,
                    BaseColumns._ID + "=?",
                    arrayOf(notes[holder.adapterPosition].id.toString())
                )
                notes.removeAt(holder.adapterPosition)
                notifyItemRemoved(holder.adapterPosition)
                return true
            }
        })
    }

    override fun getItemCount(): Int {

        val cursor = db.query(
            NotesDataBaseInfo.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null)
        val cursorCount: Int = cursor.count
        cursor.close()
        return  cursorCount
    }
}

// ViewHolder implementation
class NotesHolder(val view: View) : RecyclerView.ViewHolder(view) {}