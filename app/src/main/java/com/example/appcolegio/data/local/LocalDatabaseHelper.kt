package com.example.appcolegio.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appcolegio.data.model.AssignmentConfig
import com.example.appcolegio.data.model.UtpCriterion

class LocalDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "mpaz_pro_colegio.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_QUIZ_PROGRESS = "quiz_progress"
        private const val TABLE_ASSIGNMENT = "assignment_config"
        private const val TABLE_UTP_APPROVAL = "utp_approval"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_QUIZ_PROGRESS (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                score INTEGER NOT NULL,
                total_questions INTEGER NOT NULL,
                attempts_left INTEGER NOT NULL,
                user_answers TEXT NOT NULL
            )
            """.trimIndent()
        )

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
                status TEXT NOT NULL,
                criteria_json TEXT NOT NULL
            )
            """.trimIndent()
        )

        // Seed initial assignment config
        val cvAssignment = ContentValues().apply {
            put("course", "6.° A - 34 estudiantes")
            put("due_date", "Viernes 9 de octubre - 18:00")
            put("max_attempts", 3)
            put("show_correction", 1)
            put("is_published", 0)
        }
        db.insert(TABLE_ASSIGNMENT, null, cvAssignment)

        // Seed initial UTP approval status
        val cvUtp = ContentValues().apply {
            put("status", "PENDIENTE")
            put("criteria_json", "Alineación:Conforme|Claridad:Conforme|Accesibilidad:Conforme|Actividad:Revisar")
        }
        db.insert(TABLE_UTP_APPROVAL, null, cvUtp)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_QUIZ_PROGRESS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ASSIGNMENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_UTP_APPROVAL")
        onCreate(db)
    }

    fun saveQuizAttempt(score: Int, total: Int, attemptsLeft: Int, answersStr: String) {
        val db = writableDatabase
        val cv = ContentValues().apply {
            put("score", score)
            put("total_questions", total)
            put("attempts_left", attemptsLeft)
            put("user_answers", answersStr)
        }
        db.insert(TABLE_QUIZ_PROGRESS, null, cv)
    }

    fun getLastQuizAttempt(): Pair<Int, Int>? { // Pair(score, attemptsLeft)
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT score, attempts_left FROM $TABLE_QUIZ_PROGRESS ORDER BY id DESC LIMIT 1", null)
        var result: Pair<Int, Int>? = null
        if (cursor.moveToFirst()) {
            val score = cursor.getInt(0)
            val attemptsLeft = cursor.getInt(1)
            result = Pair(score, attemptsLeft)
        }
        cursor.close()
        return result
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
            put("criteria_json", "Alineación:Conforme|Claridad:Conforme|Accesibilidad:Conforme|Actividad:Conforme")
        }
        db.insert(TABLE_UTP_APPROVAL, null, cv)
    }
}
