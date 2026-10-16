package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.ui.AppScreen

@Composable
fun QuizResultScreen(
    score: Int,
    total: Int,
    attemptsLeft: Int,
    onRetry: () -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigate(AppScreen.UNIT_DETAIL) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Resultado de la actividad", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Ciencias • OA 9", fontSize = 12.sp, color = Color(0xFF64748B))
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Score Circle Badge
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFEF3C7)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$score/$total",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD97706)
                    )
                    Text(
                        text = "correctas",
                        fontSize = 10.sp,
                        color = Color(0xFFB45309)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFFEF3C7)
            ) {
                Text(
                    text = "🎯 ${score * 20}% de logro",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = Color(0xFFB45309),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¡Buen trabajo, Antonia!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Revisa dos respuestas y vuelve a intentarlo cuando te sientas lista.",
                fontSize = 13.sp,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Attempts Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("🔄 ", fontSize = 14.sp)
                    Column {
                        Text(
                            text = "Te quedan $attemptsLeft intentos",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1D4ED8)
                        )
                        Text(
                            text = "Se conserva tu mejor resultado.",
                            fontSize = 11.sp,
                            color = Color(0xFF3B82F6)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Answers Breakdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tus respuestas", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text("🟢 $score correctas  🔴 ${total - score} por revisar", fontSize = 11.sp, color = Color(0xFF64748B))
            }

            Spacer(modifier = Modifier.height(12.dp))

            ResultAnswerItem(1, "Transformación en una lámpara", "Eléctrica ➔ luz y calor", isCorrect = true)
            Spacer(modifier = Modifier.height(8.dp))

            ResultAnswerItem(2, "Fuente renovable", "Energía solar", isCorrect = true)
            Spacer(modifier = Modifier.height(8.dp))

            ResultAnswerItem(3, "Energía en una tostadora", "Térmica ➔ eléctrica", isCorrect = false)
            Spacer(modifier = Modifier.height(8.dp))

            ResultAnswerItem(4, "Transferencia de calor", "Por contacto", isCorrect = true)
            Spacer(modifier = Modifier.height(8.dp))

            ResultAnswerItem(5, "Energía almacenada", "Energía sonora", isCorrect = false)

            Spacer(modifier = Modifier.height(28.dp))

            // Bottom Buttons: Revisar & Reintentar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onNavigate(AppScreen.DIDACTIC_CONTENT) },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Revisar", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                }

                Button(
                    onClick = { onRetry() },
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Reintentar", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
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
    val bgCircle = if (isCorrect) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
    val iconTint = if (isCorrect) Color(0xFF16A34A) else Color(0xFFDC2626)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(if (isCorrect) Color(0xFF16A34A) else Color(0xFFDC2626)),
                contentAlignment = Alignment.Center
            ) {
                Text("$number", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text(subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(bgCircle),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isCorrect) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = if (isCorrect) "Correcto" else "Incorrecto",
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}