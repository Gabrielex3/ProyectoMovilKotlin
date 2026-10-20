package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            KidsTopBar(
                title = "📝 Actividades Pendientes",
                subtitle = "Tus misiones y quizzes",
                color = KidOrange,
                onBack = { onNavigate(AppScreen.STUDENT_HOME) }
            )
        },
        bottomBar = { StudentBottomBar(selected = 2, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            KidsSectionTitle("🔎 Filtrar por asignatura")
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip("🌈 Todos", selectedFilterSubjectId == "ALL") { selectedFilterSubjectId = "ALL" }
                subjects.forEach { subj ->
                    val emoji = kidSubjectStyle(subj.name).emoji
                    FilterChip(
                        "$emoji ${subj.name.split(" ").first()}",
                        selectedFilterSubjectId == subj.id
                    ) { selectedFilterSubjectId = subj.id }
                }
            }

            Spacer(Modifier.height(20.dp))

            KidsSectionTitle("🏁 Evaluaciones y Quizzes")
            Spacer(Modifier.height(10.dp))

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
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable { onClick() },
        shape = RoundedCornerShape(50),
        color = if (isSelected) KidBlue else MaterialTheme.colorScheme.surface,
        border = BorderStroke(2.dp, KidBlue)
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.ExtraBold,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
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
    val style = kidSubjectStyle(activity.subjectName)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(3.dp, style.color),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                KidsChip("${style.emoji} ${activity.subjectName}", style.color, Color.White)
                KidsChip(
                    text = if (isAvailable) "¡Disponible!" else "Completado",
                    bg = if (isAvailable) KidGreen else KidRed,
                    fg = Color.White
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = activity.title,
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (bestScore != null) {
                Spacer(Modifier.height(8.dp))
                KidsChip("⭐ Mejor nota: ${bestScore.first}/${bestScore.second} correctas", KidYellow, KidInk)
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "📅 Vence: ${activity.dueDate}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isAvailable) KidOrange else KidBlue)
            ) {
                Text(
                    text = if (isAvailable) "¡Realizar Quiz! 🚀" else "Ver Retroalimentación ➔",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}