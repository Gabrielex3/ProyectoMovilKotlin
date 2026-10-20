package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.appcolegio.ui.AppScreen

@Composable
fun DidacticContentScreen(
    onNavigate: (AppScreen) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            KidsTopBar(
                title = "La energía se transforma",
                subtitle = "🔬 Ciencias • Unidad 2",
                color = KidGreen,
                onBack = { onNavigate(AppScreen.UNIT_DETAIL) },
                trailing = {
                    IconButton(onClick = { }) { Text("🔖", fontSize = 22.sp) }
                },
                bottom = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Paso 2 de 3", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("66%", fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = KidYellow)
                    }
                    KidsProgressBar(
                        progress = 0.66f,
                        color = KidYellow,
                        track = Color.White.copy(alpha = 0.25f),
                        height = 12.dp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            KidsChip("💡 IDEA CLAVE", KidGreen, Color.White)

            Spacer(Modifier.height(10.dp))

            Text(
                text = "La energía no desaparece: cambia de una forma a otra",
                fontSize = 21.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 27.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Cuando la luz del Sol llega a un panel solar, se transforma en energía eléctrica. Esa electricidad puede encender una lámpara y convertirse en luz y calor.",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(20.dp))

            // Diagrama
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(3.dp, KidGreen),
                elevation = CardDefaults.cardElevation(4.dp)
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
                        DiagramNode("☀️ Solar", "Energía inicial", Color(0xFFDBEAFE), Color(0xFF1D4ED8))
                        Text("➔", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = KidOrange)
                        DiagramNode("⚡ Eléctrica", "Transformación", Color(0xFFFEF3C7), Color(0xFFB45309))
                        Text("➔", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = KidOrange)
                        DiagramNode("💡 Luz", "Resultado", Color(0xFFDCFCE7), Color(0xFF15803D))
                    }

                    Spacer(Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(KidGreen.copy(alpha = 0.12f), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Text("ℹ️ ", fontSize = 14.sp)
                        Text(
                            text = "Una transformación puede producir más de una forma de energía.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // Audio
            OutlinedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(24.dp),
                border = BorderStroke(2.dp, KidBlue)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(KidBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("▶", color = Color.White, fontSize = 14.sp)
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "🎧 Escuchar explicación",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.weight(1f))
                    Text("1:24", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Pregunta de reflexión
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF7D6)),
                border = BorderStroke(2.dp, KidYellow),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🤔 ", fontSize = 26.sp)
                    Text(
                        text = "Piensa: ¿qué transformación de energía ocurre cuando enciendes una tostadora?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF7A4B0B),
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = { onNavigate(AppScreen.QUIZ_ACTIVITY) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = KidOrange),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text("¡Ir a la actividad! 🚀", fontSize = 17.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun DiagramNode(title: String, subtitle: String, bg: Color, textColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(bg)
                .padding(horizontal = 10.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(title, fontSize = 12.sp, fontWeight = FontWeight.ExtraBold, color = textColor)
        }
        Spacer(Modifier.height(4.dp))
        Text(subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}