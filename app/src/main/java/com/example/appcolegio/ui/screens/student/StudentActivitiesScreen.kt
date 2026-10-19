package com.example.appcolegio.ui.screens.student

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
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.ui.AppScreen

data class StudentActivityItem(
    val id: String,
    val subjectId: String,
    val subjectName: String,
    val title: String,
    val dueDate: String,
    val priority: String,
    val isCompleted: Boolean = false
)

@Composable
fun StudentActivitiesScreen(
    subjects: List<SubjectItem>,
    bestScores: Map<String, Pair<Int, Int>>,
    canTake: (String) -> Boolean,
    onSelectSubject: (String) -> Unit,
    onSelectQuiz: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    var selectedFilterSubjectId by remember { mutableStateOf("ALL") }

    val dynamicActivities = mutableListOf<StudentActivityItem>()
    MockData.unitsBySubject.forEach { (subjId, units) ->
        val subjName = subjects.find { it.id == subjId }?.name ?: "Asignatura"
        units.forEach { unit ->
            unit.quizzes.forEach { quiz ->
                dynamicActivities.add(
                    StudentActivityItem(
                        id = quiz.id,
                        subjectId = subjId,
                        subjectName = subjName,
                        title = quiz.title,
                        dueDate = "Viernes 9 de octubre - 18:00",
                        priority = "Alta",
                        isCompleted = bestScores.containsKey(quiz.id)
                    )
                )
            }
        }
    }

    MockData.quizQuestions.groupBy { it.subjectId }.forEach { (subjId, questions) ->
        val subjName = subjects.find { it.id == subjId }?.name ?: "Asignatura"
        val firstQ = questions.firstOrNull()
        if (firstQ != null) {
            val qId = "Q_CUSTOM_${firstQ.id}"
            dynamicActivities.add(
                StudentActivityItem(
                    id = qId,
                    subjectId = subjId,
                    subjectName = subjName,
                    title = firstQ.title,
                    dueDate = "Próxima entrega",
                    priority = "Alta",
                    isCompleted = bestScores.containsKey(qId)
                )
            )
        }
    }

    val distinctActivities = dynamicActivities.distinctBy { it.title }

    val filteredActivities = if (selectedFilterSubjectId == "ALL") {
        distinctActivities
    } else {
        distinctActivities.filter { it.subjectId == selectedFilterSubjectId }
    }

    val sortedActivities = filteredActivities.sortedBy {
        when (it.priority) {
            "Alta" -> 1
            "Media" -> 2
            else -> 3
        }
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Spacer(modifier = Modifier.height(28.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onNavigate(AppScreen.STUDENT_HOME) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Actividades Pendientes", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Listado sincronizado con notas y estado", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.STUDENT_HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.SUBJECTS_LIST) },
                    icon = { Icon(Icons.Default.Book, contentDescription = "Asignaturas") },
                    label = { Text("Asignaturas") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.STUDENT_ACTIVITIES) },
                    icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Actividades") },
                    label = { Text("Actividades") }
                )
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
            Text("Filtrar por Ramo / Asignatura:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                FilterChip("Todos", selectedFilterSubjectId == "ALL") { selectedFilterSubjectId = "ALL" }
                subjects.forEach { subj ->
                    FilterChip(subj.name.take(8), selectedFilterSubjectId == subj.id) { selectedFilterSubjectId = subj.id }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Evaluaciones y Quizzes:", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(10.dp))

            if (sortedActivities.isEmpty()) {
                Text("No hay actividades para este filtro.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            } else {
                sortedActivities.forEach { act ->
                    val isAvailable = canTake(act.id)
                    val bestScore = bestScores[act.id]

                    ActivityCard(
                        activity = act,
                        isAvailable = isAvailable,
                        bestScore = bestScore,
                        onAction = {
                            onSelectSubject(act.subjectId)
                            onSelectQuiz(act.id)
                            if (isAvailable) {
                                onNavigate(AppScreen.QUIZ_SESSION)
                            } else {
                                onNavigate(AppScreen.QUIZ_RESULT)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val bg = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = bg,
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFCBD5E1))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = textColor
        )
    }
}

@Composable
fun ActivityCard(
    activity: StudentActivityItem,
    isAvailable: Boolean,
    bestScore: Pair<Int, Int>?,
    onAction: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = Color(0xFFEFF6FF)
                ) {
                    Text(
                        text = activity.subjectName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = Color(0xFF1D4ED8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isAvailable) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                ) {
                    Text(
                        text = if (isAvailable) "Disponible para realizar" else "Intentos agotados (Completado)",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = if (isAvailable) Color(0xFF15803D) else Color(0xFFDC2626),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = activity.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (bestScore != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFEF3C7)
                ) {
                    Text(
                        text = "⭐ Mejor nota obtenida: ${bestScore.first}/${bestScore.second} correctas",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFB45309)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "📅 Vence: ${activity.dueDate}",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Button(
                    onClick = onAction,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAvailable) Color(0xFF2563EB) else Color(0xFFD97706)
                    ),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = if (isAvailable) "Realizar Quiz" else "Ver Retroalimentación ➔",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
