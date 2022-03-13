package com.adrpien

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView



// RecyclerView Adapter implementation
class NotesAdapter(val context: Context, val db: SQLiteDatabase): RecyclerView.Adapter<NotesHolder>() {

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
        val titleArray: ArrayList<String> = arrayListOf()
        val descriptionArray: ArrayList<String> = arrayListOf()

        // Fun query returns object Cursor. We can compare cursor to array of results od your query.
        val cursor = db.query(NotesDataBaseInfo.TABLE_NAME,
            null,
            BaseColumns._ID + "=?",
            arrayOf(holder.adapterPosition.plus(1).toString()),
            null,
            null,
            null)

        // Check if cursor is not empty
        if(cursor.moveToFirst()) {
            if(!(cursor.getString(1).isNullOrEmpty() || cursor.getString(2).isNullOrEmpty())){
                title.text = cursor.getString(1)
                description.text = cursor.getString(2)
                // titleArray[cursor.position.minus(1)] = title.text.toString()
                // descriptionArray[cursor.position.minus(1)] = description.text.toString()
            }
        }
        cursor.close() // Closing cursor

        noteCardView.setOnClickListener {
            val intent = Intent(it.context, AddNoteActivity::class.java)
            intent.putExtra(NotesDataBaseInfo.TABLE_COLUMN_TITLE, title.text.toString())
            intent.putExtra(NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION, description.text.toString())
            intent.putExtra(BaseColumns._ID, holder.adapterPosition.plus(1).toString())
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        val cursor = db.query(NotesDataBaseInfo.TABLE_NAME,
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