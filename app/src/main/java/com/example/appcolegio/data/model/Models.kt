package com.example.appcolegio.data.model

data class UserProfile(
    val id: String,
    val name: String,
    val role: String, // "Estudiante", "Docente", "Jefatura UTP"
    val courseInfo: String,
    val avatarLetter: String,
    val isDemo: Boolean = false
)

data class SubjectItem(
    val id: String,
    val name: String,
    val subtitle: String,
    val progressPercent: Int,
    val colorHex: Long
)

data class ContentItem(
    val id: String,
    val title: String,
    val bodyText: String,
    val durationMin: Int = 5
)

data class InteractiveItem(
    val id: String,
    val title: String,
    val description: String,
    val type: String = "diagram" // "diagram", "audio", "simulation"
)

data class QuizItem(
    val id: String,
    val title: String,
    val questions: List<QuizQuestion>
)

data class UnitItem(
    val id: String,
    val subjectId: String,
    val subjectName: String,
    val unitNumber: Int,
    val unitTitle: String,
    val description: String,
    val progressPercent: Int,
    val isAuthorizedByUtp: Boolean = true, // Por defecto las preexistentes están autorizadas
    val learningObjectives: MutableList<LearningObjective> = mutableListOf(),
    val contents: MutableList<ContentItem> = mutableListOf(),
    val interactiveContents: MutableList<InteractiveItem> = mutableListOf(),
    val quizzes: MutableList<QuizItem> = mutableListOf()
)

data class LearningObjective(
    val code: String,
    val description: String,
    val achievedPercent: Int = 70,
    val logradoCount: Int = 20,
    val enProcesoCount: Int = 10,
    val sinEvidenciaCount: Int = 4
)

data class QuizQuestion(
    val id: Int,
    val subjectId: String = "SUBJ_1",
    val subjectName: String = "Ciencias Naturales",
    val title: String,
    val contextText: String,
    val imageType: String = "lamp",
    val options: List<String>,
    val correctIndex: Int,
    val userSelectedIndex: Int? = null
)

data class QuestionResult(
    val id: Int,
    val title: String,
    val subtitle: String,
    val isCorrect: Boolean
)

data class AssignmentConfig(
    val course: String = "6.° A - 34 estudiantes",
    val dueDate: String = "Viernes 9 de octubre - 18:00",
    val maxAttempts: Int = 3,
    val showCorrection: Boolean = true,
    val isPublished: Boolean = false
)

data class UtpCriterion(
    val id: String,
    val title: String,
    val subtitle: String,
    val status: String // "Conforme", "Revisar"
)

data class UtpApprovalState(
    val status: String, // "PENDIENTE", "AUTORIZADO", "CAMBIOS_SOLICITADOS"
    val sender: String = "Camila Rojas",
    val timeStr: String = "Hoy, 10:24",
    val criteria: List<UtpCriterion>
)
