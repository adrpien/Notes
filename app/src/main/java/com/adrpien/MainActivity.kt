package com.adrpien

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.GridLayoutManager
import com.adrpien.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var isClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handling FloatButton click
        binding.addNoteFloatButton.setOnClickListener {

            val rotateOpenAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_right_anim)
            val translateOpenAnim = AnimationUtils.loadAnimation(this, R.anim.expand_float_buttons_anims)
            val rotateCloseAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_left_anim)
            val translateCloseAnim = AnimationUtils.loadAnimation(this, R.anim.collapse_float_buttons_anims)
            if(!isClicked) {
                binding.addNoteFloatButton.startAnimation(rotateOpenAnim)

                binding.addSoundNoteFloatButton.startAnimation(translateOpenAnim)
                binding.addGraphicNoteFloatButton.startAnimation(translateOpenAnim)
                binding.addTextNoteFloatButton.startAnimation(translateOpenAnim)

                binding.addGraphicNoteFloatButton.visibility = View.VISIBLE
                binding.addTextNoteFloatButton.visibility = View.VISIBLE
                binding.addSoundNoteFloatButton.visibility = View.VISIBLE

                binding.addSoundNoteFloatButton.isClickable = true
                binding.addGraphicNoteFloatButton.isClickable = true
                binding.addTextNoteFloatButton.isClickable = true
            } else {
                binding.addNoteFloatButton.startAnimation(rotateCloseAnim)

                binding.addSoundNoteFloatButton.startAnimation(translateCloseAnim)
                binding.addGraphicNoteFloatButton.startAnimation(translateCloseAnim)
                binding.addTextNoteFloatButton.startAnimation(translateCloseAnim)

                binding.addGraphicNoteFloatButton.visibility = View.INVISIBLE
                binding.addTextNoteFloatButton.visibility = View.INVISIBLE
                binding.addSoundNoteFloatButton.visibility = View.INVISIBLE

                binding.addSoundNoteFloatButton.isClickable = false
                binding.addGraphicNoteFloatButton.isClickable = false
                binding.addTextNoteFloatButton.isClickable = false
            }
            isClicked = !isClicked



            // val intent = Intent(applicationContext, AddNoteActivity::class.java)
            // startActivity(intent)
        }




    }

    override fun onResume() {
        super.onResume()

        // Creating DataBase
        val notesDataBaseHelper = NotesDataBaseHelper(applicationContext)
        val dataBase = notesDataBaseHelper.writableDatabase

        // Getting cursor with all notes
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
        val notesArray: ArrayList<TextNote> = arrayListOf()
        if(cursor.count>0){
            cursor.moveToFirst()
            while (!cursor.isAfterLast){
                val note = TextNote(-1)
                note.id = cursor.getInt(0)
                note.title = cursor.getString(1)
                note.description = cursor.getString(2)
                notesArray.add(note)
                cursor.moveToNext()
            }
        }
        cursor.close()

        // Creating Recycler View
        val recyclerView = binding.notesRecyclerView
        // Setting layoutManager
        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        //Setting adapter
        recyclerView.adapter = NotesAdapter(applicationContext, dataBase, notesArray)


    }
}