package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.ContentItem
import com.example.appcolegio.data.model.InteractiveItem
import com.example.appcolegio.data.model.QuizItem
import com.example.appcolegio.data.model.UnitItem
import com.example.appcolegio.ui.AppScreen

@Composable
fun UnitDetailScreen(
    units: List<UnitItem>,
    bestScores: Map<String, Pair<Int, Int>>,
    canTake: (String) -> Boolean,
    onSelectQuiz: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    var selectedUnitIndex by remember { mutableStateOf(0) }
    val currentUnit = units.getOrNull(selectedUnitIndex) ?: units.firstOrNull()
    val style = kidSubjectStyle(currentUnit?.subjectName ?: "")

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            KidsTopBar(
                title = "${style.emoji} ${currentUnit?.subjectName ?: "Asignatura"}",
                subtitle = "Unidades de Aprendizaje (${units.size} unidades)",
                color = style.color,
                onBack = { onNavigate(AppScreen.SUBJECTS_LIST) }
            )
        },
        bottomBar = { StudentBottomBar(selected = 1, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            KidsSectionTitle("🗺️ Elige tu nivel")
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                units.forEachIndexed { index, unit ->
                    val isSelected = selectedUnitIndex == index
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(18.dp))
                            .clickable { selectedUnitIndex = index },
                        shape = RoundedCornerShape(18.dp),
                        color = if (isSelected) style.color else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(2.dp, style.color)
                    ) {
                        Text(
                            text = "Unidad ${unit.unitNumber}",
                            modifier = Modifier.padding(vertical = 14.dp),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (currentUnit != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = style.color),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            KidsChip("UNIDAD ${currentUnit.unitNumber}", KidYellow, KidInk)
                            Text(
                                "${currentUnit.progressPercent}% completado",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Spacer(Modifier.height(12.dp))

                        Text(
                            text = currentUnit.unitTitle,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White,
                            lineHeight = 24.sp
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            text = currentUnit.description,
                            fontSize = 13.sp,
                            color = Color.White.copy(alpha = 0.95f),
                            lineHeight = 18.sp
                        )

                        Spacer(Modifier.height(14.dp))

                        KidsProgressBar(
                            progress = currentUnit.progressPercent / 100f,
                            color = KidYellow,
                            track = Color.Black.copy(alpha = 0.2f),
                            height = 12.dp
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))
                KidsSectionTitle("🎯 Objetivos de Aprendizaje (OAs)")
                Spacer(Modifier.height(10.dp))
                currentUnit.learningObjectives.forEach { oa ->
                    OaCard(oaCode = oa.code, text = oa.description)
                    Spacer(Modifier.height(8.dp))
                }

                Spacer(Modifier.height(16.dp))
                KidsSectionTitle("📖 1. Contenidos de Estudio")
                Spacer(Modifier.height(10.dp))
                if (currentUnit.contents.isEmpty()) {
                    Text("No hay contenidos añadidos aún.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    currentUnit.contents.forEach { content ->
                        ExpandableContentCard(content = content)
                        Spacer(Modifier.height(10.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
                KidsSectionTitle("⚡ 2. Contenido Interactivo")
                Spacer(Modifier.height(10.dp))
                if (currentUnit.interactiveContents.isEmpty()) {
                    Text("No hay recursos interactivos aún.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    currentUnit.interactiveContents.forEach { interactive ->
                        InteractiveCard(item = interactive) {
                            onNavigate(AppScreen.DIDACTIC_CONTENT)
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
                KidsSectionTitle("📝 3. Quizzes y Evaluaciones")
                Spacer(Modifier.height(10.dp))
                if (currentUnit.quizzes.isEmpty()) {
                    Text("No hay quizzes disponibles para esta unidad.", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    currentUnit.quizzes.forEach { quiz ->
                        val isAvailable = canTake(quiz.id)
                        val bestScore = bestScores[quiz.id]

                        QuizItemCard(
                            quiz = quiz,
                            isAvailable = isAvailable,
                            bestScore = bestScore,
                            onClick = {
                                onSelectQuiz(quiz.id)
                                if (isAvailable) {
                                    onNavigate(AppScreen.QUIZ_SESSION)
                                } else {
                                    onNavigate(AppScreen.QUIZ_RESULT)
                                }
                            }
                        )
                        Spacer(Modifier.height(10.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OaCard(oaCode: String, text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.5.dp, KidGreen.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            KidsChip(oaCode, KidGreen, Color.White)
            Spacer(Modifier.width(10.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
fun ExpandableContentCard(content: ContentItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(2.dp, KidBlue.copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📖", fontSize = 26.sp)
                Spacer(Modifier.width(10.dp))
                Text(
                    content.title,
                    modifier = Modifier.weight(1f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                KidsChip(if (expanded) "▲ Ocultar" else "▼ Leer", KidBlue, Color.White)
            }
            if (expanded) {
                Spacer(Modifier.height(10.dp))
                Text(content.bodyText, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, lineHeight = 20.sp)
                Spacer(Modifier.height(6.dp))
                Text("⏱️ Lectura estimada: ${content.durationMin} min", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun InteractiveCard(item: InteractiveItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = KidBlue.copy(alpha = 0.12f)),
        border = BorderStroke(2.dp, KidBlue),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("⚡", fontSize = 30.sp)
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = KidBlue)
                Text(item.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            KidsChip("Abrir ➔", KidBlue, Color.White)
        }
    }
}

@Composable
fun QuizItemCard(
    quiz: QuizItem,
    isAvailable: Boolean,
    bestScore: Pair<Int, Int>?,
    onClick: () -> Unit
) {
    val accent = if (isAvailable) KidOrange else Color(0xFF94A3B8)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isAvailable) KidYellow.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = BorderStroke(2.dp, accent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("📝", fontSize = 28.sp)
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(quiz.title, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                    Text("${quiz.questions.size} preguntas de evaluación", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                KidsChip(
                    text = if (isAvailable) "Disponible" else "Sin intentos",
                    bg = if (isAvailable) KidGreen else KidRed,
                    fg = Color.White
                )
            }

            if (bestScore != null) {
                Spacer(Modifier.height(8.dp))
                KidsChip("⭐ Mejor nota: ${bestScore.first}/${bestScore.second} correctas", KidYellow, KidInk)
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isAvailable) KidOrange else KidBlue),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = if (isAvailable) "¡Iniciar Quiz! 🚀" else "Ver Retroalimentación ➔",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
        }
    }
}