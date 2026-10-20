package com.example.appcolegio.ui.screens.teacher

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.ui.AppScreen

// ---------- Paleta ----------
internal val TeacherAccent = Color(0xFF0D9488)
internal val TeacherOk = Color(0xFF16A34A)
internal val TeacherWarn = Color(0xFFD97706)
internal val TeacherDanger = Color(0xFFDC2626)
internal val TeacherInfo = Color(0xFF2563EB)
internal val TeacherMuted = Color(0xFF64748B)

internal fun teacherProgressColor(percent: Float): Color = when {
    percent >= 70f -> TeacherOk
    percent >= 50f -> TeacherAccent
    else -> TeacherWarn
}

// ---------- Barra superior ----------
@Composable
internal fun TeacherTopBar(
    title: String,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    leading: (@Composable () -> Unit)? = null,
    trailing: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                onBack != null -> IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
                leading != null -> {
                    Spacer(Modifier.width(8.dp))
                    leading()
                    Spacer(Modifier.width(12.dp))
                }
                else -> Spacer(Modifier.width(12.dp))
            }
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            trailing()
            Spacer(Modifier.width(8.dp))
        }
        Box(
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )
    }
}

// ---------- Barra inferior ----------
@Composable
internal fun TeacherBottomBar(selected: Int, onNavigate: (AppScreen) -> Unit) {
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = TeacherAccent,
        selectedTextColor = TeacherAccent,
        indicatorColor = TeacherAccent.copy(alpha = 0.14f)
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = selected == 0,
            onClick = { onNavigate(AppScreen.TEACHER_HOME) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Curso") },
            label = { Text("Curso") },
            colors = colors
        )
        NavigationBarItem(
            selected = selected == 1,
            onClick = { onNavigate(AppScreen.CONTENT_ASSIGNMENT) },
            icon = { Icon(Icons.AutoMirrored.Filled.Assignment, contentDescription = "Asignar") },
            label = { Text("Asignar") },
            colors = colors
        )
        NavigationBarItem(
            selected = selected == 2,
            onClick = { onNavigate(AppScreen.PEDAGOGICAL_REPORT) },
            icon = { Icon(Icons.Default.Analytics, contentDescription = "Informes") },
            label = { Text("Informes") },
            colors = colors
        )
    }
}

// ---------- Barra de acciones fija (abajo) ----------
@Composable
internal fun TeacherActionBar(
    applyNavigationInsets: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        Column {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(if (applyNavigationInsets) Modifier.navigationBarsPadding() else Modifier)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                content = content
            )
        }
    }
}

// ---------- Tarjeta base ----------
@Composable
internal fun TeacherCard(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(14.dp)
    val m = if (onClick != null) modifier.clip(shape).clickable(onClick = onClick) else modifier
    Card(
        modifier = m,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) TeacherAccent.copy(alpha = 0.07f) else MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            if (selected) 1.5.dp else 1.dp,
            if (selected) TeacherAccent else MaterialTheme.colorScheme.outlineVariant
        ),
        elevation = CardDefaults.cardElevation(0.dp),
        content = content
    )
}

// ---------- Etiqueta de estado ----------
@Composable
internal fun TeacherStatusPill(text: String, color: Color, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, shape = RoundedCornerShape(6.dp), color = color.copy(alpha = 0.12f)) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

// ---------- Encabezado de sección ----------
@Composable
internal fun TeacherSectionHeader(
    title: String,
    caption: String? = null,
    trailing: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
            if (caption != null) {
                Text(caption, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        trailing?.invoke()
    }
}

@Composable
internal fun TeacherStepTitle(number: Int, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(TeacherAccent),
            contentAlignment = Alignment.Center
        ) {
            Text("$number", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.width(10.dp))
        Text(title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
    }
}

// ---------- Barras de progreso ----------
@Composable
internal fun TeacherProgressBar(
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier.fillMaxWidth(),
    height: Dp = 8.dp
) {
    val animated by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(700),
        label = "teacherProgress"
    )
    Box(
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            Modifier
                .fillMaxWidth(animated)
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(color)
        )
    }
}

@Composable
internal fun TeacherDistributionBar(segments: List<Pair<Int, Color>>, height: Dp = 10.dp) {
    val visible = segments.filter { it.first > 0 }
    if (visible.isEmpty()) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50)),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        visible.forEach { (count, color) ->
            Box(
                Modifier
                    .weight(count.toFloat())
                    .fillMaxHeight()
                    .background(color)
            )
        }
    }
}

@Composable
internal fun TeacherLegendItem(color: Color, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(Modifier.width(6.dp))
        Text(text, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// ---------- Campos de texto ----------
@Composable
internal fun teacherFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = TeacherAccent,
    focusedLabelColor = TeacherAccent,
    cursorColor = TeacherAccent
)