package com.adrpien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.adrpien.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Handling FloatButton click
        binding.addNoteFloatButton.setOnClickListener {
            val intent = Intent(applicationContext, AddNoteActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        // Creating DataBase
        val notesDataBaseHelper = NotesDataBaseHelper(applicationContext)
        val dataBase = notesDataBaseHelper.writableDatabase

        val cursor = dataBase.query(
            NotesDataBaseInfo.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null,
            null )

        // Creating and filling  ArrayList of Note objects
        val notesArray: ArrayList<Note> = arrayListOf()
        if(cursor.count>0){
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val note = Note()
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.description = cursor.getString(2)
                notesArray.add(note)
                cursor.moveToNext()
            }
        }

        // Creating Recycler View
        val recyclerView = binding.notesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = NotesAdapter(applicationContext, dataBase, notesArray)


    }
}