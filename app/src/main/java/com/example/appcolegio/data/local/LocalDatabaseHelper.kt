package com.example.appcolegio.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appcolegio.data.model.AssignmentConfig

class LocalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MpazPro.db"
        private const val DATABASE_VERSION = 4 // Increment version for multiple students/teachers seed

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USUARIO = "usuario"
        const val COLUMN_CONTRASENA = "contrasena"
        const val COLUMN_ROLE = "role"

        const val TABLE_ASSIGNMENT = "assignment_config"
        const val TABLE_UTP_APPROVAL = "utp_approval"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createUsersTable = ("CREATE TABLE $TABLE_USERS ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_USUARIO TEXT UNIQUE,"
                + "$COLUMN_CONTRASENA TEXT,"
                + "$COLUMN_ROLE TEXT)")
        db.execSQL(createUsersTable)

        db.execSQL(
            """
            CREATE TABLE $TABLE_ASSIGNMENT (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                course TEXT NOT NULL,
                due_date TEXT NOT NULL,
                max_attempts INTEGER NOT NULL,
                show_correction INTEGER NOT NULL,
                is_published INTEGER NOT NULL
            )
            """.trimIndent()
        )

        db.execSQL(
            """
            CREATE TABLE $TABLE_UTP_APPROVAL (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                status TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Initial Seed
        val cvAssignment = ContentValues().apply {
            put("course", "6.° A - 34 estudiantes")
            put("due_date", "Viernes 9 de octubre - 18:00")
            put("max_attempts", 3)
            put("show_correction", 1)
            put("is_published", 1)
        }
        db.insert(TABLE_ASSIGNMENT, null, cvAssignment)

        val cvUtp = ContentValues().apply {
            put("status", "PENDIENTE")
        }
        db.insert(TABLE_UTP_APPROVAL, null, cvUtp)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ASSIGNMENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_UTP_APPROVAL")
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
                ContentValues().apply { put(COLUMN_USUARIO, "profe2"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "DOCENTE") },
                ContentValues().apply { put(COLUMN_USUARIO, "alumno1"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "ESTUDIANTE") },
                ContentValues().apply { put(COLUMN_USUARIO, "alumno2"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "ESTUDIANTE") },
                ContentValues().apply { put(COLUMN_USUARIO, "alumno3"); put(COLUMN_CONTRASENA, "1234"); put(COLUMN_ROLE, "ESTUDIANTE") }
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

    fun getAssignmentConfig(): AssignmentConfig {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT course, due_date, max_attempts, show_correction, is_published FROM $TABLE_ASSIGNMENT ORDER BY id DESC LIMIT 1", null)
        var config = AssignmentConfig()
        if (cursor.moveToFirst()) {
            config = AssignmentConfig(
                course = cursor.getString(0),
                dueDate = cursor.getString(1),
                maxAttempts = cursor.getInt(2),
                showCorrection = cursor.getInt(3) == 1,
                isPublished = cursor.getInt(4) == 1
            )
        }
        cursor.close()
        return config
    }

    fun updateAssignmentConfig(config: AssignmentConfig) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("course", config.course)
            put("due_date", config.dueDate)
            put("max_attempts", config.maxAttempts)
            put("show_correction", if (config.showCorrection) 1 else 0)
            put("is_published", if (config.isPublished) 1 else 0)
        }
        db.insert(TABLE_ASSIGNMENT, null, cv)
    }

    fun getUtpStatus(): String {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT status FROM $TABLE_UTP_APPROVAL ORDER BY id DESC LIMIT 1", null)
        var status = "PENDIENTE"
        if (cursor.moveToFirst()) {
            status = cursor.getString(0)
        }
        cursor.close()
        return status
    }

    fun updateUtpStatus(status: String) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("status", status)
        }
        db.insert(TABLE_UTP_APPROVAL, null, cv)
    }
}
