package com.example.appcolegio.data.local

import com.example.appcolegio.data.model.*

object MockData {

    val profiles = listOf(
        UserProfile("antonia", "Antonia Soto", "Estudiante", "6.° A - Estudiante", "AS"),
        UserProfile("camila", "Camila Rojas", "Docente", "Profesora jefa - 6.° A", "CR"),
        UserProfile("maria", "María Vera", "Jefatura UTP", "Jefatura Técnico-pedagógica", "MV")
    )

    val subjects = mutableListOf(
        SubjectItem("SUBJ_1", "Ciencias Naturales", "3 Unidades • Energía y Materia", 44, 0xFF2E7D32),
        SubjectItem("SUBJ_2", "Lenguaje y Comunicación", "3 Unidades • Textos y Narración", 72, 0xFF6A1B9A),
        SubjectItem("SUBJ_3", "Matemática", "3 Unidades • Números y Geometría", 58, 0xFF1565C0),
        SubjectItem("SUBJ_4", "Historia, Geografía y C. S.", "3 Unidades • Sociedad y República", 31, 0xFFD84315)
    )

    // Preguntas base de Ciencias (5 preguntas)
    private val scienceQuestions = listOf(
        QuizQuestion(1, "SUBJ_1", "Ciencias Naturales", "Transformación en una lámpara", "¿Qué transformación de energía ocurre en esta lámpara?", "lamp", listOf("A. Energía química en sonora", "B. Energía eléctrica en luz y calor", "C. Energía solar en movimiento", "D. Energía térmica en electricidad"), 1),
        QuizQuestion(2, "SUBJ_1", "Ciencias Naturales", "Fuente renovable", "¿Cuál de las siguientes corresponde a una fuente de energía renovable?", "sun", listOf("A. Energía solar obtenida del Sol", "B. Carbón mineral refinado", "C. Gas natural para calderas", "D. Combustibles fósiles pesados"), 0),
        QuizQuestion(3, "SUBJ_1", "Ciencias Naturales", "Energía en una tostadora", "En una tostadora de pan, la energía eléctrica se transforma principalmente en:", "toaster", listOf("A. Energía eólica y movimiento", "B. Energía térmica para calentar", "C. Energía química almacenada", "D. Energía magnética estática"), 1),
        QuizQuestion(4, "SUBJ_1", "Ciencias Naturales", "Transferencia de calor", "Cuando dos objetos con distinta temperatura se tocan, el calor se transfiere por:", "heater", listOf("A. Contacto directo entre cuerpos", "B. Proceso de fotosíntesis", "C. Fuerza de gravedad relativa", "D. Combustión interna rápida"), 0),
        QuizQuestion(5, "SUBJ_1", "Ciencias Naturales", "Energía almacenada", "La energía presente en los alimentos y baterías que usa nuestro organismo se denomina:", "battery", listOf("A. Energía sonora de alta frecuencia", "B. Energía solar directa", "C. Energía química almacenada", "D. Energía eólica ambiental"), 2)
    )

    // Preguntas base de Lenguaje (4 preguntas)
    private val languageQuestions = listOf(
        QuizQuestion(6, "SUBJ_2", "Lenguaje y Comunicación", "Propósito del Mito", "¿Cuál es el propósito principal de un relato mítico?", "book", listOf("A. Explicar el origen del universo o de fenómenos sagrados", "B. Entregar instrucciones paso a paso", "C. Informar sobre noticias recientes", "D. Exponer datos científicos"), 0),
        QuizQuestion(7, "SUBJ_2", "Lenguaje y Comunicación", "Diferencia con la Leyenda", "¿Qué elemento caracteriza principalmente a la leyenda?", "book", listOf("A. Se basa en hechos de una localidad transmitidos oralmente", "B. Ocurre siempre antes del nacimiento de los dioses", "C. Contiene únicamente fórmulas matemáticas", "D. Es una noticia del diario oficial"), 0),
        QuizQuestion(8, "SUBJ_2", "Lenguaje y Comunicación", "Figuras Literarias", "¿Qué figura literaria compara dos elementos usando la palabra 'como'?", "book", listOf("A. Símil", "B. Hipérbole", "C. Metáfora", "D. Animación"), 0),
        QuizQuestion(9, "SUBJ_2", "Lenguaje y Comunicación", "Hecho vs Opinión", "¿Cuál de las siguientes frases corresponde a una opinión?", "book", listOf("A. El agua hierve a 100°C", "B. Este libro es el más aburrido del mundo", "C. Santiago es la capital de Chile", "D. El sol sale por el este"), 1)
    )

    // Preguntas base de Matemática (4 preguntas)
    private val mathQuestions = listOf(
        QuizQuestion(10, "SUBJ_3", "Matemática", "Equivalencia Fraccionaria", "¿A qué fracción simplificada equivale el porcentaje 75%?", "math", listOf("A. 1/2", "B. 3/4", "C. 2/5", "D. 4/5"), 1),
        QuizQuestion(11, "SUBJ_3", "Matemática", "Razones y Proporciones", "En la razón 3 : 5, si el antecedente es 12, ¿cuál es el consecuente?", "math", listOf("A. 15", "B. 20", "C. 25", "D. 30"), 1),
        QuizQuestion(12, "SUBJ_3", "Matemática", "Cálculo de Porcentajes", "¿Cuánto es el 50% de 200?", "math", listOf("A. 50", "B. 100", "C. 150", "D. 200"), 1),
        QuizQuestion(13, "SUBJ_3", "Matemática", "Área del Triángulo", "¿Cuál es la fórmula del área del triángulo?", "math", listOf("A. Base por altura", "B. (Base por altura) / 2", "C. Lado por lado", "D. 4 por lado"), 1)
    )

    // Preguntas base de Historia (4 preguntas)
    private val historyQuestions = listOf(
        QuizQuestion(14, "SUBJ_4", "Historia, Geografía y C. S.", "Poderes del Estado", "¿Qué poder del Estado chileno tiene como función principal elaborar las leyes?", "book", listOf("A. Poder Ejecutivo", "B. Poder Judicial", "C. Poder Legislativo (Congreso Nacional)", "D. Corte Suprema"), 2),
        QuizQuestion(15, "SUBJ_4", "Historia, Geografía y C. S.", "Recursos Naturales", "¿Cuál es el principal recurso minero de exportación en Chile?", "book", listOf("A. Carbón", "B. Cobre", "C. Oro", "D. Hierro"), 1),
        QuizQuestion(16, "SUBJ_4", "Historia, Geografía y C. S.", "Zonas Naturales", "¿Qué clima predomina en la Zona Central de Chile?", "book", listOf("A. Tropical lluvioso", "B. Templado mediterráneo", "C. Desértico árido", "D. Polar antártico"), 1)
    )

    val unitsBySubject = mutableMapOf(
        "SUBJ_1" to mutableListOf(
            UnitItem(
                id = "U_CIE_1", subjectId = "SUBJ_1", subjectName = "Ciencias Naturales",
                unitNumber = 1, unitTitle = "La materia y sus transformaciones",
                description = "Estudia los estados de la materia, mezclas y separaciones.",
                progressPercent = 80,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 1", "Investigar experimentalmente los estados de la materia."),
                    LearningObjective("OA 2", "Clasificar sustancias puras y mezclas.")
                ),
                contents = mutableListOf(
                    ContentItem("C1", "Estados de la Materia", "La materia se presenta principalmente en tres estados: sólido, líquido y gaseoso.", 6)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I1", "Simulador de Temperatura", "Observa cómo el calor cambia el estado del agua.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q1", "Evaluación Unidad 1: Materia y Energía", scienceQuestions)
                )
            ),
            UnitItem(
                id = "U_CIE_2", subjectId = "SUBJ_1", subjectName = "Ciencias Naturales",
                unitNumber = 2, unitTitle = "La energía y sus transformaciones",
                description = "Explora cómo la energía cambia, se transfiere y está presente en tu vida.",
                progressPercent = 44,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 8", "Explicar que la energía es necesaria para que los objetos cambien."),
                    LearningObjective("OA 9", "Investigar transformaciones de energía en situaciones simples.")
                ),
                contents = mutableListOf(
                    ContentItem("C3", "Principio de Conservación de la Energía", "La energía no se crea ni se destruye, solo se transforma.", 7)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I3", "Panel Solar Interactivo", "Visualiza la conversión de luz solar en electricidad.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q2", "Evaluación Unidad 2: Transformaciones Energéticas", scienceQuestions)
                )
            ),
            UnitItem(
                id = "U_CIE_3", subjectId = "SUBJ_1", subjectName = "Ciencias Naturales",
                unitNumber = 3, unitTitle = "Ecosistemas y biodiversidad de Chile",
                description = "Analiza las interacciones entre los seres vivos y su entorno en Chile.",
                progressPercent = 20,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 10", "Analizar los efectos de la actividad humana en los ecosistemas."),
                    LearningObjective("OA 11", "Describir las cadenas y tramas alimentarias.")
                ),
                contents = mutableListOf(
                    ContentItem("C5", "Cadenas Tróficas", "Los productores, consumidores y descomponedores forman redes complejas.", 6)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I5", "Red Trófica Interactiva", "Conecta flora y fauna nativa de Chile.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q3", "Evaluación Unidad 3: Ecosistemas", scienceQuestions)
                )
            )
        ),
        "SUBJ_2" to mutableListOf(
            UnitItem(
                id = "U_LEN_1", subjectId = "SUBJ_2", subjectName = "Lenguaje y Comunicación",
                unitNumber = 1, unitTitle = "Relatos que nos unen - Mitos y Leyendas",
                description = "Analiza textos narrativos tradicionales y su impacto cultural.",
                progressPercent = 72,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 3", "Leer y comprender textos literarios tradicionales."),
                    LearningObjective("OA 4", "Analizar aspectos relevantes de las narraciones leídas.")
                ),
                contents = mutableListOf(
                    ContentItem("C6", "El Mito y sus Dioses", "Narraciones fantásticas que explican el origen del mundo.", 6)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I6", "Biblioteca de Mitos", "Explora historias de la mitología chilena.", "audio")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q4", "Evaluación Unidad 1: Mitos, Leyendas y Textos", languageQuestions)
                )
            ),
            UnitItem(
                id = "U_LEN_2", subjectId = "SUBJ_2", subjectName = "Lenguaje y Comunicación",
                unitNumber = 2, unitTitle = "Poesía y figuras literarias",
                description = "Descubre la musicalidad y expresividad del lenguaje poético.",
                progressPercent = 50,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 6", "Leer poesías y analizar rima, verso y estrofa.")
                ),
                contents = mutableListOf(
                    ContentItem("C8", "La Metáfora y el Símil", "Comparaciones estéticas en los poemas.", 5)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I7", "Crea tu Poema", "Taller guiado de escritura en verso.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q5", "Evaluación Unidad 2: Poesía", languageQuestions)
                )
            ),
            UnitItem(
                id = "U_LEN_3", subjectId = "SUBJ_2", subjectName = "Lenguaje y Comunicación",
                unitNumber = 3, unitTitle = "Medios de comunicación y opinión",
                description = "Analiza noticias y textos argumentativos.",
                progressPercent = 30,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 11", "Distinguir hechos de opiniones en la prensa.")
                ),
                contents = mutableListOf(
                    ContentItem("C9", "Hecho vs Opinión", "Información verificable versus puntos de vista.", 6)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I8", "Verificador de Noticias", "Ejercicios prácticos periodísticos.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q6", "Evaluación Unidad 3: Medios", languageQuestions)
                )
            )
        ),
        "SUBJ_3" to mutableListOf(
            UnitItem(
                id = "U_MAT_1", subjectId = "SUBJ_3", subjectName = "Matemática",
                unitNumber = 1, unitTitle = "Fracciones y razones",
                description = "Operaciones con fracciones y cálculo de razones y proporciones.",
                progressPercent = 58,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 5", "Comprender fracciones y números mixtos."),
                    LearningObjective("OA 6", "Comprender la razón entre dos magnitudes.")
                ),
                contents = mutableListOf(
                    ContentItem("C10", "Suma y Resta de Fracciones", "Homogeneizar denominadores.", 8)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I9", "Partidor de Pizzas", "Fracciones gráficas.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q7", "Evaluación Unidad 1: Fracciones y Razones", mathQuestions)
                )
            ),
            UnitItem(
                id = "U_MAT_2", subjectId = "SUBJ_3", subjectName = "Matemática",
                unitNumber = 2, unitTitle = "Razones y porcentaje aplicado",
                description = "Calcula descuentos e IVA.",
                progressPercent = 40,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 7", "Resolver problemas con porcentajes.")
                ),
                contents = mutableListOf(
                    ContentItem("C11", "Cálculo de Porcentajes", "Cálculo de porcentajes aplicados.", 7)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I10", "Supermercado Matemático", "Calcula descuentos reales.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q8", "Evaluación Unidad 2: Porcentajes", mathQuestions)
                )
            ),
            UnitItem(
                id = "U_MAT_3", subjectId = "SUBJ_3", subjectName = "Matemática",
                unitNumber = 3, unitTitle = "Geometría: Área y perímetro",
                description = "Área de triángulos y paralelogramos.",
                progressPercent = 25,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 16", "Calcular áreas de figuras geométricas.")
                ),
                contents = mutableListOf(
                    ContentItem("C12", "Área del Triángulo", "Base por altura dividido por dos.", 6)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I11", "Geoplano Virtual", "Cálculo de áreas en cuadrículas.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q9", "Evaluación Unidad 3: Geometría", mathQuestions)
                )
            )
        ),
        "SUBJ_4" to mutableListOf(
            UnitItem(
                id = "U_HIS_1", subjectId = "SUBJ_4", subjectName = "Historia, Geografía y C. S.",
                unitNumber = 1, unitTitle = "Chile republicano e instituciones",
                description = "Organización política y poderes del Estado.",
                progressPercent = 31,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 11", "Organización política de Chile."),
                    LearningObjective("OA 12", "Derechos y deberes ciudadanos.")
                ),
                contents = mutableListOf(
                    ContentItem("C13", "Poderes del Estado", "Ejecutivo, Legislativo y Judicial.", 7)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I12", "Organigrama de la República", "Estructura del Estado chileno.", "diagram")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q10", "Evaluación Unidad 1: República e Instituciones", historyQuestions)
                )
            ),
            UnitItem(
                id = "U_HIS_2", subjectId = "SUBJ_4", subjectName = "Historia, Geografía y C. S.",
                unitNumber = 2, unitTitle = "La economía y sociedad chilena",
                description = "Recursos naturales de Chile.",
                progressPercent = 15,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 15", "Recursos naturales y su distribución.")
                ),
                contents = mutableListOf(
                    ContentItem("C14", "Minería y Agricultura", "Cobre y recursos renovables.", 6)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I13", "Mapa Económico", "Riquezas regionales de Chile.", "diagram")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q11", "Evaluación Unidad 2: Economía", historyQuestions)
                )
            ),
            UnitItem(
                id = "U_HIS_3", subjectId = "SUBJ_4", subjectName = "Historia, Geografía y C. S.",
                unitNumber = 3, unitTitle = "Geografía de Chile y regiones",
                description = "Zonas naturales de Chile.",
                progressPercent = 10,
                learningObjectives = mutableListOf(
                    LearningObjective("OA 19", "Grandes zonas naturales de Chile.")
                ),
                contents = mutableListOf(
                    ContentItem("C15", "Zonas Naturales", "Clima y relieve desde el norte a la patagonia.", 8)
                ),
                interactiveContents = mutableListOf(
                    InteractiveItem("I14", "Viaje por Chile", "Paisajes y regiones.", "simulation")
                ),
                quizzes = mutableListOf(
                    QuizItem("Q12", "Evaluación Unidad 3: Geografía", historyQuestions)
                )
            )
        )
    )

    val learningObjectives = mutableListOf(
        LearningObjective("OA 1", "Investigar experimentalmente los estados de la materia."),
        LearningObjective("OA 8", "Explicar la energía y sus transformaciones en la vida cotidiana."),
        LearningObjective("OA 3", "Leer y compreender textos literarios tradicionales."),
        LearningObjective("OA 5", "Demostrar que comprenden las fracciones y números mixtos."),
        LearningObjective("OA 11", "Explicar los principales elementos de la organización política de Chile.")
    )

    val quizQuestions = mutableListOf<QuizQuestion>().apply {
        addAll(scienceQuestions)
        addAll(languageQuestions)
        addAll(mathQuestions)
        addAll(historyQuestions)
    }

    var assignmentConfig = AssignmentConfig(
        course = "6.° A - 34 estudiantes",
        dueDate = "Viernes 9 de octubre - 18:00",
        maxAttempts = 3,
        showCorrection = true,
        isPublished = true
    )

    var utpApprovalState = UtpApprovalState(
        status = "AUTORIZADO",
        sender = "Camila Rojas",
        timeStr = "Hoy, 10:24",
        criteria = listOf(
            UtpCriterion("C1", "Alineación curricular", "OA 9 - Ciencias Naturales 6.º básico", "Conforme"),
            UtpCriterion("C2", "Claridad y edad apropiada", "Lenguaje adecuado para 11-12 años", "Conforme"),
            UtpCriterion("C3", "Accesibilidad del recurso", "Audio, contraste y apoyo visual disponible", "Conforme"),
            UtpCriterion("C4", "Actividad y retroalimentación", "5 alternativas - 3 intentos - corrección visible", "Conforme")
        )
    )
}
