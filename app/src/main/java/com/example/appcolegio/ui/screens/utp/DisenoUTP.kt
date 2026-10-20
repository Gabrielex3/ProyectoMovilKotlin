package com.example.appcolegio.ui.screens.utp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcolegio.ui.AppScreen

internal val UtpAccent = Color(0xFF7C3AED)

@Composable
internal fun UtpBottomBar(selected: Int, onNavigate: (AppScreen) -> Unit) {
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = UtpAccent,
        selectedTextColor = UtpAccent,
        indicatorColor = UtpAccent.copy(alpha = 0.14f)
    )
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = selected == 0,
            onClick = { onNavigate(AppScreen.UTP_HOME) },
            icon = { Icon(Icons.Default.Verified, contentDescription = "Solicitudes") },
            label = { Text("Solicitudes") },
            colors = colors
        )
        NavigationBarItem(
            selected = selected == 1,
            onClick = { onNavigate(AppScreen.UTP_REVIEW) },
            icon = { Icon(Icons.Default.History, contentDescription = "Revisión") },
            label = { Text("Revisión") },
            colors = colors
        )
    }
}

@Composable
internal fun UtpSwitchProfileButton(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Text(
            text = "Cambiar perfil",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}