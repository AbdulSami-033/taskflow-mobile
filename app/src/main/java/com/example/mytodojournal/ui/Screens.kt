package com.example.mytodojournal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.* import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

// --- Data Models (Defined for this file's compilation) ---
data class Habit(
    val name: String,
    val goal: String,
    val completedDates: List<String>
)

data class JournalEntry(
    val date: String,
    val content: String,
    val moodTag: String,
    val activityTags: List<String>
)

// --- 1. Journal Screen (Consolidated and Detailed) ---
@Composable
fun JournalScreen() {
    val sampleJournalEntries = listOf(
        JournalEntry(
            date = "Today",
            content = "Finished the UI structure! It was a productive day focused on implementing navigation logic. Feeling happy about the progress.",
            moodTag = "Happy",
            activityTags = listOf("Coding", "Planning")
        ),
        JournalEntry(
            date = "Yesterday",
            content = "Spent the morning on errands and the afternoon catching up on old emails. Need to focus more on high-priority tasks tomorrow.",
            moodTag = "Neutral",
            activityTags = listOf("Home", "Admin")
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Your Daily Journal",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        // Button to add a new journal entry
        item {
            Button(
                onClick = { /* Navigate to AddEditJournal Screen */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Write New Entry")
            }
        }

        items(sampleJournalEntries) { entry ->
            JournalEntryCard(entry)
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun JournalEntryCard(entry: JournalEntry) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${entry.date} - Mood: ${entry.moodTag}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.height(8.dp))
            Text(text = entry.content, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Tags: ${entry.activityTags.joinToString()}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


// --- 2. Habits Screen (Consolidated and Detailed) ---
@Composable
fun HabitsScreen() {
    val sampleHabits = listOf(
        Habit(
            name = "Drink Water (8 glasses)",
            goal = "Daily",
            completedDates = listOf("2025-12-10", "2025-12-11", "2025-12-13")
        ),
        Habit(
            name = "Read 30 mins",
            goal = "Daily",
            completedDates = listOf("2025-12-14")
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                "Habit Tracker",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        items(sampleHabits) { habit ->
            HabitItemCard(habit)
        }

        // Placeholder for charts visualization
        item {
            Text(
                "Weekly Progress Chart (Placeholder)",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(Modifier.height(200.dp).fillMaxWidth().background(Color.LightGray.copy(alpha = 0.5f)))
        }

        item { Spacer(Modifier.height(80.dp)) }
    }
}

@Composable
fun HabitItemCard(habit: Habit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = habit.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "${habit.goal} - Last completed: ${habit.completedDates.lastOrNull() ?: "Never"}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Completion Status Indicator (Simple logic)
            val completionCount = habit.completedDates.size
            Text(
                text = "$completionCount days",
                color = if (completionCount > 2) Color.Green else Color.Red,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.width(16.dp))
            Button(onClick = { /* Mark as Complete */ }) {
                Text("Done")
            }
        }
    }
}


// --- 3. Settings Screen (Final Corrected Version) ---
@Composable
fun SettingsScreen(onToggleTheme: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp, bottom = 80.dp)
    ) {
        item {
            ListItem(
                headlineContent = { Text("App Theme") },
                supportingContent = { Text("Toggle Light/Dark Mode") },
                trailingContent = {
                    Switch(
                        checked = isSystemInDarkTheme(),
                        onCheckedChange = { onToggleTheme() }
                    )
                },
                modifier = Modifier.clickable { onToggleTheme() }
            )
            HorizontalDivider() // FIX: Uses M3 HorizontalDivider
        }

        // Security Setting (Feature h)
        item {
            ListItem(
                headlineContent = { Text("Security Lock") },
                supportingContent = { Text("Enable PIN or Biometric lock") },
                trailingContent = { Switch(checked = false, onCheckedChange = { /* Toggle lock */ }) },
                modifier = Modifier.clickable { /* Handle click */ }
            )
            HorizontalDivider() // FIX: Uses M3 HorizontalDivider
        }

        // Profile/Account Setting (Feature a)
        item {
            ListItem(
                headlineContent = { Text("Manage Profile") },
                supportingContent = { Text("Update goals and preferences") },
                trailingContent = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                modifier = Modifier.clickable { /* Handle click */ }
            )
            HorizontalDivider() // FIX: Uses M3 HorizontalDivider
        }
    }
}