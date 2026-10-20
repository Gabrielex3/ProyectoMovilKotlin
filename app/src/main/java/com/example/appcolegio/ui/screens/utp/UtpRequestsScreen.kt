package com.example.appcolegio.ui.screens.utp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.ui.AppScreen
import com.example.appcolegio.ui.screens.teacher.TeacherCard
import com.example.appcolegio.ui.screens.teacher.TeacherDanger
import com.example.appcolegio.ui.screens.teacher.TeacherInfo
import com.example.appcolegio.ui.screens.teacher.TeacherOk
import com.example.appcolegio.ui.screens.teacher.TeacherSectionHeader
import com.example.appcolegio.ui.screens.teacher.TeacherStatusPill
import com.example.appcolegio.ui.screens.teacher.TeacherTopBar
import com.example.appcolegio.ui.screens.teacher.TeacherWarn

@Composable
fun UtpRequestsScreen(
    utpStatus: String,
    onSelectRequest: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    val allUnits = mutableListOf<Triple<String, String, String>>() // id, title, subjectName
    MockData.unitsBySubject.forEach { (subjId, units) ->
        val subjName = MockData.subjects.find { it.id == subjId }?.name ?: "Asignatura"
        units.forEach { u ->
            allUnits.add(Triple(u.id, "Unidad ${u.unitNumber}: ${u.unitTitle}", subjName))
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Solicitudes UTP",
                subtitle = "Jefatura Técnico-Pedagógica • Segundo Ciclo",
                trailing = { UtpSwitchProfileButton { onNavigate(AppScreen.PROFILE_SELECTION) } }
            )
        },
        bottomBar = { UtpBottomBar(selected = 0, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            TeacherSectionHeader(
                title = "Unidades enviadas por docentes",
                caption = "${allUnits.size} ${if (allUnits.size == 1) "solicitud" else "solicitudes"} para aprobación"
            )

            Spacer(Modifier.height(12.dp))

            if (allUnits.isEmpty()) {
                Text(
                    "No hay solicitudes por revisar.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            allUnits.forEach { (unitId, unitTitle, subjName) ->
                RequestCard(
                    subjectName = subjName,
                    unitTitle = unitTitle,
                    teacherName = "Camila Rojas (6.° A)",
                    status = utpStatus,
                    onClick = {
                        onSelectRequest(unitId)
                        onNavigate(AppScreen.UTP_REVIEW)
                    }
                )
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun RequestCard(
    subjectName: String,
    unitTitle: String,
    teacherName: String,
    status: String,
    onClick: () -> Unit
) {
    val isAutorizado = status == "AUTORIZADO"
    val isCambios = status == "CAMBIOS_SOLICITADOS"
    val statusColor = when {
        isAutorizado -> TeacherOk
        isCambios -> TeacherDanger
        else -> TeacherWarn
    }
    val statusText = when {
        isAutorizado -> "Autorizado"
        isCambios -> "Cambios solicitados"
        else -> "Pendiente"
    }

    TeacherCard(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeacherStatusPill(subjectName, TeacherInfo)
                TeacherStatusPill(statusText, statusColor)
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = unitTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = "Docente: $teacherName • Envío reciente",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(12.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Revisar solicitud",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = UtpAccent
                )
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = UtpAccent,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}