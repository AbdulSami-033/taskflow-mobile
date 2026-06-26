package com.example.mytodojournal.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Logic to reschedule alarms would go here
            Log.d("BootReceiver", "Boot completed detected.")
            Toast.makeText(context, "MyToDo: Device Restarted - Syncing Alarms", Toast.LENGTH_LONG).show()
        }
    }
}