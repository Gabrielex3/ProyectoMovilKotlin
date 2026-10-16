package com.example.appcolegio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcolegio.ui.AppScreen
import com.example.appcolegio.ui.MainViewModel
import com.example.appcolegio.ui.screens.ProfileSelectionScreen
import com.example.appcolegio.ui.screens.student.DidacticContentScreen
import com.example.appcolegio.ui.screens.student.QuizResultScreen
import com.example.appcolegio.ui.screens.student.QuizScreen
import com.example.appcolegio.ui.screens.student.StudentHomeScreen
import com.example.appcolegio.ui.screens.student.UnitDetailScreen
import com.example.appcolegio.ui.screens.teacher.ContentAssignmentScreen
import com.example.appcolegio.ui.screens.teacher.PedagogicalReportScreen
import com.example.appcolegio.ui.screens.teacher.TeacherHomeScreen
import com.example.appcolegio.ui.screens.utp.UtpReviewScreen
import com.example.appcolegio.ui.theme.AppColegioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppColegioTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MpazProApp()
                }
            }
        }
    }
}

@Composable
fun MpazProApp(viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val repo = viewModel.getRepository()

    when (uiState.currentScreen) {
        AppScreen.PROFILE_SELECTION -> {
            ProfileSelectionScreen(
                profiles = repo.defaultProfiles,
                onProfileSelected = { user ->
                    viewModel.selectUserAndNavigate(user)
                }
            )
        }

        AppScreen.STUDENT_HOME -> {
            StudentHomeScreen(
                user = uiState.selectedUser ?: repo.defaultProfiles.first(),
                subjects = repo.defaultSubjects,
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.UNIT_DETAIL -> {
            UnitDetailScreen(
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.DIDACTIC_CONTENT -> {
            DidacticContentScreen(
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.QUIZ_ACTIVITY -> {
            val currentQ = uiState.questions.getOrNull(uiState.currentQuestionIndex)
                ?: repo.getInitialQuestions().first()

            QuizScreen(
                question = currentQ,
                currentQuestionIndex = uiState.currentQuestionIndex,
                totalQuestions = uiState.totalQuestions,
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.QUIZ_RESULT -> {
            QuizResultScreen(
                score = uiState.quizScore,
                total = uiState.totalQuestions,
                attemptsLeft = uiState.attemptsLeft,
                onRetry = { viewModel.retryQuiz() },
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.TEACHER_HOME -> {
            TeacherHomeScreen(
                user = uiState.selectedUser ?: repo.defaultProfiles[1],
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.CONTENT_ASSIGNMENT -> {
            ContentAssignmentScreen(
                currentConfig = uiState.assignmentConfig,
                onSaveConfig = { newConfig -> viewModel.updateAssignmentConfig(newConfig) },
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.PEDAGOGICAL_REPORT -> {
            PedagogicalReportScreen(
                utpStatus = uiState.utpStatus,
                objectives = repo.defaultObjectives,
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }

        AppScreen.UTP_REVIEW -> {
            UtpReviewScreen(
                utpStatus = uiState.utpStatus,
                criteriaList = repo.getUtpCriteria(),
                onAuthorize = { viewModel.updateUtpStatus("AUTORIZADO") },
                onRequestChanges = { viewModel.updateUtpStatus("CAMBIOS_SOLICITADOS") },
                onNavigate = { screen -> viewModel.navigateTo(screen) }
            )
        }
    }
}
