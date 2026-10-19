package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.QuizQuestion
import com.example.appcolegio.ui.AppScreen

@Composable
fun QuizScreen(
    questions: List<QuizQuestion>,
    onFinishQuiz: (score: Int, total: Int, answeredList: List<QuizQuestion>) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val mutableQuestions = remember(questions) { questions.toMutableList() }
    val currentQuestion = mutableQuestions.getOrNull(currentIndex) ?: mutableQuestions.first()

    var selectedOptionForCurrent by remember(currentIndex) {
        mutableIntStateOf(currentQuestion.userSelectedIndex ?: 0)
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onNavigate(AppScreen.STUDENT_ACTIVITIES) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(currentQuestion.subjectName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Evaluación de la unidad", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    IconButton(onClick = { onNavigate(AppScreen.STUDENT_ACTIVITIES) }) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pregunta ${currentIndex + 1} de ${mutableQuestions.size}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text("1 punto", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                LinearProgressIndicator(
                    progress = { (currentIndex + 1) / mutableQuestions.size.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = Color(0xFF2563EB),
                    trackColor = Color(0xFFE2E8F0)
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
            Text(
                text = "OBSERVA Y RESPONDE (${currentQuestion.title})",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currentQuestion.contextText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Visual Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF3C7))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = when (currentQuestion.imageType) {
                                "lamp" -> "💡"
                                "sun" -> "☀️"
                                "toaster" -> "🍞"
                                "heater" -> "🔥"
                                "battery" -> "🔋"
                                "math" -> "🔢"
                                else -> "📖"
                            },
                            fontSize = 44.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFFDE68A)
                        ) {
                            Text(
                                text = currentQuestion.subjectName,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Options List
            currentQuestion.options.forEachIndexed { index, optionText ->
                val isSelected = selectedOptionForCurrent == index
                OptionItem(
                    text = optionText,
                    isSelected = isSelected,
                    onClick = { selectedOptionForCurrent = index }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("ℹ️ ", fontSize = 12.sp)
                Text(
                    text = "Puedes cambiar tu respuesta antes de pasar a la siguiente.",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val q = mutableQuestions[currentIndex]
                    mutableQuestions[currentIndex] = q.copy(userSelectedIndex = selectedOptionForCurrent)

                    if (currentIndex + 1 < mutableQuestions.size) {
                        currentIndex++
                    } else {
                        var calculatedScore = 0
                        mutableQuestions.forEach { item ->
                            if ((item.userSelectedIndex ?: 0) == item.correctIndex) {
                                calculatedScore++
                            }
                        }
                        onFinishQuiz(calculatedScore, mutableQuestions.size, mutableQuestions)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = if (currentIndex + 1 < mutableQuestions.size) "Siguiente Pregunta ➔" else "Finalizar y ver Resultado",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun OptionItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF2563EB) else Color(0xFFCBD5E1)
    val cardBg = if (isSelected) Color(0xFFEFF6FF) else MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) Color(0xFF1E40AF) else MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) Color(0xFF2563EB) else Color.Transparent)
                    .border(1.dp, if (isSelected) Color(0xFF2563EB) else Color(0xFFCBD5E1), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Seleccionado",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
