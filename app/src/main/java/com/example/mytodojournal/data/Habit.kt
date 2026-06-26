package com.example.mytodojournal.data

data class Habit(
    val id: Int = 0,
    val name: String, // e.g., "Drink Water"
    val goal: String, // e.g., "8 glasses" or "Daily"
    val frequency: String, // e.g., "Daily", "Mon, Wed, Fri"
    val createdDate: String,
    // Note: Tracking progress usually involves a separate table (HabitLog)
    // but for simple UI testing, we can use a sample list of dates
    val completedDates: List<String> = emptyList()
)