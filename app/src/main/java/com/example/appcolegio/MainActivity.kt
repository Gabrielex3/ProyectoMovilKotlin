package com.example.appcolegio

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.ui.graphics.Brush
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appcolegio.data.local.LocalDatabaseHelper
import com.example.appcolegio.ui.AppScreen
import com.example.appcolegio.ui.MainViewModel
import com.example.appcolegio.ui.screens.ProfileSelectionScreen
import com.example.appcolegio.ui.screens.student.DidacticContentScreen
import com.example.appcolegio.ui.screens.student.QuizResultScreen
import com.example.appcolegio.ui.screens.student.QuizScreen
import com.example.appcolegio.ui.screens.student.StudentActivitiesScreen
import com.example.appcolegio.ui.screens.student.StudentHomeScreen
import com.example.appcolegio.ui.screens.student.SubjectsListScreen
import com.example.appcolegio.ui.screens.student.UnitDetailScreen
import com.example.appcolegio.ui.screens.teacher.ContentAssignmentScreen
import com.example.appcolegio.ui.screens.teacher.CreateQuizScreen
import com.example.appcolegio.ui.screens.teacher.PedagogicalReportScreen
import com.example.appcolegio.ui.screens.teacher.TeacherHomeScreen
import com.example.appcolegio.ui.screens.teacher.TeacherManagementScreen
import com.example.appcolegio.ui.screens.utp.UtpRequestsScreen
import com.example.appcolegio.ui.screens.utp.UtpReviewScreen
import com.example.appcolegio.ui.theme.AppColegioTheme
import com.example.appcolegio.ui.theme.ThemeMode

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = LocalDatabaseHelper(this)
        dbHelper.insertInitialUsers()

        setContent {
            val currentThemeMode by viewModel.themeMode.collectAsState()
            val isDark = when (currentThemeMode) {
                ThemeMode.SYSTEM -> androidx.compose.foundation.isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            SideEffect {
                WindowCompat.getInsetsController(window, window.decorView).apply {
                    isAppearanceLightStatusBars = !isDark
                    isAppearanceLightNavigationBars = !isDark
                }
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                window.navigationBarColor = android.graphics.Color.TRANSPARENT
                window.decorView.setBackgroundColor(if (isDark) android.graphics.Color.parseColor("#0F172A") else android.graphics.Color.parseColor("#F8FAFC"))
            }

            AppColegioTheme(themeMode = currentThemeMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
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
    val context = LocalContext.current
    val utpStatusState by viewModel.utpStatusState.collectAsState()
    val quizScore by viewModel.quizResultScore.collectAsState()
    val quizTotal by viewModel.quizTotalQuestions.collectAsState()
    val answeredList by viewModel.answeredQuestionsState.collectAsState()

    NavHost(navController = navController, startDestination = AppScreen.LOGIN.name) {

        composable(AppScreen.LOGIN.name) {
            LoginScreen(navController = navController, viewModel = viewModel)
        }

        composable(AppScreen.PROFILE_SELECTION.name) {
            ProfileSelectionScreen(
                profiles = viewModel.defaultProfiles(),
                onProfileSelected = { user ->
                    when (user.role) {
                        "Estudiante" -> navController.navigate(AppScreen.STUDENT_HOME.name)
                        "Docente" -> navController.navigate(AppScreen.TEACHER_HOME.name)
                        else -> navController.navigate(AppScreen.UTP_HOME.name)
                    }
                }
            )
        }

        composable(AppScreen.STUDENT_HOME.name) {
            val studentProfile = viewModel.getCurrentStudentProfile()
            val subjects = viewModel.getSubjects()

            StudentHomeScreen(
                user = studentProfile,
                subjects = subjects,
                onSelectSubject = { subjectId ->
                    viewModel.selectSubject(subjectId)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.SUBJECTS_LIST.name) {
            val subjects = viewModel.getSubjects()

            SubjectsListScreen(
                subjects = subjects,
                onSelectSubject = { subjectId ->
                    viewModel.selectSubject(subjectId)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.STUDENT_ACTIVITIES.name) {
            val subjects = viewModel.getSubjects()
            val bestScores = viewModel.getBestScoresForCurrentUser()

            StudentActivitiesScreen(
                subjects = subjects,
                bestScores = bestScores,
                canTake = { quizId -> viewModel.getAttemptsLeftForQuiz(quizId) > 0 },
                onSelectSubject = { subjectId ->
                    viewModel.selectSubject(subjectId)
                },
                onSelectQuiz = { quizId ->
                    viewModel.setCurrentQuizId(quizId)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.UNIT_DETAIL.name) {
            val units = viewModel.getUnitForSelectedSubject()
            val bestScores = viewModel.getBestScoresForCurrentUser()

            UnitDetailScreen(
                units = units,
                bestScores = bestScores,
                canTake = { quizId -> viewModel.getAttemptsLeftForQuiz(quizId) > 0 },
                onSelectQuiz = { quizId ->
                    viewModel.setCurrentQuizId(quizId)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.DIDACTIC_CONTENT.name) {
            DidacticContentScreen(
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.QUIZ_ACTIVITY.name) {
            val questions = viewModel.getQuestionsForSelectedSubject()

            QuizScreen(
                questions = questions,
                onFinishQuiz = { calculatedScore, totalQ, answered ->
                    viewModel.setQuizResult(calculatedScore, totalQ, answered)
                    navController.navigate(AppScreen.QUIZ_RESULT.name)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.QUIZ_SESSION.name) {
            val questions = viewModel.getQuestionsForSelectedSubject()

            QuizScreen(
                questions = questions,
                onFinishQuiz = { calculatedScore, totalQ, answered ->
                    viewModel.recordQuizCompletion(calculatedScore, totalQ, answered)
                    navController.navigate(AppScreen.QUIZ_RESULT.name)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.QUIZ_RESULT.name) {
            val questions = if (answeredList.isNotEmpty()) answeredList else viewModel.getQuestionsForSelectedSubject()

            QuizResultScreen(
                score = quizScore,
                total = quizTotal,
                questions = questions,
                attemptsLeft = viewModel.getAttemptsLeft(),
                onRetry = {
                    if (viewModel.canTakeQuiz()) {
                        navController.navigate(AppScreen.QUIZ_SESSION.name)
                    } else {
                        Toast.makeText(context, "⚠️ Has agotado todos tus intentos", Toast.LENGTH_SHORT).show()
                    }
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(AppScreen.UNIT_DETAIL.name)
                    }
                }
            )
        }

        composable(AppScreen.TEACHER_HOME.name) {
            val teacherProfile = viewModel.getCurrentTeacherProfile()

            TeacherHomeScreen(
                user = teacherProfile,
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.CONTENT_ASSIGNMENT.name) {
            TeacherManagementScreen(
                subjects = viewModel.getSubjects(),
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.CREATE_QUIZ.name) {
            CreateQuizScreen(
                subjects = viewModel.getSubjects(),
                onCreateQuiz = { subjectId, title, qText, a, b, c, d, correctIdx ->
                    viewModel.createQuizQuestion(subjectId, title, qText, a, b, c, d, correctIdx)
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.PEDAGOGICAL_REPORT.name) {
            PedagogicalReportScreen(
                utpStatus = utpStatusState.status,
                objectives = viewModel.getLearningObjectives(),
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.UTP_HOME.name) {
            UtpRequestsScreen(
                utpStatus = utpStatusState.status,
                onSelectRequest = { reqId ->
                    // Request selected
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }

        composable(AppScreen.UTP_REVIEW.name) {
            UtpReviewScreen(
                utpStatus = utpStatusState.status,
                criteriaList = utpStatusState.criteria,
                onAuthorize = {
                    viewModel.updateUtpStatus("AUTORIZADO")
                    Toast.makeText(context, "✅ Unidad Autorizada por UTP", Toast.LENGTH_SHORT).show()
                },
                onRequestChanges = {
                    viewModel.updateUtpStatus("CAMBIOS_SOLICITADOS")
                    Toast.makeText(context, "⚠️ Se han solicitado cambios al docente", Toast.LENGTH_SHORT).show()
                },
                onNavigate = { screen: AppScreen ->
                    if (screen == AppScreen.PROFILE_SELECTION) {
                        navController.navigate(AppScreen.LOGIN.name)
                    } else {
                        navController.navigate(screen.name)
                    }
                }
            )
        }
    }
}

private fun roleLabel(role: String): String =
    if (role.uppercase() == "UTP") "UTP" else role.lowercase().replaceFirstChar { it.uppercase() }

// ---------- Login ----------
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    var targetRole by remember { mutableStateOf("ESTUDIANTE") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val currentThemeMode by viewModel.themeMode.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // ----- Marca -----
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Escuela",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(34.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "MPAZ PRO 6.º",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Escuela Escritora Marcela Paz",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        // ----- Formulario -----
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Selecciona tu perfil e ingresa tus credenciales",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Perfil",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    RoleSelectTab("Estudiante", targetRole == "ESTUDIANTE", modifier = Modifier.weight(1f)) {
                        targetRole = "ESTUDIANTE"
                    }
                    RoleSelectTab("Docente", targetRole == "DOCENTE", modifier = Modifier.weight(1f)) {
                        targetRole = "DOCENTE"
                    }
                    RoleSelectTab("UTP", targetRole == "UTP", modifier = Modifier.weight(1f)) {
                        targetRole = "UTP"
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    placeholder = { Text("Ej: alumno1, profe1, utp1") },
                    singleLine = true,
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Usuario") },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    placeholder = { Text("Ejemplo: 1234") },
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (username.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Por favor ingresa tu usuario y contraseña", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val roleFromDb = viewModel.login(username.trim(), password.trim())

                        if (roleFromDb == null) {
                            Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        } else {
                            val roleDbUpper = roleFromDb.uppercase()
                            val targetUpper = targetRole.uppercase()

                            val isRoleMatching = when {
                                targetUpper == "ESTUDIANTE" && (roleDbUpper.contains("ESTUDIANTE") || roleDbUpper.contains("ALUMNO")) -> true
                                targetUpper == "DOCENTE" && (roleDbUpper.contains("DOCENTE") || roleDbUpper.contains("PROFE")) -> true
                                targetUpper == "UTP" && roleDbUpper.contains("UTP") -> true
                                else -> false
                            }

                            if (isRoleMatching) {
                                Toast.makeText(context, "Ingresando como $roleFromDb", Toast.LENGTH_SHORT).show()
                                when (targetUpper) {
                                    "ESTUDIANTE" -> navController.navigate(AppScreen.STUDENT_HOME.name)
                                    "DOCENTE" -> navController.navigate(AppScreen.TEACHER_HOME.name)
                                    "UTP" -> navController.navigate(AppScreen.UTP_HOME.name)
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "⚠️ El perfil seleccionado ($targetRole) no coincide con el rol de la cuenta ($roleFromDb)",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = "Ingresar como ${roleLabel(targetRole)}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Autenticación por base de datos SQLite segura.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ----- Tema -----
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.Transparent,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Tema",
                    modifier = Modifier.padding(horizontal = 6.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                ThemeSelectorChip("Auto", currentThemeMode == ThemeMode.SYSTEM) {
                    viewModel.setThemeMode(ThemeMode.SYSTEM)
                }
                ThemeSelectorChip("Claro", currentThemeMode == ThemeMode.LIGHT) {
                    viewModel.setThemeMode(ThemeMode.LIGHT)
                }
                ThemeSelectorChip("Oscuro", currentThemeMode == ThemeMode.DARK) {
                    viewModel.setThemeMode(ThemeMode.DARK)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ThemeSelectorChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.14f) else Color.Transparent,
        label = "themeChipBg"
    )

    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        color = bg
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun RoleSelectTab(
    roleTitle: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val bg by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        label = "roleTabBg"
    )

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bg)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = roleTitle,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}