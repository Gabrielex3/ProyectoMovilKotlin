package com.example.appcolegio.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.appcolegio.data.model.AssignmentConfig
import com.example.appcolegio.data.model.QuizQuestion
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.data.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AppScreen {
    PROFILE_SELECTION,    // Mockup 1
    STUDENT_HOME,         // Mockup 2
    UNIT_DETAIL,          // Mockup 3
    DIDACTIC_CONTENT,     // Mockup 4
    QUIZ_ACTIVITY,        // Mockup 5
    QUIZ_RESULT,          // Mockup 6
    TEACHER_HOME,         // Mockup 7
    CONTENT_ASSIGNMENT,   // Mockup 8
    PEDAGOGICAL_REPORT,   // Mockup 9
    UTP_REVIEW            // Mockup 10
}

data class AppUiState(
    val currentScreen: AppScreen = AppScreen.PROFILE_SELECTION,
    val selectedUser: UserProfile? = null,
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val attemptsLeft: Int = 2,
    val quizScore: Int = 3,
    val totalQuestions: Int = 5,
    val assignmentConfig: AssignmentConfig = AssignmentConfig(),
    val utpStatus: String = "PENDIENTE"
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application.applicationContext)

    private val _uiState = MutableStateFlow(
        AppUiState(
            selectedUser = repository.defaultProfiles.first(),
            questions = repository.getInitialQuestions(),
            assignmentConfig = repository.getAssignmentConfig(),
            utpStatus = repository.getUtpStatus()
        )
    )
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    fun selectUserAndNavigate(user: UserProfile) {
        val initialScreen = when (user.role) {
            "Estudiante" -> AppScreen.STUDENT_HOME
            "Docente" -> AppScreen.TEACHER_HOME
            "Jefatura UTP" -> AppScreen.UTP_REVIEW
            else -> AppScreen.STUDENT_HOME
        }
        _uiState.update {
            it.copy(
                selectedUser = user,
                currentScreen = initialScreen
            )
        }
    }

    fun navigateTo(screen: AppScreen) {
        _uiState.update { it.copy(currentScreen = screen) }
    }

    fun selectQuestionOption(optionIndex: Int) {
        _uiState.update { it.copy(selectedOptionIndex = optionIndex) }
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        val nextIndex = currentState.currentQuestionIndex + 1
        if (nextIndex < currentState.questions.size) {
            _uiState.update {
                it.copy(
                    currentQuestionIndex = nextIndex,
                    selectedOptionIndex = it.questions[nextIndex].userSelectedIndex
                )
            }
        } else {
            // Finished quiz -> Go to results screen
            _uiState.update { it.copy(currentScreen = AppScreen.QUIZ_RESULT) }
        }
    }

    fun retryQuiz() {
        if (_uiState.value.attemptsLeft > 0) {
            val newAttempts = _uiState.value.attemptsLeft - 1
            _uiState.update {
                it.copy(
                    attemptsLeft = newAttempts,
                    currentQuestionIndex = 0,
                    selectedOptionIndex = null,
                    currentScreen = AppScreen.QUIZ_ACTIVITY
                )
            }
            repository.saveQuizAttempt(
                score = _uiState.value.quizScore,
                total = _uiState.value.totalQuestions,
                attemptsLeft = newAttempts,
                answersStr = "1:B|2:A|3:A|4:A|5:A"
            )
        }
    }

    fun updateAssignmentConfig(config: AssignmentConfig) {
        repository.updateAssignmentConfig(config)
        _uiState.update { it.copy(assignmentConfig = config) }
    }

    fun updateUtpStatus(newStatus: String) {
        repository.updateUtpStatus(newStatus)
        _uiState.update { it.copy(utpStatus = newStatus) }
    }

    fun getRepository(): AppRepository = repository
}
