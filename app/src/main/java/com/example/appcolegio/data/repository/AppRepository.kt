package com.example.appcolegio.data.repository

import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.data.model.*

class AppRepository(private val dbHelper: LocalDatabaseHelper) {

    // 1. Función exclusiva para verificar el login en la base de datos
    fun login(username: String, pass: String): String? {
        return dbHelper.checkUser(username, pass)
    }

    // 2. Funciones conectadas al MockData para las pantallas

    fun getSubjects(): List<SubjectItem> {
        return MockData.subjects
    }

    fun getLearningObjectives(): List<LearningObjective> {
        return MockData.learningObjectives
    }

    // Funciones específicas que pedían los errores
    fun getInitialQuestions(): List<QuizQuestion> {
        return MockData.quizQuestions
    }

    fun getAssignmentConfig(): AssignmentConfig {
        return MockData.initialAssignmentConfig
    }

    fun getUtpStatus(): UtpApprovalState {
        return MockData.utpInitialState
    }

    fun defaultProfiles(): List<UserProfile> {
        // Retornamos algunos perfiles simulados por si la interfaz gráfica antigua los usaba
        return listOf(
            UserProfile("1", "Carmen", "Jefatura UTP", "", "C"),
            UserProfile("2", "Luis", "Docente", "6.° Básico", "L"),
            UserProfile("3", "Ana", "Estudiante", "6.° Básico", "A")
        )
    }
}