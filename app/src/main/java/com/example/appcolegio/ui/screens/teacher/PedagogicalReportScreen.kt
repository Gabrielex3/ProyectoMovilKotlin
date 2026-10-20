package com.example.appcolegio.ui.screens.teacher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val isAutorizado = utpStatus == "AUTORIZADO"
    val isCambiosSolicitados = utpStatus == "CAMBIOS_SOLICITADOS"

    val statusColor = when {
        isAutorizado -> TeacherOk
        isCambiosSolicitados -> TeacherDanger
        else -> TeacherWarn
    }
    val titleText = when {
        isAutorizado -> "Informe autorizado por UTP"
        isCambiosSolicitados -> "Cambios solicitados por UTP"
        else -> "Pendiente de validación UTP"
    }
    val subtitleText = when {
        isAutorizado -> "Aprobado para visualización institucional."
        isCambiosSolicitados -> "Revisa los comentarios de la Jefatura UTP y reenvía."
        else -> "Borrador docente. No está visible para estudiantes."
    }
    val badgeText = when {
        isAutorizado -> "VALIDADO"
        isCambiosSolicitados -> "CAMBIOS"
        else -> "BORRADOR"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Informe pedagógico",
                subtitle = "Ciencias • 6.° A",
                onBack = { onNavigate(AppScreen.TEACHER_HOME) },
                trailing = { TeacherStatusPill("Docente", TeacherAccent) }
            )
        },
        bottomBar = { TeacherBottomBar(selected = 2, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Estado UTP
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = statusColor.copy(alpha = 0.10f)),
                border = BorderStroke(1.dp, statusColor.copy(alpha = 0.40f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(titleText, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = statusColor)
                        Text(subtitleText, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(Modifier.width(8.dp))
                    TeacherStatusPill(badgeText, statusColor)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Filtros
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                FilterDropdownChip("6.° A")
                FilterDropdownChip("Unidad 2")
            }

            Spacer(Modifier.height(24.dp))

            // Síntesis del curso
            TeacherCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    TeacherSectionHeader(
                        title = "Síntesis del curso",
                        trailing = {
                            Text("34 estudiantes", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    )
                    Spacer(Modifier.height(14.dp))
                    TeacherDistributionBar(
                        listOf(19 to TeacherOk, 10 to TeacherWarn, 5 to TeacherMuted)
                    )
                    Spacer(Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SummaryBox("19", "Logrado", TeacherOk.copy(alpha = 0.12f), TeacherOk, Modifier.weight(1f))
                        SummaryBox("10", "En proceso", TeacherWarn.copy(alpha = 0.12f), TeacherWarn, Modifier.weight(1f))
                        SummaryBox("5", "Sin evidencia", TeacherMuted.copy(alpha = 0.12f), TeacherMuted, Modifier.weight(1f))
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            TeacherSectionHeader(
                title = "Resultados por objetivo",
                trailing = {
                    Text("Ordenar por logro", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TeacherAccent)
                }
            )
            Spacer(Modifier.height(12.dp))

            objectives.forEach { oa ->
                OaResultCard(oa)
                Spacer(Modifier.height(12.dp))
            }

            Spacer(Modifier.height(4.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Text(
                    text = "Resumen basado en actividades del MVP. Requiere criterio docente y validación UTP; no constituye diagnóstico automático.",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun FilterDropdownChip(text: String) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier.padding(start = 12.dp, end = 6.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SummaryBox(count: String, label: String, bg: Color, textColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bg),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(count, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = textColor)
        }
    }
}

@Composable
fun OaResultCard(oa: LearningObjective) {
    val color = teacherProgressColor(oa.achievedPercent.toFloat())

    TeacherCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeacherStatusPill(oa.code, TeacherInfo)
                Text("${oa.achievedPercent}%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
            }

            Spacer(Modifier.height(10.dp))

            Text(
                oa.description,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(12.dp))

            TeacherProgressBar(progress = oa.achievedPercent / 100f, color = color)

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TeacherLegendItem(TeacherOk, "${oa.logradoCount} logrado")
                TeacherLegendItem(TeacherWarn, "${oa.enProcesoCount} en proceso")
                TeacherLegendItem(TeacherMuted, "${oa.sinEvidenciaCount} sin evidencia")
            }
        }
    }
}