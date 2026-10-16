package com.example.appcolegio.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MpazPro.db"
        private const val DATABASE_VERSION = 2 // Subimos la versión para que borre la BD vieja

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USUARIO = "usuario"
        const val COLUMN_CONTRASENA = "contrasena"
        const val COLUMN_ROLE = "role"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USUARIO TEXT UNIQUE,"
                + "$COLUMN_CONTRASENA TEXT,"
                + "$COLUMN_ROLE TEXT)")
        db.execSQL(createUsersTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun insertInitialUsers() {
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT count(*) FROM $TABLE_USERS", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        if (count == 0) {
            val users = listOf(
                ContentValues().apply { put(COLUMN_USUARIO, "utp1"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "UTP") },
                ContentValues().apply { put(COLUMN_USUARIO, "profe1"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "DOCENTE") },
                ContentValues().apply { put(COLUMN_USUARIO, "alumno1"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "ESTUDIANTE") }
            )
            for (user in users) {
                db.insert(TABLE_USERS, null, user)
            }
        }
        db.close()
    }

    fun checkUser(usuario: String, contrasena: String): String? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $COLUMN_ROLE FROM $TABLE_USERS WHERE $COLUMN_USUARIO = ? AND $COLUMN_CONTRASENA = ?",
            arrayOf(usuario, contrasena)
        )

        var role: String? = null
        if (cursor.moveToFirst()) {
            role = cursor.getString(0)
        }
        cursor.close()
        db.close()
        return role
    }
}