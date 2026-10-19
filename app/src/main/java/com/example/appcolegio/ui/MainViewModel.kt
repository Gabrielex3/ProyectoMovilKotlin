package com.example.appcolegio.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.data.model.*
import com.example.appcolegio.data.repository.AppRepository
import com.example.appcolegio.ui.theme.ThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dbHelper = LocalDatabaseHelper(application)
    private val repository = AppRepository(dbHelper)

    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode: StateFlow<ThemeMode> = _themeMode.asStateFlow()

    private val _selectedSubjectId = MutableStateFlow("SUBJ_1")
    val selectedSubjectId: StateFlow<String> = _selectedSubjectId.asStateFlow()

    private val _currentQuizId = MutableStateFlow("DEFAULT_QUIZ")
    val currentQuizId: StateFlow<String> = _currentQuizId.asStateFlow()

    private val _currentUsername = MutableStateFlow("alumno1")
    val currentUsername: StateFlow<String> = _currentUsername.asStateFlow()

    private val _utpStatusState = MutableStateFlow(repository.getUtpStatus())
    val utpStatusState: StateFlow<UtpApprovalState> = _utpStatusState.asStateFlow()

    private val _assignmentConfigState = MutableStateFlow(repository.getAssignmentConfig())
    val assignmentConfigState: StateFlow<AssignmentConfig> = _assignmentConfigState.asStateFlow()

    private val _quizResultScore = MutableStateFlow(3)
    val quizResultScore: StateFlow<Int> = _quizResultScore.asStateFlow()

    private val _quizTotalQuestions = MutableStateFlow(5)
    val quizTotalQuestions: StateFlow<Int> = _quizTotalQuestions.asStateFlow()

    // Intentos y notas por estudiante (key: "username_quizId")
    private val _userAttemptsMap = MutableStateFlow<Map<String, Int>>(emptyMap())
    val userAttemptsMap: StateFlow<Map<String, Int>> = _userAttemptsMap.asStateFlow()

    private val _userScoresMap = MutableStateFlow<Map<String, Pair<Int, Int>>>(emptyMap())
    val userScoresMap: StateFlow<Map<String, Pair<Int, Int>>> = _userScoresMap.asStateFlow()

    private val _answeredQuestionsState = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val answeredQuestionsState: StateFlow<List<QuizQuestion>> = _answeredQuestionsState.asStateFlow()

    fun setThemeMode(mode: ThemeMode) {
        _themeMode.value = mode
    }

    fun login(username: String, pass: String): String? {
        val role = repository.login(username, pass)
        if (role != null) {
            _currentUsername.value = username
        }
        return role
    }

    fun getCurrentStudentProfile(): UserProfile {
        val uname = _currentUsername.value
        val name = when (uname) {
            "alumno2" -> "Benjamín Silva"
            "alumno3" -> "Sofía Morales"
            else -> "Antonia Soto"
        }
        return UserProfile(uname, name, "Estudiante", "6.° A - Estudiante", name.take(2).uppercase())
    }

    fun getCurrentTeacherProfile(): UserProfile {
        val uname = _currentUsername.value
        val name = if (uname == "profe2") "Carlos Mendoza" else "Camila Rojas"
        return UserProfile(uname, name, "Docente", "Profesora jefa - 6.° A", name.take(2).uppercase())
    }

    fun selectSubject(subjectId: String) {
        _selectedSubjectId.value = subjectId
    }

    fun setCurrentQuizId(quizId: String) {
        _currentQuizId.value = quizId
    }

    fun getUnitForSelectedSubject(): List<UnitItem> {
        return repository.getUnitsForSelectedSubject(_selectedSubjectId.value)
    }

    fun getQuestionsForSelectedSubject(): List<QuizQuestion> {
        return repository.getQuestionsForSelectedSubject(_selectedSubjectId.value)
    }

    private fun getKey(quizId: String): String {
        return "${_currentUsername.value}_$quizId"
    }

    fun getBestScoresForCurrentUser(): Map<String, Pair<Int, Int>> {
        val prefix = "${_currentUsername.value}_"
        return _userScoresMap.value
            .filterKeys { it.startsWith(prefix) }
            .mapKeys { it.key.removePrefix(prefix) }
    }

    fun getAttemptsLeftForQuiz(quizId: String): Int {
        val max = _assignmentConfigState.value.maxAttempts
        if (max > 10) return 99 // Sin límite
        val key = getKey(quizId)
        val used = _userAttemptsMap.value[key] ?: 0
        return maxOf(0, max - used)
    }

    fun recordQuizCompletion(score: Int, total: Int, answeredList: List<QuizQuestion>) {
        val qId = _currentQuizId.value
        val key = getKey(qId)
        _quizResultScore.value = score
        _quizTotalQuestions.value = total
        _answeredQuestionsState.value = answeredList

        val currentUsed = _userAttemptsMap.value[key] ?: 0
        _userAttemptsMap.value = _userAttemptsMap.value + (key to (currentUsed + 1))

        val currentBest = _userScoresMap.value[key]
        val bestScore = if (currentBest != null) maxOf(currentBest.first, score) else score
        _userScoresMap.value = _userScoresMap.value + (key to (bestScore to total))

        // Sincronizar progreso del estudiante
        val currentSubjId = _selectedSubjectId.value
        val percent = if (total > 0) (bestScore * 100) / total else 0

        val subjIndex = MockData.subjects.indexOfFirst { it.id == currentSubjId }
        if (subjIndex != -1) {
            val old = MockData.subjects[subjIndex]
            MockData.subjects[subjIndex] = old.copy(progressPercent = maxOf(old.progressPercent, percent))
        }

        val units = MockData.unitsBySubject[currentSubjId]
        if (!units.isNullOrEmpty()) {
            val firstU = units[0]
            units[0] = firstU.copy(progressPercent = maxOf(firstU.progressPercent, percent))
        }
    }

    fun setQuizResult(score: Int, total: Int, answeredList: List<QuizQuestion>) {
        recordQuizCompletion(score, total, answeredList)
    }

    fun getAttemptsLeft(): Int {
        return getAttemptsLeftForQuiz(_currentQuizId.value)
    }

    fun canTakeQuiz(): Boolean {
        return getAttemptsLeft() > 0
    }

    fun resetAttempts() {
        val prefix = "${_currentUsername.value}_"
        _userAttemptsMap.value = _userAttemptsMap.value.filterKeys { !it.startsWith(prefix) }
    }

    fun createQuizQuestion(
        subjectId: String,
        title: String,
        questionText: String,
        optionA: String,
        optionB: String,
        optionC: String,
        optionD: String,
        correctIndex: Int
    ) {
        val subjectName = repository.getSubjects().find { it.id == subjectId }?.name ?: "Ciencias Naturales"
        val newQuestion = QuizQuestion(
            id = repository.getInitialQuestions().size + 1,
            subjectId = subjectId,
            subjectName = subjectName,
            title = title,
            contextText = questionText,
            imageType = "book",
            options = listOf("A. $optionA", "B. $optionB", "C. $optionC", "D. $optionD"),
            correctIndex = correctIndex
        )
        repository.addQuizQuestion(newQuestion)
    }

    fun defaultProfiles(): List<UserProfile> {
        return repository.defaultProfiles()
    }

    fun getInitialQuestions(): List<QuizQuestion> {
        return repository.getInitialQuestions()
    }

    fun getAssignmentConfig(): AssignmentConfig {
        return _assignmentConfigState.value
    }

    fun updateAssignmentConfig(config: AssignmentConfig) {
        repository.updateAssignmentConfig(config)
        _assignmentConfigState.value = repository.getAssignmentConfig()
    }

    fun getUtpStatus(): UtpApprovalState {
        return _utpStatusState.value
    }

    fun updateUtpStatus(newStatus: String) {
        repository.updateUtpStatus(newStatus)
        _utpStatusState.value = repository.getUtpStatus()
    }

    fun getSubjects(): List<SubjectItem> {
        return repository.getSubjects()
    }

    fun getLearningObjectives(): List<LearningObjective> {
        return repository.getLearningObjectives()
    }
}
