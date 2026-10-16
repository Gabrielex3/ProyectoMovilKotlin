package com.example.appcolegio.ui.screens.teacher

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
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.ui.AppScreen

@Composable
fun TeacherHomeScreen(
    user: UserProfile,
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = true,
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
                    selected = false,
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
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1D4ED8))
                        .clickable { onNavigate(AppScreen.PROFILE_SELECTION) },
                    contentAlignment = Alignment.Center
                ) {
                    Text("M", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Curso 6.° A", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Profesora Camila Rojas", fontSize = 12.sp, color = Color(0xFF64748B))
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFCCFBF1),
                    modifier = Modifier.clickable { onNavigate(AppScreen.PROFILE_SELECTION) }
                ) {
                    Text(
                        text = "Docente 🔄",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color(0xFF0F766E),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Blue Banner Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Buenos días, Camila", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tu curso completó 112 actividades esta semana.",
                            fontSize = 12.sp,
                            color = Color(0xFF93C5FD)
                        )
                    }
                    Text("➔", color = Color.White, fontSize = 20.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Course Summary Metrics
            Text("Resumen del curso", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Text("Últimos 7 días", fontSize = 11.sp, color = Color(0xFF64748B))

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard("👥 34", "estudiantes", Modifier.weight(1f))
                MetricCard("📈 82%", "participación", Modifier.weight(1f))
                MetricCard("📝 6", "por revisar", Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Progress by Subject
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Avance por asignatura", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFEFF6FF)
                ) {
                    Text("6.° A", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), fontSize = 11.sp, color = Color(0xFF1D4ED8), fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SubjectProgressItem("Lenguaje", 0.76f, "76%")
            Spacer(modifier = Modifier.height(8.dp))
            SubjectProgressItem("Matemática", 0.64f, "64%")
            Spacer(modifier = Modifier.height(8.dp))
            SubjectProgressItem("Ciencias", 0.58f, "58%")
            Spacer(modifier = Modifier.height(8.dp))
            SubjectProgressItem("Historia", 0.46f, "46%")

            Spacer(modifier = Modifier.height(24.dp))

            // Recent Activity
            Text("Actividad reciente", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("📈 ", fontSize = 14.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Mayor participación en Ciencias", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            Text("28 de 34 estudiantes completaron OA 9", fontSize = 11.sp, color = Color(0xFF64748B))
                        }
                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFDCFCE7)) {
                            Text("+12%", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), color = Color(0xFF15803D), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⏳ ", fontSize = 14.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text("6 actividades esperan revisión", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                            Text("Asignadas durante los últimos 3 días", fontSize = 11.sp, color = Color(0xFF64748B))
                        }
                        Text("➔", color = Color(0xFF94A3B8), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun MetricCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, fontSize = 10.sp, color = Color(0xFF64748B))
        }
    }
}

@Composable
fun SubjectProgressItem(name: String, progress: Float, percentText: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B), modifier = Modifier.width(90.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = Color(0xFF2563EB),
                trackColor = Color(0xFFE2E8F0)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(percentText, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1D4ED8))
        }
    }
}