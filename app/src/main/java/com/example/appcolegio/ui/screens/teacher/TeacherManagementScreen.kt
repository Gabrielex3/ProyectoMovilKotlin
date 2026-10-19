package com.example.appcolegio.ui.screens.teacher

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val pendingOas = remember { mutableListOf<LearningObjective>() }

    var contentTitle by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    val pendingContents = remember { mutableListOf<ContentItem>() }

    var interTitle by remember { mutableStateOf("") }
    var interDesc by remember { mutableStateOf("") }
    val pendingInteractive = remember { mutableListOf<InteractiveItem>() }

    val context = LocalContext.current
    val unitsForSubj = MockData.unitsBySubject[selectedSubjectId] ?: mutableListOf()

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
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onNavigate(AppScreen.TEACHER_HOME) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Gestión y Edición Curricular", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        Text("Docente • Crear unidades enviadas a UTP", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.TEACHER_HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Curso") },
                    label = { Text("Curso") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.CONTENT_ASSIGNMENT) },
                    icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Asignar") },
                    label = { Text("Asignar") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.PEDAGOGICAL_REPORT) },
                    icon = { Icon(Icons.Default.Analytics, contentDescription = "Informes") },
                    label = { Text("Informes") }
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
            Text("1. Selecciona la Asignatura:", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                subjects.forEach { subj ->
                    val isSelected = selectedSubjectId == subj.id
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                selectedSubjectId = subj.id
                                editingUnitId = null
                                unitTitle = ""
                                unitDesc = ""
                                pendingOas.clear()
                                pendingContents.clear()
                                pendingInteractive.clear()
                            },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFCBD5E1))
                    ) {
                        Text(
                            text = subj.name.take(9),
                            modifier = Modifier.padding(vertical = 10.dp),
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("2. Unidades Existentes (Toca para Editar):", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))

            unitsForSubj.forEach { unit ->
                val isEditing = editingUnitId == unit.id
                val utpBadge = if (unit.isAuthorizedByUtp) "✅ Autorizada UTP" else "⏳ Pendiente UTP"
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            editingUnitId = unit.id
                            unitTitle = unit.unitTitle
                            unitDesc = unit.description
                            pendingOas.clear()
                            pendingOas.addAll(unit.learningObjectives)
                            pendingContents.clear()
                            pendingContents.addAll(unit.contents)
                            pendingInteractive.clear()
                            pendingInteractive.addAll(unit.interactiveContents)
                            Toast.makeText(context, "✏️ Editando Unidad ${unit.unitNumber}", Toast.LENGTH_SHORT).show()
                        },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(if (isEditing) 2.dp else 1.dp, if (isEditing) Color(0xFF2563EB) else Color.Transparent),
                    colors = CardDefaults.cardColors(containerColor = if (isEditing) Color(0xFFEFF6FF) else MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Unidad ${unit.unitNumber}: ${unit.unitTitle}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = if (unit.isAuthorizedByUtp) Color(0xFFDCFCE7) else Color(0xFFFEF3C7)
                            ) {
                                Text(utpBadge, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 10.sp, color = if (unit.isAuthorizedByUtp) Color(0xFF15803D) else Color(0xFFB45309), fontWeight = FontWeight.Bold)
                            }
                        }
                        Text(unit.description, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("• Objetivos: ${unit.learningObjectives.size} OAs | Contenidos: ${unit.contents.size} | Interactivos: ${unit.interactiveContents.size}", fontSize = 11.sp, color = Color(0xFF16A34A))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            val headerTitle = if (editingUnitId != null) "3. Editando Unidad Seleccionada:" else "3. Crear Nueva Unidad (Enviada a UTP):"
            Text(headerTitle, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = unitTitle,
                onValueChange = { unitTitle = it },
                label = { Text("Título de la Unidad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = unitDesc,
                onValueChange = { unitDesc = it },
                label = { Text("Descripción General") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Objetivos de Aprendizaje (OAs)
            Text("• Objetivos de Aprendizaje (${pendingOas.size} añadidos):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
            pendingOas.forEach { oa ->
                Text(" - [${oa.code}] ${oa.description}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = oaCode, onValueChange = { oaCode = it }, label = { Text("Código OA (ej: OA 12)") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = oaDesc, onValueChange = { oaDesc = it }, label = { Text("Descripción del OA") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (oaCode.isNotBlank() && oaDesc.isNotBlank()) {
                        pendingOas.add(LearningObjective(oaCode.trim(), oaDesc.trim()))
                        oaCode = ""
                        oaDesc = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().height(38.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A))
            ) {
                Text("➕ Agregar este OA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contenidos de Estudio
            Text("• Contenidos de Estudio (${pendingContents.size} añadidos):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
            pendingContents.forEach { c ->
                Text(" - ${c.title}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = contentTitle, onValueChange = { contentTitle = it }, label = { Text("Título del Contenido") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = contentText, onValueChange = { contentText = it }, label = { Text("Texto / Explicación") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (contentTitle.isNotBlank() && contentText.isNotBlank()) {
                        pendingContents.add(ContentItem("C_${System.currentTimeMillis()}", contentTitle.trim(), contentText.trim()))
                        contentTitle = ""
                        contentText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().height(38.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text("➕ Agregar Contenido", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contenido Interactivo
            Text("• Contenido Interactivo (${pendingInteractive.size} añadidos):", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD97706))
            pendingInteractive.forEach { i ->
                Text(" - ${i.title}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = interTitle, onValueChange = { interTitle = it }, label = { Text("Título del Recurso Interactivo") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(value = interDesc, onValueChange = { interDesc = it }, label = { Text("Descripción / Instrucciones") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    if (interTitle.isNotBlank()) {
                        pendingInteractive.add(InteractiveItem("I_${System.currentTimeMillis()}", interTitle.trim(), interDesc.trim(), "simulation"))
                        interTitle = ""
                        interDesc = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().height(38.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706))
            ) {
                Text("➕ Agregar Interactivo", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (editingUnitId != null) {
                    Button(
                        onClick = {
                            editingUnitId = null
                            unitTitle = ""
                            unitDesc = ""
                            pendingOas.clear()
                            pendingContents.clear()
                            pendingInteractive.clear()
                            Toast.makeText(context, "Modo edición cancelado", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.weight(1f).height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                    ) {
                        Text("Cancelar", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
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
                                    Toast.makeText(context, "✅ Unidad actualizada y enviada a UTP para aprobación", Toast.LENGTH_LONG).show()
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
                                Toast.makeText(context, "🚀 ¡Nueva unidad creada y enviada a UTP para autorización!", Toast.LENGTH_LONG).show()
                            }

                            editingUnitId = null
                            unitTitle = ""
                            unitDesc = ""
                            pendingOas.clear()
                            pendingContents.clear()
                            pendingInteractive.clear()
                        }
                    },
                    modifier = Modifier.weight(2f).height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(if (editingUnitId != null) "Guardar y Enviar a UTP" else "Crear y Enviar a UTP", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}
