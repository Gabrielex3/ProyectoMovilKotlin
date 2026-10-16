package com.example.appcolegio.data.repository

import android.content.Context
import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.data.model.AssignmentConfig
import com.example.appcolegio.data.model.LearningObjective
import com.example.appcolegio.data.model.QuizQuestion
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.data.model.UtpCriterion

class AppRepository(context: Context) {

    private val dbHelper = LocalDatabaseHelper(context)

    val defaultProfiles = listOf(
        UserProfile("antonia", "Antonia Soto", "Estudiante", "6.° A - Estudiante", "AS"),
        UserProfile("camila", "Camila Rojas", "Docente", "Profesora jefa - 6.° A", "CR"),
        UserProfile("maria", "María Vera", "Jefatura UTP", "Jefatura Técnico-pedagógica", "MV")
    )

    val defaultSubjects = listOf(
        SubjectItem("lenguaje", "Lenguaje", "Relatos que nos unen", 72, 0xFF6A1B9A),
        SubjectItem("matematica", "Matemática", "Fracciones y razones", 58, 0xFF1565C0),
        SubjectItem("ciencias", "Ciencias", "La energía", 44, 0xFF2E7D32),
        SubjectItem("historia", "Historia", "Chile republicano", 31, 0xFFD84315)
    )

    val defaultObjectives = listOf(
        LearningObjective(
            code = "OA 8",
            description = "Explicar que la energía es necesaria para que los objetos cambien y los seres vivos realicen procesos vitales.",
            achievedPercent = 74,
            logradoCount = 25,
            enProcesoCount = 7,
            sinEvidenciaCount = 2
        ),
        LearningObjective(
            code = "OA 9",
            description = "Investigar transformaciones de energía en situaciones simples del entorno.",
            achievedPercent = 56,
            logradoCount = 18,
            enProcesoCount = 10,
            sinEvidenciaCount = 6
        )
    )

    fun getInitialQuestions(): List<QuizQuestion> = listOf(
        QuizQuestion(
            id = 1,
            title = "Transformación en una lámpara",
            contextText = "¿Qué transformación de energía ocurre en esta lámpara?",
            imageType = "lamp",
            options = listOf(
                "A.  Energía química en energía sonora",
                "B.  Energía eléctrica en luz y calor",
                "C.  Energía solar en movimiento",
                "D.  Energía térmica en electricidad"
            ),
            correctIndex = 1, // B
            userSelectedIndex = 1 // Default matching mockup
        ),
        QuizQuestion(
            id = 2,
            title = "Fuente renovable",
            contextText = "¿Cuál de las siguientes corresponde a una fuente de energía renovable?",
            imageType = "sun",
            options = listOf(
                "A.  Energía solar obtenida del Sol",
                "B.  Carbón mineral refinado",
                "C.  Gas natural para calderas",
                "D.  Combustibles fósiles pesados"
            ),
            correctIndex = 0, // A
            userSelectedIndex = 0
        ),
        QuizQuestion(
            id = 3,
            title = "Energía en una tostadora",
            contextText = "En una tostadora de pan, la energía eléctrica se transforma principalmente en:",
            imageType = "toaster",
            options = listOf(
                "A.  Energía eólica y movimiento",
                "B.  Energía térmica para calentar",
                "C.  Energía química almacenada",
                "D.  Energía magnética estática"
            ),
            correctIndex = 1, // B
            userSelectedIndex = 0 // Mockup shows wrong answer selected (selected 0 instead of 1)
        ),
        QuizQuestion(
            id = 4,
            title = "Transferencia de calor",
            contextText = "Cuando dos objetos con distinta temperatura se tocan, el calor se transfiere por:",
            imageType = "heater",
            options = listOf(
                "A.  Contacto directo entre cuerpos",
                "B.  Proceso de fotosíntesis",
                "C.  Fuerza de gravedad relativa",
                "D.  Combustión interna rápida"
            ),
            correctIndex = 0, // A
            userSelectedIndex = 0
        ),
        QuizQuestion(
            id = 5,
            title = "Energía almacenada",
            contextText = "La energía presente en los alimentos y baterías que usa nuestro organismo se denomina:",
            imageType = "battery",
            options = listOf(
                "A.  Energía sonora de alta frecuencia",
                "B.  Energía solar directa",
                "C.  Energía química almacenada",
                "D.  Energía eólica ambiental"
            ),
            correctIndex = 2, // C
            userSelectedIndex = 0 // Mockup shows wrong answer selected
        )
    )

    fun getAssignmentConfig(): AssignmentConfig = dbHelper.getAssignmentConfig()

    fun updateAssignmentConfig(config: AssignmentConfig) {
        dbHelper.updateAssignmentConfig(config)
    }

    fun getUtpStatus(): String = dbHelper.getUtpStatus()

    fun updateUtpStatus(status: String) {
        dbHelper.updateUtpStatus(status)
    }

    fun saveQuizAttempt(score: Int, total: Int, attemptsLeft: Int, answersStr: String) {
        dbHelper.saveQuizAttempt(score, total, attemptsLeft, answersStr)
    }

    fun getLastQuizAttempt(): Pair<Int, Int>? = dbHelper.getLastQuizAttempt()

    fun getUtpCriteria(): List<UtpCriterion> = listOf(
        UtpCriterion("1", "Alineación curricular", "OA 9 - Ciencias Naturales 6.º básico", "Conforme"),
        UtpCriterion("2", "Claridad y edad apropiada", "Lenguaje adecuado para 11-12 años", "Conforme"),
        UtpCriterion("3", "Accesibilidad del recurso", "Audio, contraste y apoyo visual disponible", "Conforme"),
        UtpCriterion("4", "Actividad y retroalimentación", "5 alternativas - 3 intentos - corrección visible", "Revisar")
    )
}
