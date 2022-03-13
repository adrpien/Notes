package com.adrpien

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.adrpien.databinding.ActivityAddNoteBinding

// Edit/add note activity
class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Filling addTitleTextView and addDescriptionTextView from intent extras
        if (intent.hasExtra(NotesDataBaseInfo.TABLE_COLUMN_TITLE)) {
            binding.addTitleTextView.setText(intent.getStringExtra(NotesDataBaseInfo.TABLE_COLUMN_TITLE))
        }
        if (intent.hasExtra(NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION)) {
            binding.addDescriptionTextView.setText(intent.getStringExtra(NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION))
        }
    }

    // Adding ActionBarMenu to Activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Handling menu item click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.saveButton){

            val title = binding.addTitleTextView.text.toString()
            val description = binding.addDescriptionTextView.text.toString()

            if((title.isEmpty() && description.isEmpty())){
                Toast.makeText(applicationContext, "Pusta notatka! Nie zapisano!", Toast.LENGTH_SHORT)
            } else {

                // Creating ContentValues for database
                val contentValues = ContentValues()
                contentValues.put(NotesDataBaseInfo.TABLE_COLUMN_TITLE, title)
                contentValues.put(NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION, description)

                val dataBaseHelper = NotesDataBaseHelper(binding.root.context)
                val dataBase = dataBaseHelper.writableDatabase

                // Saving/editing note
                if(!intent.hasExtra(BaseColumns._ID)){
                    dataBase.insert(
                        NotesDataBaseInfo.TABLE_NAME,
                        null,
                        contentValues)
                    Toast.makeText(applicationContext, "Dodano nową notatkę", Toast.LENGTH_SHORT).show()
                } else {
                    dataBase.update(
                        NotesDataBaseInfo.TABLE_NAME,
                        contentValues,
                        BaseColumns._ID + "=?",
                        arrayOf(intent.getStringExtra(BaseColumns._ID)))
                    Toast.makeText(applicationContext, "Edytowano notatkę", Toast.LENGTH_SHORT).show()
                }
                dataBase.close()
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}