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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.ui.AppScreen

@Composable
fun UnitDetailScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onNavigate(AppScreen.STUDENT_HOME) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Ciencias Naturales", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text("Unidad 2 de 4", fontSize = 12.sp, color = Color(0xFF64748B))
                }
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFDBEAFE),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = "Estudiante",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        color = Color(0xFF1E40AF),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.STUDENT_HOME) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
                    label = { Text("Inicio") }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onNavigate(AppScreen.UNIT_DETAIL) },
                    icon = { Icon(Icons.Default.Book, contentDescription = "Contenidos") },
                    label = { Text("Contenidos") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(AppScreen.QUIZ_ACTIVITY) },
                    icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Actividades") },
                    label = { Text("Actividades") }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Main Green Unit Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF059669))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF047857)
                        ) {
                            Text(
                                text = "UNIDAD 2",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text("44% completado", color = Color(0xFFD1FAE5), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "La energía y sus\ntransformaciones",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        lineHeight = 24.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Explora cómo la energía cambia, se transfiere y está presente en tu vida cotidiana.",
                        fontSize = 12.sp,
                        color = Color(0xFFECFDF5),
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LinearProgressIndicator(
                        progress = { 0.44f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = Color(0xFF34D399),
                        trackColor = Color(0xFF047857)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Objetivos de Aprendizaje
            Text("🎯 Objetivos de aprendizaje", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Spacer(modifier = Modifier.height(12.dp))

            OaCard(
                oaCode = "OA 8",
                text = "Explicar que la energía es necesaria para que los objetos cambien y los seres vivos realicen procesos vitales."
            )

            Spacer(modifier = Modifier.height(8.dp))

            OaCard(
                oaCode = "OA 9",
                text = "Investigar transformaciones de energía en situaciones simples del entorno."
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Ruta de aprendizaje
            Text("Ruta de aprendizaje", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))

            Spacer(modifier = Modifier.height(12.dp))

            RouteItemCard(
                numberStr = "1",
                title = "¿Qué es la energía?",
                subtitle = "Lectura y audio • 8 min",
                badgeText = "Completado",
                badgeColor = Color(0xFFD1FAE5),
                textColor = Color(0xFF047857),
                onClick = { onNavigate(AppScreen.DIDACTIC_CONTENT) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RouteItemCard(
                numberStr = "2",
                title = "La energía se transforma",
                subtitle = "Contenido interactivo • 10 min",
                badgeText = "Continuar",
                badgeColor = Color(0xFFDBEAFE),
                textColor = Color(0xFF1D4ED8),
                onClick = { onNavigate(AppScreen.DIDACTIC_CONTENT) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            RouteItemCard(
                numberStr = "3",
                title = "Comprueba lo aprendido",
                subtitle = "Actividad • 5 preguntas",
                badgeText = "Pendiente",
                badgeColor = Color(0xFFF1F5F9),
                textColor = Color(0xFF64748B),
                onClick = { onNavigate(AppScreen.QUIZ_ACTIVITY) }
            )
        }
    }
}

@Composable
fun OaCard(oaCode: String, text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = Color(0xFFD1FAE5)
            ) {
                Text(
                    text = oaCode,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = Color(0xFF047857),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                fontSize = 12.sp,
                color = Color(0xFF334155),
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun RouteItemCard(
    numberStr: String,
    title: String,
    subtitle: String,
    badgeText: String,
    badgeColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF1F5F9)),
                contentAlignment = Alignment.Center
            ) {
                Text(numberStr, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF1E293B))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                Text(subtitle, fontSize = 11.sp, color = Color(0xFF64748B))
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = badgeColor
            ) {
                Text(
                    text = badgeText,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = textColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
