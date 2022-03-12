package com.adrpien

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView



// RecyclerView implementation
class NotesAdapter(val context: Context, val db: SQLiteDatabase): RecyclerView.Adapter<NotesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.note_cell, parent, false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val title: TextView = holder.view.findViewById<TextView>(R.id.titleTextView)
        val description: TextView = holder.view.findViewById<TextView>(R.id.descriptionTextView)

        val cursor = db.query(NotesDataBaseInfo.TABLE_NAME,
            null,
            BaseColumns._ID + "=?",
            arrayOf(holder.adapterPosition.plus(1).toString()),
            null,
            null,
            null)
        if(cursor.moveToFirst()) {
            if(!(cursor.getString(1).isNullOrEmpty() || cursor.getString(2).isNullOrEmpty())){
                title.text = cursor.getString(1)
                description.text = cursor.getString(2)
            } else {
                Toast.makeText(context, "Pusta notatka! Nie zapisano", Toast.LENGTH_SHORT).show()
            }
        }
        cursor.close()
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