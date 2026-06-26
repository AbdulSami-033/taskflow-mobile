package com.example.mytodojournal.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class MotivationService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Daily quote delivery logic
        Toast.makeText(this, "Daily Quote: Believe you can and you're halfway there.", Toast.LENGTH_LONG).show()

        // Stops the service after doing the work
        stopSelf()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}