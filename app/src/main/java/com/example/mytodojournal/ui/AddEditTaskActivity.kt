package com.example.mytodojournal.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.mytodojournal.data.AppDatabase
import com.example.mytodojournal.data.TaskEntity
import com.example.mytodojournal.ui.theme.MyToDoJournalTheme
import kotlinx.coroutines.launch

class AddEditTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyToDoJournalTheme {
                AddEditTaskScreen(
                    onSave = { task -> saveTaskToDatabase(task) },
                    onNavigateBack = { finish() }
                )
            }
        }
    }

    private fun saveTaskToDatabase(task: TaskEntity) {
        lifecycleScope.launch {
            // Note: This needs the TaskDao interface and AppDatabase class to be compiled successfully
            try {
                AppDatabase.getDatabase(applicationContext)
                    .taskDao()
                    .insertTask(task)
                Toast.makeText(this@AddEditTaskActivity, "Task Saved: ${task.title}", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@AddEditTaskActivity, "Error saving task: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(onSave: (TaskEntity) -> Unit, onNavigateBack: () -> Unit) {
    // ... (UI code is the same)
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Study") }
    var priority by remember { mutableStateOf("Medium") }
    var dueDate by remember { mutableStateOf("") }

    // ... (Rest of the UI logic)
    val categories = listOf("Work", "Home", "Health", "Study", "Custom")
    val priorities = listOf("Low", "Medium", "High")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Task") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Task Title") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description (Optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Text("Category: $category", style = MaterialTheme.typography.bodyLarge)
            Text("Priority: $priority", style = MaterialTheme.typography.bodyLarge)

            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Due Date (e.g., YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && dueDate.isNotBlank()) {
                        val task = TaskEntity(
                            title = title,
                            description = description.ifBlank { null },
                            category = category,
                            priority = priority,
                            dueDate = dueDate
                        )
                        onSave(task)
                    } else {
                        // Handle error
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(12.dp)
            ) {
                Text("Save Task")
            }
        }
    }
}