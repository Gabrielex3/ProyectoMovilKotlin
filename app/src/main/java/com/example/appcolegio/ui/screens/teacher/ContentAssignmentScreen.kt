package com.example.appcolegio.ui.screens.teacher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.appcolegio.data.model.AssignmentConfig
import com.example.appcolegio.ui.AppScreen

@Composable
fun ContentAssignmentScreen(
    currentConfig: AssignmentConfig,
    onSaveConfig: (AssignmentConfig) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    var maxAttempts by remember { mutableIntStateOf(currentConfig.maxAttempts) }
    var showCorrection by remember { mutableStateOf(currentConfig.showCorrection) }
    var isPublished by remember { mutableStateOf(currentConfig.isPublished) }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigate(AppScreen.TEACHER_HOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Nueva asignación", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Docente • Curso 6.° A", fontSize = 12.sp, color = Color(0xFF64748B))
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
                .padding(16.dp)
        ) {
            // Wizard Steps Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepIndicator("1", "Contenido", isDone = true, isActive = false)
                Text("───", color = Color(0xFFCBD5E1), fontSize = 12.sp)
                StepIndicator("2", "Configurar", isDone = false, isActive = true)
                Text("───", color = Color(0xFFCBD5E1), fontSize = 12.sp)
                StepIndicator("3", "Confirmar", isDone = false, isActive = false)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Selected Content Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFDBEAFE)) {
                            Text("CIENCIAS - OA 9", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 11.sp, color = Color(0xFF1D4ED8), fontWeight = FontWeight.Bold)
                        }
                        Text("Cambiar", fontSize = 12.sp, color = Color(0xFF2563EB), fontWeight = FontWeight.Bold, modifier = Modifier.clickable { })
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text("La energía se transforma", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.height(2.dp))
                    Text("Contenido + actividad de 5 preguntas • 15 min", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Configura la actividad", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Spacer(modifier = Modifier.height(16.dp))

            // Field 1: Course
            Text("Curso destinatario", fontSize = 12.sp, color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("👥 6.° A • 34 estudiantes", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("▼", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Field 2: Due Date
            Text("Fecha de entrega", fontSize = 12.sp, color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(4.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("📅 Viernes 9 de octubre - 18:00", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("▼", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Field 3: Attempt Limits
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Límite de intentos", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE2E8F0))
                            .clickable { if (maxAttempts > 1) maxAttempts-- },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Restar", modifier = Modifier.size(16.dp))
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text("$maxAttempts", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

                    Spacer(modifier = Modifier.width(12.dp))

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDBEAFE))
                            .clickable { maxAttempts++ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Sumar", tint = Color(0xFF1D4ED8), modifier = Modifier.size(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AttemptOptionChip("1 intento", maxAttempts == 1) { maxAttempts = 1 }
                AttemptOptionChip("2 intentos", maxAttempts == 2) { maxAttempts = 2 }
                AttemptOptionChip("3 intentos", maxAttempts == 3) { maxAttempts = 3 }
                AttemptOptionChip("Sin límite", maxAttempts > 10) { maxAttempts = 99 }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Field 4: Show correction toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Mostrar corrección al finalizar", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Indica respuestas correctas e incorrectas", fontSize = 11.sp, color = Color(0xFF64748B))
                }
                Switch(
                    checked = showCorrection,
                    onCheckedChange = { showCorrection = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF2563EB))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Field 5: Publish now toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Publicar ahora", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Disponible inmediatamente para el curso", fontSize = 11.sp, color = Color(0xFF64748B))
                }
                Switch(
                    checked = isPublished,
                    onCheckedChange = { isPublished = it },
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF2563EB))
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Info Notice
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB))
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("ℹ️ ", fontSize = 14.sp)
                    Text(
                        text = "Se guardará como borrador hasta confirmar la asignación.",
                        fontSize = 11.sp,
                        color = Color(0xFF92400E)
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    onSaveConfig(
                        AssignmentConfig(
                            maxAttempts = maxAttempts,
                            showCorrection = showCorrection,
                            isPublished = isPublished
                        )
                    )
                    onNavigate(AppScreen.PEDAGOGICAL_REPORT)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text("Revisar asignación", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun StepIndicator(step: String, label: String, isDone: Boolean, isActive: Boolean) {
    val circleBg = when {
        isDone -> Color(0xFFDCFCE7)
        isActive -> Color(0xFF2563EB)
        else -> Color(0xFFE2E8F0)
    }
    val circleTextColor = when {
        isDone -> Color(0xFF15803D)
        isActive -> Color.White
        else -> Color(0xFF64748B)
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(circleBg),
            contentAlignment = Alignment.Center
        ) {
            Text(if (isDone) "✓" else step, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = circleTextColor)
        }
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, fontSize = 11.sp, fontWeight = if (isActive || isDone) FontWeight.Bold else FontWeight.Normal, color = if (isActive) Color(0xFF1E293B) else Color(0xFF64748B))
    }
}

@Composable
fun AttemptOptionChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    val bg = if (isSelected) Color(0xFFDBEAFE) else Color.White
    val textCol = if (isSelected) Color(0xFF1D4ED8) else Color(0xFF475569)

    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = bg,
        border = if (isSelected) BorderStroke(1.dp, Color(0xFF2563EB)) else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
    ) {
        Text(label, modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp), fontSize = 10.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, color = textCol)
    }
}
