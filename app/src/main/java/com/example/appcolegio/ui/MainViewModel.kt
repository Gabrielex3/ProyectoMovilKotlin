package com.example.appcolegio.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.data.repository.AppRepository
import com.example.appcolegio.data.model.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Inicializamos la base de datos y el repositorio
    private val dbHelper = LocalDatabaseHelper(application)
    private val repository = AppRepository(dbHelper)

    // Función de Login
    fun login(username: String, pass: String): String? {
        return repository.login(username, pass)
    }

    // Funciones que las pantallas (screens)

    fun defaultProfiles(): List<UserProfile> {
        return repository.defaultProfiles()
    }

    fun getInitialQuestions(): List<QuizQuestion> {
        return repository.getInitialQuestions()
    }

    fun getAssignmentConfig(): AssignmentConfig {
        return repository.getAssignmentConfig()
    }

    fun getUtpStatus(): UtpApprovalState {
        return repository.getUtpStatus()
    }

    fun getSubjects(): List<SubjectItem> {
        return repository.getSubjects()
    }

    fun getLearningObjectives(): List<LearningObjective> {
        return repository.getLearningObjectives()
    }
}