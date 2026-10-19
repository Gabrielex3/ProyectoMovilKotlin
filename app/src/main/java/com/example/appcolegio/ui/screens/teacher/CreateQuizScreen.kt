package com.example.appcolegio.ui.screens.teacher

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Spacer(modifier = Modifier.height(28.dp)) // Safe camera punch-hole padding
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onNavigate(AppScreen.CONTENT_ASSIGNMENT) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Creador de Quizzes (Varias Preguntas)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Docente • Generador con Múltiples Preguntas", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("1. Selecciona la Asignatura / Ramo:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                subjects.forEach { subj ->
                    val isSelected = selectedSubjectId == subj.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedSubjectId = subj.id },
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(if (isSelected) 2.dp else 1.dp, if (isSelected) Color(0xFF2563EB) else Color(0xFFE2E8F0)),
                        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFEFF6FF) else MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(subj.name, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color(0xFF1D4ED8) else MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.weight(1f))
                            if (isSelected) Text("✓", color = Color(0xFF1D4ED8), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = quizTitle,
                onValueChange = { quizTitle = it },
                label = { Text("Título General del Quiz / Evaluación") },
                placeholder = { Text("Ej: Evaluación Unidad 2: Ciencias") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text("2. Agregar Pregunta #${pendingQuestions.size + 1}:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = questionText,
                onValueChange = { questionText = it },
                label = { Text("Enunciado de la Pregunta") },
                placeholder = { Text("Ej: ¿Qué fuente de energía es renovable?") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(value = optA, onValueChange = { optA = it }, label = { Text("Alternativa A") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = optB, onValueChange = { optB = it }, label = { Text("Alternativa B") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = optC, onValueChange = { optC = it }, label = { Text("Alternativa C") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = optD, onValueChange = { optD = it }, label = { Text("Alternativa D") }, singleLine = true, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Text("Respuesta Correcta:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("A", "B", "C", "D").forEachIndexed { index, label ->
                    val isSelected = correctIndex == index
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { correctIndex = index },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Color(0xFF2563EB) else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSelected) Color(0xFF2563EB) else Color(0xFFCBD5E1))
                    ) {
                        Text(
                            text = "Opción $label",
                            modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Button to Add Question to Pending List
            Button(
                onClick = {
                    if (quizTitle.isBlank() || questionText.isBlank() || optA.isBlank() || optB.isBlank()) {
                        Toast.makeText(context, "Por favor ingresa el título del quiz, la pregunta y al menos A y B", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val subjectName = subjects.find { it.id == selectedSubjectId }?.name ?: "Asignatura"
                    val q = QuizQuestion(
                        id = pendingQuestions.size + 100,
                        subjectId = selectedSubjectId,
                        subjectName = subjectName,
                        title = quizTitle.trim(),
                        contextText = questionText.trim(),
                        imageType = "book",
                        options = listOf("A. $optA", "B. $optB", "C. ${if(optC.isBlank()) "N/A" else optC}", "D. ${if(optD.isBlank()) "N/A" else optD}"),
                        correctIndex = correctIndex
                    )
                    pendingQuestions.add(q)
                    Toast.makeText(context, "✅ Pregunta ${pendingQuestions.size} agregada al quiz", Toast.LENGTH_SHORT).show()

                    // Reset question fields for next question
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
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text("➕ Agregar otra pregunta al Quiz", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            if (pendingQuestions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("📋 Preguntas acumuladas (${pendingQuestions.size}):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                pendingQuestions.forEachIndexed { idx, pq ->
                    Text("${idx + 1}. ${pq.contextText}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

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
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("🚀 Guardar y Publicar Quiz (${pendingQuestions.size} preguntas)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
