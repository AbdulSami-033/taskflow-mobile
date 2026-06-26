package com.example.mytodojournal.data

data class JournalEntry(
    val id: Int = 0,
    val date: String, // e.g., "2025-12-15"
    val content: String,
    val moodTag: String, // e.g., "Happy", "Stressed", "Productive"
    val activityTags: List<String> = emptyList(), // e.g., ["Work", "Study"]
    val voiceNotePath: String? = null // Local file path for optional voice note
)