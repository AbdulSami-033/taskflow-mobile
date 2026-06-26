package com.example.mytodojournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mytodojournal.ui.* // Import all screen composables
import com.example.mytodojournal.ui.theme.MyToDoJournalTheme
import androidx.compose.ui.Modifier // <--- CRITICAL IMPORT
import androidx.compose.foundation.layout.padding // CRITICAL IMPORT

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // State for theme toggling
            var darkTheme by remember { mutableStateOf(false) } // Default to Light Mode

            MyToDoJournalTheme(darkTheme = darkTheme) { // Pass the state to the theme
                MyAppNavigation(
                    onToggleTheme = { darkTheme = !darkTheme }
                )
            }
        }
    }
}

@Composable
fun MyAppNavigation(onToggleTheme: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "tasks"

    Surface {
        // --- Centralized Scaffold for Top/Bottom Bars ---
        Scaffold(
            // FIX: Uses the new name defined in DashboardScreen.kt
            topBar = { AppTopBar(navController, currentRoute) },
            bottomBar = {
                MyToDoBottomBar(
                    selectedRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Set up the NavHost inside the Scaffold's padding
            NavHost(
                navController = navController,
                startDestination = "tasks",
                modifier = Modifier.padding(paddingValues)
            ) {
                // FIX: Uses the new name defined in DashboardScreen.kt
                composable("tasks") {
                    DashboardScreen(navController = navController)
                }
                composable("journal") { JournalScreen() }
                composable("habits") { HabitsScreen() }
                composable("settings") { SettingsScreen(onToggleTheme = onToggleTheme) }
            }
        }
    }
}