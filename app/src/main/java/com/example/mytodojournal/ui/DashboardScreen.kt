package com.example.mytodojournal.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mytodojournal.data.TaskEntity
import com.example.mytodojournal.ui.theme.MyToDoJournalTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState

// --- Data structure definitions ---
data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

val bottomNavItems = listOf(
    NavItem("tasks", Icons.Filled.CheckCircle, "Tasks"),
    NavItem("journal", Icons.Filled.Edit, "Journal"),
    NavItem("habits", Icons.Filled.Timeline, "Habits"),
    NavItem("settings", Icons.Filled.Settings, "Settings")
)

data class TaskEntity(
    val title: String,
    val description: String?,
    val category: String,
    val priority: String,
    val dueDate: String
)

val sampleTasks = listOf(
    TaskEntity(title = "Finish University Report", description = null, category = "Study", priority = "High", dueDate = "Today"),
    TaskEntity(title = "Buy Groceries", description = null, category = "Home", priority = "Medium", dueDate = "Tomorrow"),
    TaskEntity(title = "30-min Walk", description = null, category = "Health", priority = "Low", dueDate = "Today")
)

// --- 1. Main Content Composable (Tasks View) ---
// FIX: Renamed from DashboardScreenContent
@Composable
fun DashboardScreen(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "tasks"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { GreetingAndQuote() }
        item { SmartSuggestionCard() }
        item { TodayTasksHeader() }

        items(sampleTasks.take(3)) { task ->
            TaskItemCard(task)
        }

        item { Spacer(Modifier.height(32.dp)) }
    }
}


// --- 2. TopAppBarContent (Back Button and Profile Icon) ---
// FIX: Renamed from TopAppBarContent
@Composable
fun AppTopBar(navController: NavHostController, currentRoute: String) {
    val showBackButton = currentRoute != "tasks"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showBackButton) {
                IconButton(
                    onClick = { navController.popBackStack() }
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.SentimentSatisfied,
                    contentDescription = "Current Mood: Happy",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(32.dp).padding(start = 16.dp)
                )
            }
        }

        IconButton(onClick = { navController.navigate("settings") }) {
            Icon(Icons.Filled.AccountCircle, contentDescription = "Profile")
        }
    }
}


// --- 3. Supporting Composables ---

@Composable
fun GreetingAndQuote() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Hello, Abdul Sami!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = "“Productivity is never an accident. It is always the result of a commitment to excellence.”",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SmartSuggestionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Smart Suggestions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            Text("💡 Tasks you often delay: 'Read a Book' (Delayed 3 times)")
            Text("📈 Habits improving: Walk 5k steps (3/7 days completed this week)")
        }
    }
}

@Composable
fun TodayTasksHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Today's Top Tasks",
            style = MaterialTheme.typography.titleLarge
        )
        val context = LocalContext.current
        Text(
            text = "View All",
            color = MaterialTheme.colorScheme.primary,
            // Navigate to AddEditTaskActivity (or a list screen)
            modifier = Modifier.clickable {
                val intent = Intent(context, AddEditTaskActivity::class.java)
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun TaskItemCard(task: TaskEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.RadioButtonUnchecked,
                contentDescription = "Checkbox",
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${task.category} · ${task.priority} Priority",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = task.dueDate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun QuickAddTaskFAB(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = { Icon(Icons.Filled.Add, contentDescription = "Quick Add") },
        text = { Text("New Task") }
    )
}

@Composable
fun MyToDoBottomBar(selectedRoute: String, onNavigate: (String) -> Unit) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedRoute == item.route,
                onClick = { onNavigate(item.route) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()

    MyToDoJournalTheme {
        DashboardScreen(navController = navController) // FIX: Updated preview call
    }
}