package com.example.appcolegio

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: MainViewModel) {
    var targetRole by remember { mutableStateOf("ESTUDIANTE") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val currentThemeMode by viewModel.themeMode.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Floating Theme Switcher Pill Card at Top
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text("Tema:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    ThemeSelectorChip("📱 Auto", currentThemeMode == ThemeMode.SYSTEM) {
                        viewModel.setThemeMode(ThemeMode.SYSTEM)
                    }
                    ThemeSelectorChip("☀️ Claro", currentThemeMode == ThemeMode.LIGHT) {
                        viewModel.setThemeMode(ThemeMode.LIGHT)
                    }
                    ThemeSelectorChip("🌙 Oscuro", currentThemeMode == ThemeMode.DARK) {
                        viewModel.setThemeMode(ThemeMode.DARK)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Main Login Card Container
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header Logo Circle
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFDBEAFE)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = "Escuela",
                            tint = Color(0xFF1D4ED8),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFEFF6FF)
                    ) {
                        Text(
                            text = "MPAZ PRO 6.º",
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 5.dp),
                            color = Color(0xFF1D4ED8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Escuela Escritora Marcela Paz",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Selecciona tu perfil e ingresa tus credenciales",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Role Selection Tabs
                    Text(
                        text = "¿Cómo deseas ingresar?",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Contraseña") },
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
                        shape = RoundedCornerShape(26.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Ingresar como $targetRole", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Seguridad BD",
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Autenticación por base de datos SQLite segura.",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeSelectorChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bg = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        color = bg
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = textColor
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
    val bg = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val borderCol = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFCBD5E1)
    val textColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface

    Card(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderCol),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(roleTitle, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = textColor)
        }
    }
}
