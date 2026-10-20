package com.example.appcolegio.ui.screens.utp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.UtpCriterion
import com.example.appcolegio.ui.AppScreen
import com.example.appcolegio.ui.screens.teacher.TeacherActionBar
import com.example.appcolegio.ui.screens.teacher.TeacherCard
import com.example.appcolegio.ui.screens.teacher.TeacherDanger
import com.example.appcolegio.ui.screens.teacher.TeacherMuted
import com.example.appcolegio.ui.screens.teacher.TeacherOk
import com.example.appcolegio.ui.screens.teacher.TeacherProgressBar
import com.example.appcolegio.ui.screens.teacher.TeacherSectionHeader
import com.example.appcolegio.ui.screens.teacher.TeacherStatusPill
import com.example.appcolegio.ui.screens.teacher.TeacherTopBar
import com.example.appcolegio.ui.screens.teacher.TeacherWarn

@Composable
fun UtpReviewScreen(
    utpStatus: String,
    criteriaList: List<UtpCriterion>,
    onAuthorize: () -> Unit,
    onRequestChanges: () -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    val isAutorizado = utpStatus == "AUTORIZADO"
    val isCambiosSolicitados = utpStatus == "CAMBIOS_SOLICITADOS"
    val isPending = !isAutorizado && !isCambiosSolicitados

    val total = criteriaList.size
    val conformCount = if (isAutorizado) total else criteriaList.count { it.status == "Conforme" }

    val statusColor = when {
        isAutorizado -> TeacherOk
        isCambiosSolicitados -> TeacherDanger
        else -> TeacherWarn
    }
    val statusTitle = when {
        isAutorizado -> "Unidad autorizada"
        isCambiosSolicitados -> "Cambios solicitados"
        else -> "Pendiente de autorización"
    }
    val statusSubtitle = when {
        isAutorizado -> "Autorizada correctamente para el curso 6.° A."
        isCambiosSolicitados -> "Cambios solicitados enviados al docente."
        else -> "Revisa los criterios y toma una decisión."
    }
    val statusBadge = when {
        isAutorizado -> "AUTORIZADA"
        isCambiosSolicitados -> "CAMBIOS"
        else -> "PENDIENTE"
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Revisión de unidad",
                subtitle = "Enviado por Camila Rojas • hoy, 10:24",
                onBack = { onNavigate(AppScreen.UTP_HOME) },
                trailing = { UtpSwitchProfileButton { onNavigate(AppScreen.PROFILE_SELECTION) } }
            )
        },
        bottomBar = {
            Column {
                if (isPending) {
                    TeacherActionBar(applyNavigationInsets = false) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = { onRequestChanges() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                            ) {
                                Text(
                                    "Pedir cambios",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Button(
                                onClick = { onAuthorize() },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = UtpAccent)
                            ) {
                                Text("Autorizar", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                            }
                        }
                    }
                }
                UtpBottomBar(selected = 1, onNavigate = onNavigate)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Estado de la solicitud
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
                        Text(statusTitle, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = statusColor)
                        Text(statusSubtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(Modifier.width(8.dp))
                    TeacherStatusPill(statusBadge, statusColor)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Resumen del contenido
            TeacherCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TeacherStatusPill("CIENCIAS · UNIDAD 2", UtpAccent)
                        Text(
                            "Versión 1.2",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TeacherMuted
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        "La energía se transforma",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Secuencia didáctica con explicación, apoyo visual, audio y actividad.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        TagChip("OA 9")
                        TagChip("15 min")
                        TagChip("6.° A")
                        TagChip("3 intentos")
                    }

                    Spacer(Modifier.height(14.dp))

                    OutlinedButton(
                        onClick = { onNavigate(AppScreen.DIDACTIC_CONTENT) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, UtpAccent)
                    ) {
                        Icon(
                            Icons.Default.Visibility,
                            contentDescription = null,
                            tint = UtpAccent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "Abrir vista previa del contenido",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = UtpAccent
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // Criterios
            TeacherSectionHeader(
                title = "Criterios de revisión",
                trailing = {
                    Text(
                        "$conformCount de $total conformes",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )

            if (total > 0) {
                Spacer(Modifier.height(10.dp))
                TeacherProgressBar(progress = conformCount / total.toFloat(), color = TeacherOk)
            }

            Spacer(Modifier.height(12.dp))

            criteriaList.forEach { criterion ->
                CriterionCard(
                    title = criterion.title,
                    subtitle = criterion.subtitle,
                    status = if (isAutorizado) "Conforme" else criterion.status
                )
                Spacer(Modifier.height(10.dp))
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun TagChip(text: String) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun CriterionCard(title: String, subtitle: String, status: String) {
    val isConforme = status == "Conforme"
    val color = if (isConforme) TeacherOk else TeacherWarn

    TeacherCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isConforme) Icons.Default.Check else Icons.Default.Warning,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(Modifier.width(8.dp))

            TeacherStatusPill(status, color)
        }
    }
}