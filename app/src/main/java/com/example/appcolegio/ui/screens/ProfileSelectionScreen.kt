package com.example.appcolegio.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.UserProfile

// ---------- Paleta ----------
private val BgSoft = Color(0xFFF4F9FF)
private val BlueMain = Color(0xFF2F80ED)
private val BlueDark = Color(0xFF1B5FC1)
private val OrangeWarm = Color(0xFFFF8A3D)
private val GreenFresh = Color(0xFF27AE60)
private val YellowStar = Color(0xFFFFC93C)
private val PurpleUtp = Color(0xFF8E5CF7)
private val TextDark = Color(0xFF1F2A44)
private val TextSoft = Color(0xFF5B6B8C)

private data class RoleStyle(
    val emoji: String,
    val color: Color,
    val tagline: String
)

private fun roleStyle(role: String): RoleStyle = when (role) {
    "Estudiante" -> RoleStyle("🧑‍🚀", OrangeWarm, "¡Juega y aprende!")
    "Docente" -> RoleStyle("🍎", GreenFresh, "Prepara tus clases")
    else -> RoleStyle("🧭", PurpleUtp, "Organiza las unidades")
}

// ---------- Pantalla ----------
@Composable
fun ProfileSelectionScreen(
    profiles: List<UserProfile>,
    onProfileSelected: (UserProfile) -> Unit
) {
    var selectedProfile by remember { mutableStateOf(profiles.first()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BgSoft
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ----- Cabecera -----
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp))
                    .background(Brush.verticalGradient(listOf(BlueMain, BlueDark)))
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(84.dp)
                            .clip(CircleShape)
                            .background(YellowStar),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🚀", fontSize = 44.sp)
                    }

                    Spacer(Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "MPAZ PRO 6.º",
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp),
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "¡Aprendamos juntos,\npaso a paso!",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "Escuela Escritora Marcela Paz",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                }
            }

            // ----- Contenido -----
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "👋 ¿Quién eres hoy?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = TextDark
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Elige un perfil para recorrer el prototipo.",
                        fontSize = 13.sp,
                        color = TextSoft
                    )
                    Spacer(Modifier.height(16.dp))

                    profiles.forEach { profile ->
                        ProfileCard(
                            profile = profile,
                            isSelected = selectedProfile.id == profile.id,
                            onClick = { selectedProfile = profile }
                        )
                        Spacer(Modifier.height(14.dp))
                    }
                }

                Spacer(Modifier.height(4.dp))

                // Nota de privacidad
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F7EE))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🛡️", fontSize = 24.sp)
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Acceso demostrativo. No solicita cámara, GPS, micrófono ni biometría.",
                            fontSize = 12.sp,
                            color = Color(0xFF1E6B3E),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = { onProfileSelected(selectedProfile) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeWarm),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        text = "¡Entrar como ${selectedProfile.name.split(" ").first()}! 🚀",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Datos y personas exclusivamente ficticios.",
                    fontSize = 11.sp,
                    color = TextSoft
                )
            }
        }
    }
}

// ---------- Tarjeta de perfil ----------
@Composable
fun ProfileCard(
    profile: UserProfile,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val style = roleStyle(profile.role)

    val borderColor by animateColorAsState(
        targetValue = if (isSelected) style.color else Color(0xFFDCE6F5),
        label = "borderColor"
    )
    val cardBg by animateColorAsState(
        targetValue = if (isSelected) style.color.copy(alpha = 0.12f) else Color.White,
        label = "cardBg"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(if (isSelected) 3.dp else 1.5.dp, borderColor),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(if (isSelected) 6.dp else 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(style.color.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Text(style.emoji, fontSize = 32.sp)
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = profile.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextDark
                )
                Text(
                    text = profile.courseInfo,
                    fontSize = 12.sp,
                    color = TextSoft
                )
                Spacer(Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(50),
                    color = style.color
                ) {
                    Text(
                        text = "${profile.role} · ${style.tagline}",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isSelected) {
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(YellowStar),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
                }
            }
        }
    }
}