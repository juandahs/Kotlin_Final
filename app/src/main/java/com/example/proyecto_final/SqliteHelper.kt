package com.example.proyecto_final

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(Context: RegistroActivity):SQLiteOpenHelper(Context, DB_NAME, null , DB_VERSION) {

    companion object{
        const val DB_NAME =  "juego.db"
        const val DB_VERSION = 1

        const val DB_TABLE = "Usuarios"
        const val USER_ID = "id"
        const val USER_NAME = "nombre"
        const val USER_SCORE  = "Puntaje"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $DB_TABLE (" +
                "$USER_ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", $USER_NAME TEXT NOT NULL" +
                ", $USER_SCORE INTEGER DEFAULT 0)"

        db?.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $DB_TABLE"
        db?.execSQL(query)
        onCreate(db)
    }

    fun insert(usuario: Usuario): Long{
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, usuario.nombre)
        contentValues.put(USER_SCORE, usuario.puntaje)
        val result = db.insert(DB_TABLE, null, contentValues)
        db.close()
        return result
    }

    fun getUsuarios(): ArrayList<Usuario> {
        val usuarios = arrayListOf<Usuario>()
        val db = readableDatabase
        val query = "SELECT * FROM $DB_TABLE"

        try {
            val cursor = db.rawQuery(query, null)
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(USER_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME))
                val puntaje = cursor.getInt(cursor.getColumnIndexOrThrow(USER_SCORE))
                val usuario = Usuario(id, nombre, puntaje)
                usuarios.add(usuario)
            }
            cursor.close()
            db.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return usuarios
    }

    fun delete(id: Int?) {
        val db = writableDatabase
        val result = db.delete(DB_TABLE, "$USER_ID=${id}", null)
        db.close()
    }
}