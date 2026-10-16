package com.example.appcolegio.ui.screens.student

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
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.ui.AppScreen

@Composable
fun StudentHomeScreen(
    user: UserProfile,
    subjects: List<SubjectItem>,
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.STUDENT_HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.UNIT_DETAIL) },
                    icon = { Icon(Icons.Default.Book, contentDescription = "Contenidos") },
                    label = { Text("Contenidos") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.QUIZ_ACTIVITY) },
                    icon = { Icon(Icons.Default.Assignment, contentDescription = "Actividades") },
                    label = { Text("Actividades") }
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
                    Text("Hola, Antonia", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("6.° A • Martes 2 de octubre", fontSize = 12.sp, color = Color(0xFF64748B))
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFDBEAFE),
                    modifier = Modifier.clickable { onNavigate(AppScreen.PROFILE_SELECTION) }
                ) {
                    Text(
                        text = "Estudiante 🔄",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color(0xFF1E40AF),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Blue Card: Weekly Progress
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E40AF))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Tu avance esta semana", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Text("7 de 10 actividades completadas", color = Color(0xFF93C5FD), fontSize = 12.sp)
                        }

                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF2563EB)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("70%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = Color(0xFF60A5FA),
                        trackColor = Color(0xFF1E3A8A)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { onNavigate(AppScreen.UNIT_DETAIL) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(
                            text = "⚡ Continúa: \"La energía se transforma\"",
                            color = Color(0xFF1E40AF),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Subjects Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Mis asignaturas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text(
                    text = "Ver todas",
                    fontSize = 13.sp,
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigate(AppScreen.UNIT_DETAIL) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Grid of 4 subjects
            Row(modifier = Modifier.fillMaxWidth()) {
                SubjectCard(
                    title = "Lenguaje",
                    subtitle = "Relatos que nos unen",
                    percent = 72,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.UNIT_DETAIL) }
                )
                Spacer(modifier = Modifier.width(12.dp))
                SubjectCard(
                    title = "Matemática",
                    subtitle = "Fracciones y razones",
                    percent = 58,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.UNIT_DETAIL) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                SubjectCard(
                    title = "Ciencias",
                    subtitle = "La energía",
                    percent = 44,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.UNIT_DETAIL) }
                )
                Spacer(modifier = Modifier.width(12.dp))
                SubjectCard(
                    title = "Historia",
                    subtitle = "Chile republicano",
                    percent = 31,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.UNIT_DETAIL) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // "Para hoy" Section
            Text("Para hoy", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(AppScreen.DIDACTIC_CONTENT) },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7ED))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📅 Para hoy", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFFC2410C))
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Actividad de Ciencias - 10 min aprox.", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFFFEDD5)
                    ) {
                        Text(
                            text = "Nueva",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = Color(0xFFC2410C),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SubjectCard(
    title: String,
    subtitle: String,
    percent: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text("$percent%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { percent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = Color(0xFF2563EB),
                trackColor = Color(0xFFE2E8F0)
            )
        }
    }
}