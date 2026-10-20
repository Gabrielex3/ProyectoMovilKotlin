package com.example.appcolegio.ui.screens.student

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.ui.AppScreen

// ---------- Paleta ----------
internal val KidBlue = Color(0xFF2F80ED)
internal val KidBlueDark = Color(0xFF1B5FC1)
internal val KidOrange = Color(0xFFFF8A3D)
internal val KidGreen = Color(0xFF27AE60)
internal val KidYellow = Color(0xFFFFC93C)
internal val KidPurple = Color(0xFF8E5CF7)
internal val KidRed = Color(0xFFEB5757)
internal val KidInk = Color(0xFF1F2A44) // texto sobre amarillo

// ---------- Estilo por asignatura ----------
internal data class KidSubjectStyle(val emoji: String, val color: Color)

internal fun kidSubjectStyle(name: String, fallback: Color = KidBlue): KidSubjectStyle {
    val n = name.lowercase()
    return when {
        n.contains("cien") -> KidSubjectStyle("🔬", KidGreen)
        n.contains("mat") -> KidSubjectStyle("🧮", KidBlue)
        n.contains("leng") -> KidSubjectStyle("📚", KidOrange)
        n.contains("hist") -> KidSubjectStyle("🏛️", KidPurple)
        else -> KidSubjectStyle("⭐", fallback)
    }
}

// ---------- Barra de progreso animada ----------
@Composable
internal fun KidsProgressBar(
    progress: Float,
    color: Color,
    track: Color,
    height: Dp = 12.dp,
    modifier: Modifier = Modifier
) {
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 900),
        label = "kidsProgress"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(track)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animated)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(color)
        )
    }
}

// ---------- Chip / etiqueta ----------
@Composable
internal fun KidsChip(text: String, bg: Color, fg: Color, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(50), color = bg) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            color = fg,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
internal fun KidsSectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 17.sp,
        fontWeight = FontWeight.ExtraBold,
        color = MaterialTheme.colorScheme.onSurface
    )
}

// ---------- Barra superior con degradado ----------
@Composable
internal fun KidsTopBar(
    title: String,
    subtitle: String,
    color: Color = KidBlue,
    onBack: (() -> Unit)? = null,
    trailing: @Composable () -> Unit = {},
    bottom: @Composable ColumnScope.() -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(Brush.verticalGradient(listOf(color, lerp(color, Color.Black, 0.25f))))
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color.White)
                }
            } else {
                Spacer(Modifier.width(12.dp))
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            trailing()
        }
        bottom()
    }
}

// ---------- Barra inferior del estudiante ----------
@Composable
internal fun StudentBottomBar(selected: Int, onNavigate: (AppScreen) -> Unit) {
    val items = listOf(
        Triple("🏠", "Inicio", AppScreen.STUDENT_HOME),
        Triple("📚", "Asignaturas", AppScreen.SUBJECTS_LIST),
        Triple("📝", "Actividades", AppScreen.STUDENT_ACTIVITIES)
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEachIndexed { index, (emoji, label, screen) ->
            NavigationBarItem(
                selected = selected == index,
                onClick = { onNavigate(screen) },
                icon = { Text(emoji, fontSize = 24.sp) },
                label = { Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = KidYellow.copy(alpha = 0.5f)
                )
            )
        }
    }
}