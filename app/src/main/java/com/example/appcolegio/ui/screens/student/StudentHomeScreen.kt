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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.ui.AppScreen

@Composable
fun StudentHomeScreen(
    user: UserProfile,
    subjects: List<SubjectItem>,
    onSelectSubject: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.STUDENT_HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = false,
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
                    Text("Hola, ${user.name.split(" ").first()}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text("${user.courseInfo} • Martes 2 de octubre", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                        onClick = {
                            onSelectSubject("SUBJ_1")
                            onNavigate(AppScreen.UNIT_DETAIL)
                        },
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
                Text("Mis asignaturas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(
                    text = "Ver todas",
                    fontSize = 13.sp,
                    color = Color(0xFF2563EB),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigate(AppScreen.SUBJECTS_LIST) }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Grid of 4 subjects with uniform height
            Row(modifier = Modifier.fillMaxWidth()) {
                val s1 = subjects.getOrNull(0)
                val s2 = subjects.getOrNull(1)
                SubjectCard(
                    title = s1?.name ?: "Ciencias Naturales",
                    subtitle = s1?.subtitle ?: "Unidad 2: La energía",
                    percent = s1?.progressPercent ?: 44,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (s1 != null) onSelectSubject(s1.id)
                        onNavigate(AppScreen.UNIT_DETAIL)
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                SubjectCard(
                    title = s2?.name ?: "Lenguaje y Com.",
                    subtitle = s2?.subtitle ?: "Unidad 1: Mitos y leyendas",
                    percent = s2?.progressPercent ?: 72,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (s2 != null) onSelectSubject(s2.id)
                        onNavigate(AppScreen.UNIT_DETAIL)
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                val s3 = subjects.getOrNull(2)
                val s4 = subjects.getOrNull(3)
                SubjectCard(
                    title = s3?.name ?: "Matemática",
                    subtitle = s3?.subtitle ?: "Unidad 1: Fracciones",
                    percent = s3?.progressPercent ?: 58,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (s3 != null) onSelectSubject(s3.id)
                        onNavigate(AppScreen.UNIT_DETAIL)
                    }
                )
                Spacer(modifier = Modifier.width(12.dp))
                SubjectCard(
                    title = s4?.name ?: "Historia y C. S.",
                    subtitle = s4?.subtitle ?: "Unidad 1: Chile republicano",
                    percent = s4?.progressPercent ?: 31,
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (s4 != null) onSelectSubject(s4.id)
                        onNavigate(AppScreen.UNIT_DETAIL)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // "Para hoy" Section
            Text("Para hoy", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onSelectSubject("SUBJ_1")
                        onNavigate(AppScreen.DIDACTIC_CONTENT)
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                        Text("Actividad de Ciencias - 10 min aprox.", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
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
        modifier = modifier
            .heightIn(min = 125.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$percent%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column {
                Spacer(modifier = Modifier.height(8.dp))
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
}
