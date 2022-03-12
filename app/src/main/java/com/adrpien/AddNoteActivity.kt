package com.adrpien

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.adrpien.databinding.ActivityAddNoteBinding
import com.adrpien.databinding.ActivityMainBinding

// Edit/add note activity
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

            if((title.isEmpty() && description.isEmpty())){
                    Toast.makeText(applicationContext, "Pusta notatka! Nie zapisano!", Toast.LENGTH_SHORT)
                } else {

                    // Creating ContentValues for database
                    val contentValues = ContentValues()
                    contentValues.put(NotesDataBaseInfo.TABLE_COLUMN_TITLE, title)
                    contentValues.put(NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION, description)

                    // Saving/editing note
                    if(true){
                        dataBase.insert(NotesDataBaseInfo.TABLE_NAME, null, contentValues)
                        Toast.makeText(applicationContext, "Dodano nową notatkę", Toast.LENGTH_SHORT).show()
                    } else {
                        dataBase.update(NotesDataBaseInfo.TABLE_NAME, contentValues, null, null)
                        Toast.makeText(applicationContext, "Edytowano notatkę", Toast.LENGTH_SHORT)
                    }
                }
            }


        }
    }