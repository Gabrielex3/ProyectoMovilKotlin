package com.example.appcolegio

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.ui.AppScreen
import com.example.appcolegio.ui.MainViewModel
import com.example.appcolegio.ui.screens.student.StudentHomeScreen
import com.example.appcolegio.ui.screens.teacher.TeacherHomeScreen
import com.example.appcolegio.ui.screens.utp.UtpReviewScreen
import com.example.appcolegio.ui.theme.AppColegioTheme
import androidx.compose.foundation.layout.safeDrawingPadding

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = LocalDatabaseHelper(this)
        dbHelper.insertInitialUsers()

        setContent {
            AppColegioTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    // Usamos el enum LOGIN como punto de partida
    NavHost(navController = navController, startDestination = AppScreen.LOGIN.name) {

        composable(AppScreen.LOGIN.name) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable(AppScreen.STUDENT_HOME.name) {
            val studentProfile = viewModel.defaultProfiles().find { it.role == "Estudiante" } ?: viewModel.defaultProfiles().first()
            val subjects = viewModel.getSubjects()

            StudentHomeScreen(
                user = studentProfile,
                subjects = subjects,
                onNavigate = { screen: AppScreen -> navController.navigate(screen.name) }
            )
        }

        composable(AppScreen.TEACHER_HOME.name) {
            val teacherProfile = viewModel.defaultProfiles().find { it.role == "Docente" } ?: viewModel.defaultProfiles().first()

            TeacherHomeScreen(
                user = teacherProfile,
                onNavigate = { screen: AppScreen -> navController.navigate(screen.name) }
            )
        }

        composable(AppScreen.UTP_HOME.name) {
            val utpStatus = viewModel.getUtpStatus()

            UtpReviewScreen(
                utpStatus = utpStatus.status,
                criteriaList = utpStatus.criteria,
                onAuthorize = { /* Lógica pendiente */ },
                onRequestChanges = { /* Lógica pendiente */ },
                onNavigate = { screen: AppScreen -> navController.navigate(screen.name) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Escuela Escritora Marcela Paz",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Apoyo Didáctico",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val role = viewModel.login(username, password)

                if (role != null) {
                    Toast.makeText(context, "Ingresando como $role", Toast.LENGTH_SHORT).show()

                    when (role) {
                        "STUDENT" -> navController.navigate(AppScreen.STUDENT_HOME.name)
                        "TEACHER" -> navController.navigate(AppScreen.TEACHER_HOME.name)
                        "UTP" -> navController.navigate(AppScreen.UTP_HOME.name)
                    }
                } else {
                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }
    }
}