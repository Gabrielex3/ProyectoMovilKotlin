package com.example.appcolegio.ui.screens.teacher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.ui.AppScreen

@Composable
fun TeacherHomeScreen(
    user: UserProfile,
    onNavigate: (AppScreen) -> Unit
) {
    val firstName = user.name.split(" ").first()
    val initials = user.name.split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Curso 6.° A",
                subtitle = "Profesora ${user.name}",
                leading = {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(TeacherAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(initials, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                },
                trailing = {
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onNavigate(AppScreen.PROFILE_SELECTION) },
                        shape = RoundedCornerShape(8.dp),
                        color = Color.Transparent,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Text(
                            text = "Cambiar perfil",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        bottomBar = { TeacherBottomBar(selected = 0, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Resumen destacado
            TeacherCard(
                modifier = Modifier.fillMaxWidth(),
                selected = true,
                onClick = { onNavigate(AppScreen.PEDAGOGICAL_REPORT) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            "Buenos días, $firstName",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            "Tu curso completó 112 actividades esta semana.",
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Ver informe",
                        tint = TeacherAccent
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            TeacherSectionHeader("Resumen del curso", "Últimos 7 días")
            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard("34", "Estudiantes", Modifier.weight(1f))
                MetricCard("82%", "Participación", Modifier.weight(1f))
                MetricCard("6", "Por revisar", Modifier.weight(1f))
            }

            Spacer(Modifier.height(24.dp))

            TeacherSectionHeader(
                title = "Avance por asignatura",
                trailing = { TeacherStatusPill("6.° A", TeacherInfo) }
            )
            Spacer(Modifier.height(12.dp))

            SubjectProgressItem("Lenguaje", 0.76f, "76%")
            Spacer(Modifier.height(8.dp))
            SubjectProgressItem("Matemática", 0.64f, "64%")
            Spacer(Modifier.height(8.dp))
            SubjectProgressItem("Ciencias", 0.58f, "58%")
            Spacer(Modifier.height(8.dp))
            SubjectProgressItem("Historia", 0.46f, "46%")

            Spacer(Modifier.height(24.dp))

            TeacherSectionHeader("Actividad reciente")
            Spacer(Modifier.height(12.dp))

            TeacherCard(modifier = Modifier.fillMaxWidth()) {
                ActivityRow(
                    icon = Icons.Default.Analytics,
                    tint = TeacherOk,
                    title = "Mayor participación en Ciencias",
                    subtitle = "28 de 34 estudiantes completaron OA 9",
                    trailing = { TeacherStatusPill("+12%", TeacherOk) }
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                ActivityRow(
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    tint = TeacherWarn,
                    title = "6 actividades esperan revisión",
                    subtitle = "Asignadas durante los últimos 3 días",
                    onClick = { onNavigate(AppScreen.PEDAGOGICAL_REPORT) },
                    trailing = {
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ActivityRow(
    icon: ImageVector,
    tint: Color,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null,
    trailing: @Composable () -> Unit = {}
) {
    val base = Modifier.fillMaxWidth()
    Row(
        modifier = (if (onClick != null) base.clickable(onClick = onClick) else base).padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(tint.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Spacer(Modifier.width(8.dp))
        trailing()
    }
}

@Composable
fun MetricCard(title: String, subtitle: String, modifier: Modifier = Modifier) {
    TeacherCard(modifier = modifier) {
        Column(Modifier.padding(14.dp)) {
            Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun SubjectProgressItem(name: String, progress: Float, percentText: String) {
    val color = teacherProgressColor(progress * 100f)
    TeacherCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.width(96.dp)
            )
            TeacherProgressBar(progress = progress, color = color, modifier = Modifier.weight(1f))
            Text(
                percentText,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = color,
                textAlign = TextAlign.End,
                modifier = Modifier.width(48.dp)
            )
        }
    }
}