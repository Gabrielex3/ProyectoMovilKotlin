package com.example.appcolegio.ui.screens.teacher

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.local.MockData
import com.example.appcolegio.data.model.ContentItem
import com.example.appcolegio.data.model.InteractiveItem
import com.example.appcolegio.data.model.LearningObjective
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.data.model.UnitItem
import com.example.appcolegio.ui.AppScreen

@Composable
fun TeacherManagementScreen(
    subjects: List<SubjectItem>,
    onNavigate: (AppScreen) -> Unit
) {
    var selectedSubjectId by remember { mutableStateOf(subjects.firstOrNull()?.id ?: "SUBJ_1") }
    var editingUnitId by remember { mutableStateOf<String?>(null) }

    var unitTitle by remember { mutableStateOf("") }
    var unitDesc by remember { mutableStateOf("") }

    var oaCode by remember { mutableStateOf("") }
    var oaDesc by remember { mutableStateOf("") }
    val pendingOas = remember { mutableStateListOf<LearningObjective>() }

    var contentTitle by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    val pendingContents = remember { mutableStateListOf<ContentItem>() }

    var interTitle by remember { mutableStateOf("") }
    var interDesc by remember { mutableStateOf("") }
    val pendingInteractive = remember { mutableStateListOf<InteractiveItem>() }

    val context = LocalContext.current
    val unitsForSubj = MockData.unitsBySubject[selectedSubjectId] ?: mutableListOf()

    fun resetForm() {
        editingUnitId = null
        unitTitle = ""
        unitDesc = ""
        pendingOas.clear()
        pendingContents.clear()
        pendingInteractive.clear()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TeacherTopBar(
                title = "Gestión curricular",
                subtitle = "Docente • Unidades enviadas a UTP",
                onBack = { onNavigate(AppScreen.TEACHER_HOME) },
                trailing = {
                    if (editingUnitId != null) TeacherStatusPill("Editando", TeacherInfo)
                }
            )
        },
        bottomBar = {
            Column {
                TeacherActionBar {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (editingUnitId != null) {
                            OutlinedButton(
                                onClick = {
                                    resetForm()
                                    Toast.makeText(context, "Modo edición cancelado", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                            ) {
                                Text(
                                    "Cancelar",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Button(
                            onClick = {
                                if (unitTitle.isBlank()) {
                                    Toast.makeText(context, "Ingresa al menos el título de la unidad", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }

                                val list = MockData.unitsBySubject[selectedSubjectId]
                                if (list != null) {
                                    if (editingUnitId != null) {
                                        // Editar unidad existente
                                        val index = list.indexOfFirst { it.id == editingUnitId }
                                        if (index != -1) {
                                            val existing = list[index]
                                            list[index] = existing.copy(
                                                unitTitle = unitTitle.trim(),
                                                description = if (unitDesc.isNotBlank()) unitDesc.trim() else existing.description,
                                                learningObjectives = if (pendingOas.isNotEmpty()) pendingOas.toMutableList() else existing.learningObjectives,
                                                contents = if (pendingContents.isNotEmpty()) pendingContents.toMutableList() else existing.contents,
                                                interactiveContents = if (pendingInteractive.isNotEmpty()) pendingInteractive.toMutableList() else existing.interactiveContents,
                                                isAuthorizedByUtp = false // Al editar requiere nueva aprobación UTP
                                            )
                                            MockData.utpApprovalState = MockData.utpApprovalState.copy(status = "PENDIENTE")
                                            Toast.makeText(context, "Unidad actualizada y enviada a UTP para aprobación", Toast.LENGTH_LONG).show()
                                        }
                                    } else {
                                        // Crear nueva unidad (requiere aprobación UTP)
                                        val newUnit = UnitItem(
                                            id = "U_NEW_${System.currentTimeMillis()}",
                                            subjectId = selectedSubjectId,
                                            subjectName = subjects.find { it.id == selectedSubjectId }?.name ?: "Asignatura",
                                            unitNumber = list.size + 1,
                                            unitTitle = unitTitle.trim(),
                                            description = if (unitDesc.isNotBlank()) unitDesc.trim() else "Unidad creada por docente",
                                            progressPercent = 0,
                                            isAuthorizedByUtp = false, // Nuevas unidades pendientes de UTP
                                            learningObjectives = pendingOas.toMutableList(),
                                            contents = pendingContents.toMutableList(),
                                            interactiveContents = pendingInteractive.toMutableList()
                                        )
                                        MockData.utpApprovalState = MockData.utpApprovalState.copy(status = "PENDIENTE")
                                        list.add(newUnit)
                                        Toast.makeText(context, "Nueva unidad creada y enviada a UTP para autorización", Toast.LENGTH_LONG).show()
                                    }

                                    resetForm()
                                }
                            },
                            modifier = Modifier
                                .weight(2f)
                                .height(48.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TeacherAccent)
                        ) {
                            Text(
                                if (editingUnitId != null) "Guardar y enviar a UTP" else "Crear y enviar a UTP",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }
                TeacherBottomBar(selected = 1, onNavigate = onNavigate)
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
            // ---------- 1. Asignatura ----------
            TeacherStepTitle(1, "Asignatura")
            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                subjects.forEach { subj ->
                    SubjectChip(
                        label = subj.name,
                        isSelected = selectedSubjectId == subj.id,
                        onClick = {
                            selectedSubjectId = subj.id
                            resetForm()
                        }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ---------- 2. Unidades existentes ----------
            TeacherStepTitle(2, "Unidades existentes")
            Spacer(Modifier.height(2.dp))
            Text(
                "Toca una unidad para editarla.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 34.dp)
            )
            Spacer(Modifier.height(10.dp))

            if (unitsForSubj.isEmpty()) {
                Text(
                    "Esta asignatura aún no tiene unidades.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            unitsForSubj.forEach { unit ->
                val isEditing = editingUnitId == unit.id

                TeacherCard(
                    modifier = Modifier.fillMaxWidth(),
                    selected = isEditing,
                    onClick = {
                        editingUnitId = unit.id
                        unitTitle = unit.unitTitle
                        unitDesc = unit.description
                        pendingOas.clear()
                        pendingOas.addAll(unit.learningObjectives)
                        pendingContents.clear()
                        pendingContents.addAll(unit.contents)
                        pendingInteractive.clear()
                        pendingInteractive.addAll(unit.interactiveContents)
                        Toast.makeText(context, "Editando Unidad ${unit.unitNumber}", Toast.LENGTH_SHORT).show()
                    }
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Unidad ${unit.unitNumber}: ${unit.unitTitle}",
                                modifier = Modifier.weight(1f),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(Modifier.width(8.dp))
                            TeacherStatusPill(
                                text = if (unit.isAuthorizedByUtp) "Autorizada UTP" else "Pendiente UTP",
                                color = if (unit.isAuthorizedByUtp) TeacherOk else TeacherWarn
                            )
                        }
                        Spacer(Modifier.height(4.dp))
                        Text(
                            unit.description,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "${unit.learningObjectives.size} OAs  ·  ${unit.contents.size} contenidos  ·  ${unit.interactiveContents.size} interactivos",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TeacherMuted
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
            }

            Spacer(Modifier.height(16.dp))

            // ---------- 3. Formulario ----------
            TeacherStepTitle(
                3,
                if (editingUnitId != null) "Editando unidad seleccionada" else "Nueva unidad (se envía a UTP)"
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = unitTitle,
                onValueChange = { unitTitle = it },
                label = { Text("Título de la unidad") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = teacherFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = unitDesc,
                onValueChange = { unitDesc = it },
                label = { Text("Descripción general") },
                minLines = 2,
                shape = RoundedCornerShape(12.dp),
                colors = teacherFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(20.dp))

            // Objetivos de Aprendizaje
            CurriculumSection(
                title = "Objetivos de aprendizaje",
                accent = TeacherOk,
                items = pendingOas.map { "[${it.code}] ${it.description}" },
                addLabel = "Agregar OA",
                onAdd = {
                    if (oaCode.isNotBlank() && oaDesc.isNotBlank()) {
                        pendingOas.add(LearningObjective(oaCode.trim(), oaDesc.trim()))
                        oaCode = ""
                        oaDesc = ""
                    }
                }
            ) {
                OutlinedTextField(
                    value = oaCode,
                    onValueChange = { oaCode = it },
                    label = { Text("Código OA (ej: OA 12)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = teacherFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = oaDesc,
                    onValueChange = { oaDesc = it },
                    label = { Text("Descripción del OA") },
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp),
                    colors = teacherFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(14.dp))

            // Contenidos de Estudio
            CurriculumSection(
                title = "Contenidos de estudio",
                accent = TeacherInfo,
                items = pendingContents.map { it.title },
                addLabel = "Agregar contenido",
                onAdd = {
                    if (contentTitle.isNotBlank() && contentText.isNotBlank()) {
                        pendingContents.add(ContentItem("C_${System.currentTimeMillis()}", contentTitle.trim(), contentText.trim()))
                        contentTitle = ""
                        contentText = ""
                    }
                }
            ) {
                OutlinedTextField(
                    value = contentTitle,
                    onValueChange = { contentTitle = it },
                    label = { Text("Título del contenido") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = teacherFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = contentText,
                    onValueChange = { contentText = it },
                    label = { Text("Texto / explicación") },
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp),
                    colors = teacherFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(14.dp))

            // Contenido Interactivo
            CurriculumSection(
                title = "Contenido interactivo",
                accent = TeacherWarn,
                items = pendingInteractive.map { it.title },
                addLabel = "Agregar interactivo",
                onAdd = {
                    if (interTitle.isNotBlank()) {
                        pendingInteractive.add(InteractiveItem("I_${System.currentTimeMillis()}", interTitle.trim(), interDesc.trim(), "simulation"))
                        interTitle = ""
                        interDesc = ""
                    }
                }
            ) {
                OutlinedTextField(
                    value = interTitle,
                    onValueChange = { interTitle = it },
                    label = { Text("Título del recurso interactivo") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = teacherFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = interDesc,
                    onValueChange = { interDesc = it },
                    label = { Text("Descripción / instrucciones") },
                    minLines = 2,
                    shape = RoundedCornerShape(12.dp),
                    colors = teacherFieldColors(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

// ---------- Sección reutilizable: lista + campos + botón agregar ----------
@Composable
private fun CurriculumSection(
    title: String,
    accent: Color,
    items: List<String>,
    addLabel: String,
    onAdd: () -> Unit,
    fields: @Composable ColumnScope.() -> Unit
) {
    TeacherCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(accent)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    title,
                    modifier = Modifier.weight(1f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                TeacherStatusPill("${items.size} añadidos", accent)
            }

            if (items.isNotEmpty()) {
                Spacer(Modifier.height(10.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    items.forEach { text ->
                        Row(verticalAlignment = Alignment.Top) {
                            Text("•", fontSize = 12.sp, color = accent)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text,
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            fields()
            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = onAdd,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, accent)
            ) {
                androidx.compose.material3.Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(addLabel, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = accent)
            }
        }
    }
}

// ---------- Chip de asignatura ----------
@Composable
private fun SubjectChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
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
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) TeacherAccent else MaterialTheme.colorScheme.onSurface
        )
    }
}