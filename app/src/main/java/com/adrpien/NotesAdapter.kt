package com.adrpien

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(val context: Context, val db: SQLiteDatabase): RecyclerView.Adapter<NotesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.note_cell, parent, false)
        return NotesHolder(view)
    }

    override fun onBindViewHolder(holder: NotesHolder, position: Int) {
        val title: TextView = holder.itemView.findViewById(R.id.titleTextView)
        val description: TextView = holder.itemView.findViewById(R.id.descriptionTextView)

        val cursor = db.query(ContactsContract.Contacts.CONTENT_URI,
            null,
            BaseColumns._ID + "=?",
            arrayOf<String>(holder.adapterPosition.plus(1).toString(), null, null, null) )

        title.text = TestDataBase.titles[position]
        description.text = TestDataBase.description[position]

    }

    override fun getItemCount(): Int {
        return TestDataBase.titles.size
    }
}

class NotesHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {}