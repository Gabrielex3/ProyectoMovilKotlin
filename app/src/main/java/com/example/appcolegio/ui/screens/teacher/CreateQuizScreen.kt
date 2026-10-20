package com.example.appcolegio.ui.screens.teacher

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.QuizQuestion
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.ui.AppScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateQuizScreen(
    subjects: List<SubjectItem>,
    onCreateQuiz: (
        subjectId: String,
        title: String,
        questionText: String,
        optA: String,
        optB: String,
        optC: String,
        optD: String,
        correctIdx: Int
    ) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    var selectedSubjectId by remember { mutableStateOf(subjects.firstOrNull()?.id ?: "SUBJ_1") }
    var quizTitle by remember { mutableStateOf("") }
    var questionText by remember { mutableStateOf("") }
    var optA by remember { mutableStateOf("") }
    var optB by remember { mutableStateOf("") }
    var optC by remember { mutableStateOf("") }
    var optD by remember { mutableStateOf("") }
    var correctIndex by remember { mutableIntStateOf(0) }

    val pendingQuestions = remember { mutableStateListOf<QuizQuestion>() }
    val context = LocalContext.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Creador de quizzes",
                subtitle = "Docente • Múltiples preguntas",
                onBack = { onNavigate(AppScreen.CONTENT_ASSIGNMENT) },
                trailing = {
                    TeacherStatusPill(
                        "${pendingQuestions.size} ${if (pendingQuestions.size == 1) "pregunta" else "preguntas"}",
                        TeacherAccent
                    )
                }
            )
        },
        bottomBar = {
            TeacherActionBar {
                Button(
                    onClick = {
                        if (pendingQuestions.isEmpty()) {
                            Toast.makeText(context, "Agrega al menos una pregunta antes de guardar", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        pendingQuestions.forEach { q ->
                            onCreateQuiz(
                                q.subjectId,
                                q.title,
                                q.contextText,
                                q.options[0].removePrefix("A. "),
                                q.options[1].removePrefix("B. "),
                                q.options.getOrNull(2)?.removePrefix("C. ") ?: "N/A",
                                q.options.getOrNull(3)?.removePrefix("D. ") ?: "N/A",
                                q.correctIndex
                            )
                        }

                        Toast.makeText(context, "🚀 ¡Quiz completo publicado con ${pendingQuestions.size} preguntas!", Toast.LENGTH_LONG).show()
                        onNavigate(AppScreen.CONTENT_ASSIGNMENT)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TeacherAccent)
                ) {
                    Text(
                        "Guardar y publicar quiz (${pendingQuestions.size})",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 1. Asignatura
            TeacherStepTitle(1, "Asignatura")
            Spacer(Modifier.height(10.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                subjects.forEach { subj ->
                    val isSelected = selectedSubjectId == subj.id
                    TeacherCard(
                        modifier = Modifier.fillMaxWidth(),
                        selected = isSelected,
                        onClick = { selectedSubjectId = subj.id }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedSubjectId = subj.id },
                                colors = RadioButtonDefaults.colors(selectedColor = TeacherAccent)
                            )
                            Text(
                                subj.name,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // 2. Datos generales
            TeacherStepTitle(2, "Datos del quiz")
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = quizTitle,
                onValueChange = { quizTitle = it },
                label = { Text("Título de la evaluación") },
                placeholder = { Text("Ej: Evaluación Unidad 2: Ciencias") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = teacherFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // 3. Pregunta
            TeacherStepTitle(3, "Pregunta #${pendingQuestions.size + 1}")
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text("Enunciado") },
                placeholder = { Text("Ej: ¿Qué fuente de energía es renovable?") },
                minLines = 2,
                shape = RoundedCornerShape(12.dp),
                colors = teacherFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(14.dp))

            Text(
                "Alternativas · marca la correcta",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))

            AnswerOptionField("A", optA, { optA = it }, correctIndex == 0) { correctIndex = 0 }
            AnswerOptionField("B", optB, { optB = it }, correctIndex == 1) { correctIndex = 1 }
            AnswerOptionField("C", optC, { optC = it }, correctIndex == 2) { correctIndex = 2 }
            AnswerOptionField("D", optD, { optD = it }, correctIndex == 3) { correctIndex = 3 }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = {
                    if (quizTitle.isBlank() || questionText.isBlank() || optA.isBlank() || optB.isBlank()) {
                        Toast.makeText(context, "Por favor ingresa el título del quiz, la pregunta y al menos A y B", Toast.LENGTH_SHORT).show()
                        return@OutlinedButton
                    }

                    val subjectName = subjects.find { it.id == selectedSubjectId }?.name ?: "Asignatura"
                    val q = QuizQuestion(
                        id = pendingQuestions.size + 100,
                        subjectId = selectedSubjectId,
                        subjectName = subjectName,
                        title = quizTitle.trim(),
                        contextText = questionText.trim(),
                        imageType = "book",
                        options = listOf("A. $optA", "B. $optB", "C. ${if (optC.isBlank()) "N/A" else optC}", "D. ${if (optD.isBlank()) "N/A" else optD}"),
                        correctIndex = correctIndex
                    )
                    pendingQuestions.add(q)
                    Toast.makeText(context, "✅ Pregunta ${pendingQuestions.size} agregada al quiz", Toast.LENGTH_SHORT).show()

                    questionText = ""
                    optA = ""
                    optB = ""
                    optC = ""
                    optD = ""
                    correctIndex = 0
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, TeacherAccent)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, tint = TeacherAccent, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Agregar pregunta al quiz", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TeacherAccent)
            }

            if (pendingQuestions.isNotEmpty()) {
                Spacer(Modifier.height(20.dp))
                TeacherSectionHeader("Preguntas agregadas", "${pendingQuestions.size} en este quiz")
                Spacer(Modifier.height(10.dp))

                TeacherCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        pendingQuestions.forEachIndexed { idx, pq ->
                            Row(verticalAlignment = Alignment.Top) {
                                Box(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(TeacherAccent.copy(alpha = 0.14f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("${idx + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TeacherAccent)
                                }
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    pq.contextText,
                                    fontSize = 13.sp,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun AnswerOptionField(
    letter: String,
    value: String,
    onValueChange: (String) -> Unit,
    isCorrect: Boolean,
    onMarkCorrect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isCorrect,
            onClick = onMarkCorrect,
            colors = RadioButtonDefaults.colors(selectedColor = TeacherAccent)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text("Alternativa $letter") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = teacherFieldColors(),
            modifier = Modifier.weight(1f)
        )
    }
}