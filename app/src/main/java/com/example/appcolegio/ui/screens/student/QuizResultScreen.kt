package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.QuizQuestion
import com.example.appcolegio.ui.AppScreen

@Composable
fun QuizResultScreen(
    score: Int,
    total: Int,
    questions: List<QuizQuestion>,
    attemptsLeft: Int,
    onRetry: () -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    val incorrectCount = maxOf(0, total - score)
    val percentage = if (total > 0) (score * 100) / total else 0
    val stars = when {
        percentage >= 80 -> 3
        percentage >= 50 -> 2
        percentage > 0 -> 1
        else -> 0
    }
    val (emoji, headline) = when {
        percentage >= 80 -> "🏆" to "¡Excelente trabajo!"
        percentage >= 50 -> "🎉" to "¡Buen trabajo!"
        else -> "💪" to "¡Sigue intentando!"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            KidsTopBar(
                title = "🎯 Resultado de la actividad",
                subtitle = "Evaluación • Desglose",
                onBack = { onNavigate(AppScreen.STUDENT_ACTIVITIES) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Trofeo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(28.dp))
                    .background(Brush.verticalGradient(listOf(KidYellow, KidOrange)))
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(emoji, fontSize = 64.sp)
                    Text("$score/$total", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold, color = KidInk)
                    Text("respuestas correctas", fontSize = 13.sp, color = KidInk)
                    Spacer(Modifier.height(8.dp))
                    Text("⭐".repeat(stars) + "☆".repeat(3 - stars), fontSize = 28.sp)
                    Spacer(Modifier.height(8.dp))
                    KidsChip("🎯 $percentage% de logro", Color.White, KidInk)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = headline,
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = if (incorrectCount == 0) "¡Excelente! Has respondido todas las preguntas correctamente." else "Revisa las respuestas detalladas a continuación.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Intentos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = KidBlue.copy(alpha = 0.12f)),
                border = BorderStroke(2.dp, KidBlue),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔄", fontSize = 26.sp)
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (attemptsLeft > 0) "Te quedan $attemptsLeft intentos" else "Has agotado tus intentos",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = KidBlue
                        )
                        Text(
                            text = "Se conserva tu mejor resultado.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                KidsSectionTitle("📋 Tus respuestas")
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    KidsChip("🟢 $score correctas", KidGreen, Color.White)
                    KidsChip("🔴 $incorrectCount incorrectas", KidRed, Color.White)
                }
            }

            Spacer(Modifier.height(12.dp))

            questions.forEachIndexed { index, q ->
                val userSelection = q.userSelectedIndex ?: 0
                val isCorrect = userSelection == q.correctIndex
                val correctOptionText = q.options.getOrNull(q.correctIndex) ?: "Respuesta correcta"

                ResultAnswerItem(
                    number = index + 1,
                    title = q.title,
                    subtitle = correctOptionText,
                    isCorrect = isCorrect
                )
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onNavigate(AppScreen.STUDENT_ACTIVITIES) },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(2.dp, KidBlue)
                ) {
                    Text("📝 Actividades", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                }

                if (attemptsLeft > 0) {
                    Button(
                        onClick = { onRetry() },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidOrange)
                    ) {
                        Text("🔁 Reintentar ($attemptsLeft)", fontSize = 13.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun ResultAnswerItem(
    number: Int,
    title: String,
    subtitle: String,
    isCorrect: Boolean
) {
    val accent = if (isCorrect) KidGreen else KidRed

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = accent.copy(alpha = 0.12f)),
        border = BorderStroke(2.dp, accent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(accent),
                contentAlignment = Alignment.Center
            ) {
                Text("$number", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.onSurface)
                Text("Respuesta correcta: $subtitle", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Text(if (isCorrect) "✅" else "❌", fontSize = 26.sp)
        }
    }
}