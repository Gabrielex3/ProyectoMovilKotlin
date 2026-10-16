package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
fun DidacticContentScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { onNavigate(AppScreen.UNIT_DETAIL) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("La energía se transforma", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                        Text("Ciencias • Unidad 2", fontSize = 12.sp, color = Color(0xFF64748B))
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.BookmarkBorder, contentDescription = "Guardar")
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("2 de 3", fontSize = 11.sp, color = Color(0xFF64748B))
                    Text("66%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2563EB))
                }

                LinearProgressIndicator(
                    progress = { 0.66f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = Color(0xFF2563EB),
                    trackColor = Color(0xFFE2E8F0)
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
            // Idea Clave Box
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFECFDF5)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("💡 IDEA CLAVE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF047857))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "La energía no desaparece: cambia de una forma a otra",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E293B)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Cuando la luz del Sol llega a un panel solar, se transforma en energía eléctrica. Esa electricidad puede encender una lámpara y convertirse en luz y calor.",
                fontSize = 13.sp,
                color = Color(0xFF475569),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Transformation Diagram Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DiagramNode("☀️ Solar", "Energía inicial", Color(0xFFEFF6FF), Color(0xFF1D4ED8))
                        Text("➔", fontSize = 18.sp, color = Color(0xFF94A3B8))
                        DiagramNode("⚡ Eléctrica", "Transformación", Color(0xFFFEF3C7), Color(0xFFD97706))
                        Text("➔", fontSize = 18.sp, color = Color(0xFF94A3B8))
                        DiagramNode("💡 Luz", "Resultado", Color(0xFFDCFCE7), Color(0xFF15803D))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF0FDF4), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("ℹ️ ", fontSize = 12.sp)
                        Text(
                            text = "Una transformación puede producir más de una forma de energía.",
                            fontSize = 11.sp,
                            color = Color(0xFF166534)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Audio Player Button
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF2563EB)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Escuchar explicación", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Spacer(modifier = Modifier.weight(1f))
                    Text("1:24", fontSize = 12.sp, color = Color(0xFF64748B))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reflection Prompt Box
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                border = BorderStroke(1.dp, Color(0xFFFDE68A))
            ) {
                Row(modifier = Modifier.padding(14.dp)) {
                    Text("❓ ", fontSize = 16.sp)
                    Text(
                        text = "Piensa: ¿qué transformación de energía ocurre cuando enciendes una tostadora?",
                        fontSize = 12.sp,
                        color = Color(0xFF92400E),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Button "Ir a la actividad"
            Button(
                onClick = { onNavigate(AppScreen.QUIZ_ACTIVITY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(26.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
            ) {
                Text("Ir a la actividad", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun DiagramNode(title: String, subtitle: String, bg: Color, textColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(bg)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(subtitle, fontSize = 10.sp, color = Color(0xFF64748B))
    }
}