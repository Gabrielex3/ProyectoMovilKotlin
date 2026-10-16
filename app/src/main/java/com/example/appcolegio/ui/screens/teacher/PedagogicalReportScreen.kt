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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.LearningObjective
import com.example.appcolegio.ui.AppScreen

@Composable
fun PedagogicalReportScreen(
    utpStatus: String,
    objectives: List<LearningObjective>,
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
                IconButton(onClick = { onNavigate(AppScreen.TEACHER_HOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Informe pedagógico", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Ciencias • 6.° A", fontSize = 12.sp, color = Color(0xFF64748B))
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFCCFBF1),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Docente",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color(0xFF0F766E),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.TEACHER_HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Curso") },
                    label = { Text("Curso") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.CONTENT_ASSIGNMENT) },
                    icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Asignar") },
                    label = { Text("Asignar") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.PEDAGOGICAL_REPORT) },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = "Informes") },
                    label = { Text("Informes") }
                )
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
            // Status Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (utpStatus == "AUTORIZADO") Color(0xFFDCFCE7) else Color(0xFFFEF3C7)
                )
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (utpStatus == "AUTORIZADO") "✅ Informe autorizado por UTP" else "⏳ Pendiente de validación UTP",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (utpStatus == "AUTORIZADO") Color(0xFF15803D) else Color(0xFFB45309)
                        )
                        Text(
                            text = if (utpStatus == "AUTORIZADO") "Aprobado para visualización institucional." else "Borrador docente. No está visible para estudiantes.",
                            fontSize = 11.sp,
                            color = if (utpStatus == "AUTORIZADO") Color(0xFF166534) else Color(0xFF92400E)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (utpStatus == "AUTORIZADO") Color(0xFFBBF7D0) else Color(0xFFFDE68A)
                    ) {
                        Text(
                            text = if (utpStatus == "AUTORIZADO") "VALIDADO" else "BORRADOR",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = if (utpStatus == "AUTORIZADO") Color(0xFF15803D) else Color(0xFFB45309),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filters Row
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Text("👥 6.° A  ▼", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold)
                }

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Text("📖 Unidad 2  ▼", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontSize = 12.sp, color = Color(0xFF1E293B), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Síntesis del Curso
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Síntesis del curso", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text("34 estudiantes", fontSize = 11.sp, color = Color(0xFF64748B))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SummaryBox("19", "Logrado", Color(0xFFDCFCE7), Color(0xFF15803D), Modifier.weight(1f))
                SummaryBox("10", "En proceso", Color(0xFFFEF3C7), Color(0xFFB45309), Modifier.weight(1f))
                SummaryBox("5", "Sin evidencia", Color(0xFFF1F5F9), Color(0xFF64748B), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Resultados por Objetivo
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Resultados por objetivo", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text("Ordenar por logro", fontSize = 11.sp, color = Color(0xFF2563EB), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(12.dp))

            objectives.forEach { oa ->
                OaResultCard(oa)
                Spacer(modifier = Modifier.height(12.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Disclaimer Footnote
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("ℹ️ ", fontSize = 14.sp)
                    Text(
                        text = "Resumen basado en actividades del MVP. Requiere criterio docente y validación UTP; no constituye diagnóstico automático.",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B),
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryBox(count: String, label: String, bg: Color, textColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(count, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(label, fontSize = 10.sp, color = textColor, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun OaResultCard(oa: LearningObjective) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFD1FAE5)) {
                    Text(oa.code, modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 11.sp, color = Color(0xFF047857), fontWeight = FontWeight.Bold)
                }

                Text("${oa.achievedPercent}%", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(oa.description, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { oa.achievedPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF2563EB),
                trackColor = Color(0xFFE2E8F0)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("🟢 ${oa.logradoCount} logrado", fontSize = 11.sp, color = Color(0xFF15803D))
                Text("🟡 ${oa.enProcesoCount} en proceso", fontSize = 11.sp, color = Color(0xFFB45309))
                Text("⚪ ${oa.sinEvidenciaCount} sin evidencia", fontSize = 11.sp, color = Color(0xFF64748B))
            }
        }
    }
}