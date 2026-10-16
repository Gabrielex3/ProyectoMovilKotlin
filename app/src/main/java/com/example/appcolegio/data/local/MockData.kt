package com.example.appcolegio.data.local

import com.example.appcolegio.data.model.*

object MockData {

    // 1. Asignaturas y Unidades
    val subjects = listOf(
        SubjectItem(
            id = "SUBJ_1",
            name = "Ciencias Naturales",
            subtitle = "Unidad 1: Capas de la Tierra",
            progressPercent = 0,
            colorHex = 0xFF4CAF50 // Verde
        ),
        SubjectItem(
            id = "SUBJ_2",
            name = "Matemática",
            subtitle = "Unidad 1: Fracciones y Porcentajes",
            progressPercent = 0,
            colorHex = 0xFF2196F3 // Azul
        ),
        SubjectItem(
            id = "SUBJ_3",
            name = "Lenguaje",
            subtitle = "Unidad 1: Mitos y Leyendas",
            progressPercent = 0,
            colorHex = 0xFFFF9800 // Naranja
        ),
        SubjectItem(
            id = "SUBJ_4",
            name = "Historia",
            subtitle = "Unidad 1: República de Chile",
            progressPercent = 0,
            colorHex = 0xFFE91E63 // Rosa
        )
    )

    // 2. Objetivos de Aprendizaje
    val learningObjectives = listOf(
        LearningObjective(
            code = "OA 16",
            description = "Describir las características de las capas de la Tierra (atmósfera, litósfera e hidrósfera).",
            achievedPercent = 75,
            logradoCount = 20,
            enProcesoCount = 10,
            sinEvidenciaCount = 4
        ),
        LearningObjective(
            code = "OA 3",
            description = "Demostrar que comprenden el concepto de porcentaje.",
            achievedPercent = 60,
            logradoCount = 15,
            enProcesoCount = 15,
            sinEvidenciaCount = 4
        )
    )

    // 3. Preguntas de la Prueba
    val quizQuestions = listOf(
        QuizQuestion(
            id = 1,
            title = "Pregunta 1",
            contextText = "¿Cuál es la capa de la Tierra compuesta principalmente por agua?",
            imageType = "lamp",
            options = listOf("Atmósfera", "Litósfera", "Hidrósfera", "Núcleo"),
            correctIndex = 2
        ),
        QuizQuestion(
            id = 2,
            title = "Pregunta 2",
            contextText = "¿Qué gas es el más abundante en la atmósfera terrestre?",
            imageType = "sun",
            options = listOf("Oxígeno", "Nitrógeno", "Dióxido de Carbono", "Hidrógeno"),
            correctIndex = 1
        )
    )

    // 4. Configuración
    val initialAssignmentConfig = AssignmentConfig(
        course = "6.° Básico - 34 estudiantes",
        dueDate = "Viernes 30 de octubre - 18:00",
        maxAttempts = 3,
        showCorrection = true,
        isPublished = false
    )

    // 5. Estado inicial de revisión para UTP
    val utpInitialState = UtpApprovalState(
        status = "PENDIENTE",
        sender = "Profesor Luis",
        timeStr = "Hoy, 10:24",
        criteria = listOf(
            UtpCriterion("C1", "Alineación Curricular", "OA 16 y contenidos", "Conforme"),
            UtpCriterion("C2", "Dificultad de la Prueba", "2 preguntas, 4 alternativas", "Revisar")
        )
    )
}