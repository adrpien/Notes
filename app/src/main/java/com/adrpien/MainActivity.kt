package com.adrpien

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adrpien.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Creating Recycler View
        val recyclerView = binding.notesRecyclerView
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = NotesAdapter(applicationContext, dataBase)

    }
}