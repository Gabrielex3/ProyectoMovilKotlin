package com.example.appcolegio.ui.screens.teacher

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.AssignmentConfig
import com.example.appcolegio.ui.AppScreen

@Composable
fun ContentAssignmentScreen(
    currentConfig: AssignmentConfig,
    onSaveConfig: (AssignmentConfig) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    var maxAttempts by remember { mutableIntStateOf(currentConfig.maxAttempts) }
    var showCorrection by remember { mutableStateOf(currentConfig.showCorrection) }
    var isPublished by remember { mutableStateOf(currentConfig.isPublished) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Nueva asignación",
                subtitle = "Docente • Curso 6.° A",
                onBack = { onNavigate(AppScreen.TEACHER_HOME) },
                trailing = { TeacherStatusPill("Borrador", TeacherWarn) }
            )
        },
        bottomBar = {
            TeacherActionBar {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(
                        onClick = { onNavigate(AppScreen.CREATE_QUIZ) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, TeacherAccent)
                    ) {
                        Text("Crear quiz", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TeacherAccent)
                    }
                    Button(
                        onClick = {
                            onSaveConfig(
                                AssignmentConfig(
                                    maxAttempts = maxAttempts,
                                    showCorrection = showCorrection,
                                    isPublished = isPublished
                                )
                            )
                            onNavigate(AppScreen.PEDAGOGICAL_REPORT)
                        },
                        modifier = Modifier
                            .weight(1.4f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = TeacherAccent)
                    ) {
                        Text("Revisar asignación", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }
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
            // Pasos
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StepIndicator("1", "Contenido", isDone = true, isActive = false)
                StepConnector(done = true, modifier = Modifier.weight(1f))
                StepIndicator("2", "Configurar", isDone = false, isActive = true)
                StepConnector(done = false, modifier = Modifier.weight(1f))
                StepIndicator("3", "Confirmar", isDone = false, isActive = false)
            }

            Spacer(Modifier.height(20.dp))

            // Contenido seleccionado
            TeacherCard(modifier = Modifier.fillMaxWidth(), selected = true) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TeacherStatusPill("CIENCIAS · OA 9", TeacherAccent)
                        Text(
                            "Cambiar",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TeacherAccent,
                            modifier = Modifier.clickable { }
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                    Text(
                        "La energía se transforma",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "Contenido + actividad de 5 preguntas • 15 min",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            TeacherSectionHeader("Configuración de la actividad")
            Spacer(Modifier.height(14.dp))

            SelectField("Curso destinatario", "6.° A • 34 estudiantes")
            Spacer(Modifier.height(14.dp))
            SelectField("Fecha de entrega", "Viernes 9 de octubre · 18:00")

            Spacer(Modifier.height(20.dp))

            // Intentos
            TeacherCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                "Límite de intentos",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Veces que cada estudiante puede rendir",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        StepperButton(Icons.Default.Remove, "Restar") { if (maxAttempts > 1) maxAttempts-- }
                        Text(
                            text = if (maxAttempts > 10) "∞" else maxAttempts.toString(),
                            modifier = Modifier.width(36.dp),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        StepperButton(Icons.Default.Add, "Sumar") { maxAttempts++ }
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AttemptOptionChip("1", maxAttempts == 1, Modifier.weight(1f)) { maxAttempts = 1 }
                        AttemptOptionChip("2", maxAttempts == 2, Modifier.weight(1f)) { maxAttempts = 2 }
                        AttemptOptionChip("3", maxAttempts == 3, Modifier.weight(1f)) { maxAttempts = 3 }
                        AttemptOptionChip("Sin límite", maxAttempts > 10, Modifier.weight(1.6f)) { maxAttempts = 99 }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // Opciones
            TeacherCard(modifier = Modifier.fillMaxWidth()) {
                ToggleRow(
                    title = "Mostrar corrección al finalizar",
                    description = "Indica respuestas correctas e incorrectas",
                    checked = showCorrection,
                    onChange = { showCorrection = it }
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.outlineVariant)
                )
                ToggleRow(
                    title = "Publicar ahora",
                    description = "Disponible inmediatamente para el curso",
                    checked = isPublished,
                    onChange = { isPublished = it }
                )
            }

            Spacer(Modifier.height(14.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = TeacherWarn.copy(alpha = 0.10f)),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Text(
                    text = "Se guardará como borrador hasta confirmar la asignación.",
                    modifier = Modifier.padding(12.dp),
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun SelectField(label: String, value: String) {
    Column(Modifier.fillMaxWidth()) {
        Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(4.dp))
        TeacherCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    value,
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun StepperButton(icon: ImageVector, description: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = description, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun ToggleRow(
    title: String,
    description: String,
    checked: Boolean,
    onChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            Text(description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = TeacherAccent)
        )
    }
}

@Composable
private fun StepConnector(done: Boolean, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(horizontal = 8.dp)
            .height(2.dp)
            .background(if (done) TeacherOk else MaterialTheme.colorScheme.outlineVariant)
    )
}

@Composable
fun StepIndicator(step: String, label: String, isDone: Boolean, isActive: Boolean) {
    val circleBg = when {
        isDone -> TeacherOk
        isActive -> TeacherAccent
        else -> Color.Transparent
    }
    val borderCol = if (isDone || isActive) Color.Transparent else MaterialTheme.colorScheme.outline

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(circleBg)
                .border(1.dp, borderCol, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isDone) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(15.dp))
            } else {
                Text(
                    step,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.width(6.dp))
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = if (isActive || isDone) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isActive || isDone) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun AttemptOptionChip(
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) TeacherAccent.copy(alpha = 0.12f) else Color.Transparent,
        border = BorderStroke(
            if (isSelected) 1.5.dp else 1.dp,
            if (isSelected) TeacherAccent else MaterialTheme.colorScheme.outline
        )
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(vertical = 9.dp),
            textAlign = TextAlign.Center,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) TeacherAccent else MaterialTheme.colorScheme.onSurface
        )
    }
}