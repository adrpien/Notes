package com.adrpien

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.adrpien.databinding.ActivityAddNoteBinding
import com.adrpien.databinding.ActivityMainBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val notesDataBaseHelper = NotesDataBaseHelper(binding.root.context)
        val dataBase = notesDataBaseHelper.writableDatabase

        binding.addNoteButton.setOnClickListener {

            val title = binding.addTitleTextView.text.toString()
            val description = binding.addDescriptionTextView.text.toString()

            val contentValues = ContentValues()
            contentValues.put("title", title)
            contentValues.put("description", description)

            dataBase.insertOrThrow(NotesDataBaseInfo.TABLE_NAME, null, contentValues)
        }
    }
}