package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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

private val optionAccents = listOf(KidBlue, KidOrange, KidGreen, KidPurple)

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

    val style = kidSubjectStyle(currentQuestion.subjectName)
    val isLast = currentIndex + 1 >= mutableQuestions.size

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            KidsTopBar(
                title = "${style.emoji} ${currentQuestion.subjectName}",
                subtitle = "Evaluación de la unidad",
                color = style.color,
                onBack = { onNavigate(AppScreen.STUDENT_ACTIVITIES) },
                trailing = {
                    IconButton(onClick = { onNavigate(AppScreen.STUDENT_ACTIVITIES) }) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                    }
                },
                bottom = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Pregunta ${currentIndex + 1} de ${mutableQuestions.size}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text("1 punto ⭐", fontSize = 13.sp, color = Color.White)
                    }
                    KidsProgressBar(
                        progress = (currentIndex + 1) / mutableQuestions.size.toFloat(),
                        color = KidYellow,
                        track = Color.White.copy(alpha = 0.25f),
                        height = 12.dp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            KidsChip(
                text = "🧠 OBSERVA Y RESPONDE · ${currentQuestion.title}",
                bg = style.color.copy(alpha = 0.15f),
                fg = style.color
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = currentQuestion.contextText,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 26.sp
            )

            Spacer(Modifier.height(16.dp))

            // Caja visual
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = KidYellow.copy(alpha = 0.28f)),
                border = BorderStroke(2.dp, KidYellow),
                elevation = CardDefaults.cardElevation(0.dp)
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
                            fontSize = 56.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        KidsChip(currentQuestion.subjectName, KidYellow, KidInk)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            currentQuestion.options.forEachIndexed { index, optionText ->
                OptionItem(
                    text = optionText,
                    isSelected = selectedOptionForCurrent == index,
                    onClick = { selectedOptionForCurrent = index },
                    letter = ('A' + index).toString(),
                    accent = optionAccents[index % optionAccents.size]
                )
                Spacer(Modifier.height(12.dp))
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "💡 Puedes cambiar tu respuesta antes de pasar a la siguiente.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(20.dp))

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
                    .height(60.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KidOrange),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = if (isLast) "¡Finalizar y ver resultado! 🏆" else "Siguiente pregunta ➔",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun OptionItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    letter: String = "",
    accent: Color = KidBlue
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(if (isSelected) 3.dp else 2.dp, if (isSelected) accent else accent.copy(alpha = 0.35f)),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) accent.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(if (isSelected) 4.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (letter.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) accent else accent.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        letter,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isSelected) Color.White else accent
                    )
                }
                Spacer(Modifier.width(12.dp))
            }

            Text(
                text = text,
                modifier = Modifier.weight(1f),
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (isSelected) {
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(KidYellow),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = KidInk)
                }
            }
        }
    }
}