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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                    IconButton(onClick = { onNavigate(AppScreen.SUBJECTS_LIST) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(currentUnit?.subjectName ?: "Asignatura", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Unidades de Aprendizaje (3 Unidades)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFDBEAFE),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Estudiante",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = Color(0xFF1E40AF),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
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
                    selected = true,
                    onClick = { onNavigate(AppScreen.SUBJECTS_LIST) },
                    icon = { Icon(Icons.Default.Book, contentDescription = "Asignaturas") },
                    label = { Text("Asignaturas") }
                )
                NavigationBarItem(
                    selected = false,
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
            Text("Selecciona una Unidad:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                units.forEachIndexed { index, unit ->
                    val isSelected = selectedUnitIndex == index
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedUnitIndex = index },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) Color(0xFF059669) else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSelected) Color(0xFF059669) else Color(0xFFCBD5E1))
                    ) {
                        Text(
                            text = "Unidad ${unit.unitNumber}",
                            modifier = Modifier.padding(vertical = 10.dp),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (currentUnit != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF059669))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFF047857)
                            ) {
                                Text(
                                    text = "UNIDAD ${currentUnit.unitNumber}",
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Text("${currentUnit.progressPercent}% completado", color = Color(0xFFD1FAE5), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = currentUnit.unitTitle,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = currentUnit.description,
                            fontSize = 12.sp,
                            color = Color(0xFFECFDF5),
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        LinearProgressIndicator(
                            progress = { currentUnit.progressPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = Color(0xFF34D399),
                            trackColor = Color(0xFF047857)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("🎯 Objetivos de Aprendizaje (OAs)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                currentUnit.learningObjectives.forEach { oa ->
                    OaCard(oaCode = oa.code, text = oa.description)
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("📖 1. Contenidos de Estudio", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                if (currentUnit.contents.isEmpty()) {
                    Text("No hay contenidos añadidos aún.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    currentUnit.contents.forEach { content ->
                        ExpandableContentCard(content = content)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("⚡ 2. Contenido Interactivo", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                if (currentUnit.interactiveContents.isEmpty()) {
                    Text("No hay recursos interactivos aún.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    currentUnit.interactiveContents.forEach { interactive ->
                        InteractiveCard(item = interactive) {
                            onNavigate(AppScreen.DIDACTIC_CONTENT)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text("📝 3. Quizzes y Evaluaciones", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(8.dp))
                if (currentUnit.quizzes.isEmpty()) {
                    Text("No hay quizzes disponibles para esta unidad.", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OaCard(oaCode: String, text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFFD1FAE5)
            ) {
                Text(
                    text = oaCode,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                    color = Color(0xFF047857),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
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
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(content.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(if (expanded) "▲ Ocultar" else "▼ Leer", fontSize = 11.sp, color = Color(0xFF2563EB), fontWeight = FontWeight.Bold)
            }
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(content.bodyText, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("⏱️ Lectura estimada: ${content.durationMin} min", fontSize = 10.sp, color = Color(0xFF64748B))
            }
        }
    }
}

@Composable
fun InteractiveCard(item: InteractiveItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("⚡ ${item.title}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
                Text(item.description, fontSize = 11.sp, color = Color(0xFF3B82F6))
            }
            Text("Abrir ➔", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
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
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isAvailable) Color(0xFFFEF3C7) else Color(0xFFF3F4F6))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("📝 ${quiz.title}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (isAvailable) Color(0xFF92400E) else Color(0xFF4B5563))
                    Text("${quiz.questions.size} preguntas de evaluación", fontSize = 11.sp, color = if (isAvailable) Color(0xFFB45309) else Color(0xFF6B7280))
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isAvailable) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                ) {
                    Text(
                        text = if (isAvailable) "Disponible" else "Intentos agotados",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isAvailable) Color(0xFF15803D) else Color(0xFFDC2626)
                    )
                }
            }

            if (bestScore != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "⭐ Nota obtenida: ${bestScore.first}/${bestScore.second} correctas",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFB45309)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isAvailable) Color(0xFFD97706) else Color(0xFF2563EB)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(
                        text = if (isAvailable) "Iniciar Quiz" else "Ver Retroalimentación ➔",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
