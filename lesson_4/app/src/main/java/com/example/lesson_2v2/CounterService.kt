package com.example.lesson_2v2

import android.app.Service
import android.content.Intent
import android.os.IBinder

class CounterService : Service() {

    private var counter = 0

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Отримуємо значення лічильника з Intent
        counter = intent.getIntExtra("counter", counter)

        // Відправляємо нове значення через Broadcast
        val broadcastIntent = Intent("com.example.counterapp.UPDATE_COUNTER").apply {
            putExtra("counter", counter)
        }
        sendBroadcast(broadcastIntent)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
