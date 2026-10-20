package com.example.appcolegio.ui.screens.student

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.data.model.SubjectItem
import com.example.appcolegio.data.model.UserProfile
import com.example.appcolegio.ui.AppScreen

@Composable
fun StudentHomeScreen(
    user: UserProfile,
    subjects: List<SubjectItem>,
    onSelectSubject: (String) -> Unit,
    onNavigate: (AppScreen) -> Unit
) {
    val overall = if (subjects.isEmpty()) 70
    else subjects.map { it.progressPercent }.average().toInt()
    val level = overall / 10 + 1
    val firstName = user.name.split(" ").first()

    val defaults = listOf(
        Triple("Ciencias Naturales", "Unidad 2: La energía", 44),
        Triple("Lenguaje y Com.", "Unidad 1: Mitos y leyendas", 72),
        Triple("Matemática", "Unidad 1: Fracciones", 58),
        Triple("Historia y C. S.", "Unidad 1: Chile republicano", 31)
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { StudentBottomBar(selected = 0, onNavigate = onNavigate) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // ---------- Bienvenida ----------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(Brush.verticalGradient(listOf(KidBlue, KidBlueDark)))
                    .statusBarsPadding()
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(KidYellow)
                            .clickable { onNavigate(AppScreen.PROFILE_SELECTION) },
                        contentAlignment = Alignment.Center
                    ) { Text("🧑‍🚀", fontSize = 40.sp) }

                    Spacer(Modifier.width(14.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            "¡Hola, $firstName! 👋",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            "${user.courseInfo} • Martes 2 de octubre",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Spacer(Modifier.height(6.dp))
                        KidsChip(
                            text = "🔄 Cambiar perfil",
                            bg = Color.White.copy(alpha = 0.2f),
                            fg = Color.White,
                            modifier = Modifier.clickable { onNavigate(AppScreen.PROFILE_SELECTION) }
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    KidsChip("⭐ Nivel $level", KidYellow, KidInk)
                    Spacer(Modifier.weight(1f))
                    Text(
                        "$overall% de tus aventuras",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(Modifier.height(8.dp))
                KidsProgressBar(overall / 100f, KidYellow, KidBlueDark, 14.dp)
                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        onSelectSubject("SUBJ_1")
                        onNavigate(AppScreen.UNIT_DETAIL)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = KidOrange),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        "⚡ Continúa: \"La energía se transforma\"",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                }
            }

            // ---------- Contenido ----------
            Column(Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    KidsSectionTitle("🚀 Tus aventuras de aprendizaje")
                    Text(
                        text = "Ver todas ➔",
                        fontSize = 13.sp,
                        color = KidBlue,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.clickable { onNavigate(AppScreen.SUBJECTS_LIST) }
                    )
                }

                Spacer(Modifier.height(12.dp))

                (0..3).map { i -> subjects.getOrNull(i) to defaults[i] }
                    .chunked(2)
                    .forEach { pair ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            pair.forEach { (s, d) ->
                                SubjectCard(
                                    title = s?.name ?: d.first,
                                    subtitle = s?.subtitle ?: d.second,
                                    percent = s?.progressPercent ?: d.third,
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight(),
                                    onClick = {
                                        if (s != null) onSelectSubject(s.id)
                                        onNavigate(AppScreen.UNIT_DETAIL)
                                    }
                                )
                            }
                        }
                        Spacer(Modifier.height(12.dp))
                    }

                Spacer(Modifier.height(12.dp))
                KidsSectionTitle("📅 Para hoy")
                Spacer(Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .clickable {
                            onSelectSubject("SUBJ_1")
                            onNavigate(AppScreen.DIDACTIC_CONTENT)
                        },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = KidOrange.copy(alpha = 0.14f)),
                    border = BorderStroke(2.dp, KidOrange),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔬", fontSize = 36.sp)
                        Spacer(Modifier.width(12.dp))
                        Column(Modifier.weight(1f)) {
                            Text(
                                "Actividad de Ciencias",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "⏱️ 10 min aprox.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        KidsChip("¡Nueva!", KidOrange, Color.White)
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun SubjectCard(
    title: String,
    subtitle: String,
    percent: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val style = kidSubjectStyle(title)

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(3.dp, style.color),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(style.color.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) { Text(style.emoji, fontSize = 28.sp) }
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = if (percent >= 100) "🏆" else "$percent%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = style.color
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Column {
                Spacer(Modifier.height(10.dp))
                KidsProgressBar(percent / 100f, style.color, style.color.copy(alpha = 0.18f), 10.dp)
            }
        }
    }
}