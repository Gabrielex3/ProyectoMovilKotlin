package com.example.appcolegio.ui.screens.utp

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.ui.AppScreen

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
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Spacer(modifier = Modifier.height(28.dp)) // Safe camera punch-hole padding
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Historial de Solicitudes UTP", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Jefatura Técnico-Pedagógica • Segundo Ciclo", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF3E8FF)
                    ) {
                        Text(
                            text = "UTP 🔄",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            color = Color(0xFF6B21A8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.UTP_HOME) },
                    icon = { Icon(Icons.Default.Verified, contentDescription = "Solicitudes") },
                    label = { Text("Solicitudes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.UTP_REVIEW) },
                    icon = { Icon(Icons.Default.History, contentDescription = "Revisión") },
                    label = { Text("Revisión") }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Solicitudes de unidades enviadas por docentes para aprobación:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

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
    val badgeBg = when {
        isAutorizado -> Color(0xFFDCFCE7)
        isCambios -> Color(0xFFFEE2E2)
        else -> Color(0xFFFEF3C7)
    }
    val badgeColor = when {
        isAutorizado -> Color(0xFF15803D)
        isCambios -> Color(0xFFDC2626)
        else -> Color(0xFFB45309)
    }
    val badgeText = when {
        isAutorizado -> "Autorizado"
        isCambios -> "Cambios solicitados"
        else -> "Pendiente"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFEFF6FF)
                ) {
                    Text(
                        text = subjectName,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = Color(0xFF1D4ED8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = badgeBg
                ) {
                    Text(
                        text = badgeText,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        color = badgeColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = unitTitle,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Docente: $teacherName • Envío reciente",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Ver solicitud y aprobar ➔",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6B21A8)
                )
            }
        }
    }
}
