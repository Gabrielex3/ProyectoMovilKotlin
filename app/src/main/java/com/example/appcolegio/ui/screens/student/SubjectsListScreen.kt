package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.appcolegio.ui.AppScreen

@Composable
fun SubjectsListScreen(
    subjects: List<SubjectItem>,
    onSelectSubject: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            KidsTopBar(
                title = "📚 Asignaturas y Ramos",
                subtitle = "6.° Año Básico • Currículum Nacional",
                onBack = { onNavigate(AppScreen.STUDENT_HOME) }
            )
        },
        bottomBar = { StudentBottomBar(selected = 1, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            KidsSectionTitle("🎒 Elige tu aventura")

            subjects.forEach { subject ->
                SubjectDetailCard(
                    subject = subject,
                    onClick = {
                        onSelectSubject(subject.id)
                        onNavigate(AppScreen.UNIT_DETAIL)
                    }
                )
            }
        }
    }
}

@Composable
fun SubjectDetailCard(
    subject: SubjectItem,
    onClick: () -> Unit
) {
    val style = kidSubjectStyle(subject.name, Color(subject.colorHex))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(3.dp, style.color),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(style.color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) { Text(style.emoji, fontSize = 36.sp) }

                Spacer(Modifier.width(14.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = subject.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = subject.subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            KidsProgressBar(
                progress = subject.progressPercent / 100f,
                color = style.color,
                track = style.color.copy(alpha = 0.18f),
                height = 12.dp
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (subject.progressPercent >= 100) "🏆 ¡Completado!" else "${subject.progressPercent}% completado",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = style.color
                )
                KidsChip("¡Vamos! ➔", style.color, Color.White)
            }
        }
    }
}