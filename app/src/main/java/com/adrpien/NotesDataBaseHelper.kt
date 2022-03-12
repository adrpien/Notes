package com.adrpien

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


// Table info
object NotesDataBaseInfo: BaseColumns{
    const val TABLE_NAME: String = "Notes"
    const val TABLE_COLUMN_TITLE: String = "Title"
    const val TABLE_COLUMN_DESCRIPTION: String = "Description"
}


// Table Commands
object NotesComands {
    const val SQL_CREATE_COMMAND = "CREATE TABLE ${NotesDataBaseInfo.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
            "${NotesDataBaseInfo.TABLE_COLUMN_TITLE} TEXT NOT NULL, " +
            "${NotesDataBaseInfo.TABLE_COLUMN_DESCRIPTION} TEXT NOT NULL)"

    const val  SQL_DELETE = "DROP TABLE IF EXISTS ${NotesDataBaseInfo.TABLE_NAME}"
}


// SQLiteHelper implementation
class NotesDataBaseHelper(context: Context): SQLiteOpenHelper(context, NotesDataBaseInfo.TABLE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NotesComands.SQL_CREATE_COMMAND)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(NotesComands.SQL_DELETE)
        onCreate(db)
    }

}