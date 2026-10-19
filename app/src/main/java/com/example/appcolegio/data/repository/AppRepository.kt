package com.example.appcolegio.data.repository

import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.data.model.*

class AppRepository(private val dbHelper: LocalDatabaseHelper) {

    fun login(username: String, pass: String): String? {
        return dbHelper.checkUser(username, pass)
    }

    fun getSubjects(): List<SubjectItem> {
        return MockData.subjects
    }

    fun getUnitsForSelectedSubject(subjectId: String): List<UnitItem> {
        return MockData.unitsBySubject[subjectId] ?: emptyList()
    }

    fun getQuestionsForSelectedSubject(subjectId: String): List<QuizQuestion> {
        // Combinar preguntas globales de la asignatura con las de los quizzes de sus unidades
        val list = MockData.quizQuestions.filter { it.subjectId == subjectId }
        val unitList = mutableListOf<QuizQuestion>()
        val units = MockData.unitsBySubject[subjectId]
        if (units != null) {
            for (u in units) {
                for (qItem in u.quizzes) {
                    unitList.addAll(qItem.questions)
                }
            }
        }
        val combined = (list + unitList).distinctBy { it.id }
        return if (combined.isNotEmpty()) combined else MockData.quizQuestions.filter { it.subjectId == "SUBJ_1" }
    }

    fun addQuizQuestion(question: QuizQuestion) {
        MockData.quizQuestions.add(question)
        // Asignar a la unidad de la materia correspondiente
        val units = MockData.unitsBySubject[question.subjectId]
        if (!units.isNullOrEmpty()) {
            val targetUnit = units.lastOrNull() ?: units[0]
            val existingQuiz = targetUnit.quizzes.firstOrNull()
            if (existingQuiz != null) {
                val updatedQuestions = existingQuiz.questions.toMutableList()
                updatedQuestions.add(question)
                val index = targetUnit.quizzes.indexOf(existingQuiz)
                if (index != -1) {
                    targetUnit.quizzes[index] = existingQuiz.copy(questions = updatedQuestions)
                }
            } else {
                targetUnit.quizzes.add(QuizItem("Q_DOC_${System.currentTimeMillis()}", question.title, listOf(question)))
            }
        }
    }

    fun getLearningObjectives(): List<LearningObjective> {
        return MockData.learningObjectives
    }

    fun getInitialQuestions(): List<QuizQuestion> {
        return MockData.quizQuestions
    }

    fun getAssignmentConfig(): AssignmentConfig {
        return dbHelper.getAssignmentConfig()
    }

    fun updateAssignmentConfig(config: AssignmentConfig) {
        MockData.assignmentConfig = config
        dbHelper.updateAssignmentConfig(config)
    }

    fun getUtpStatus(): UtpApprovalState {
        val statusFromDb = dbHelper.getUtpStatus()
        return MockData.utpApprovalState.copy(status = statusFromDb)
    }

    fun updateUtpStatus(newStatus: String) {
        val updatedCriteria = if (newStatus == "AUTORIZADO") {
            MockData.utpApprovalState.criteria.map { it.copy(status = "Conforme") }
        } else {
            MockData.utpApprovalState.criteria
        }
        MockData.utpApprovalState = MockData.utpApprovalState.copy(status = newStatus, criteria = updatedCriteria)
        dbHelper.updateUtpStatus(newStatus)
    }

    fun defaultProfiles(): List<UserProfile> {
        return MockData.profiles
    }
}
