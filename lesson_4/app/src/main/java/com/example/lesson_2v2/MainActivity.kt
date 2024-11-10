package com.example.lesson_2v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var counterReceiver: BroadcastReceiver
    private lateinit var counterText: TextView
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterText = findViewById(R.id.counterText)
        val incrementButton: Button = findViewById(R.id.incrementButton)
        val goToSecondActivityButton: Button = findViewById(R.id.goToSecondActivityButton)

        // Зареєструвати BroadcastReceiver з флагом RECEIVER_NOT_EXPORTED
        counterReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                counter = intent?.getIntExtra("counter", 0) ?: 0
                counterText.text = counter.toString()
            }
        }

        // Фільтр для оновлення лічильника
        val filter = IntentFilter("com.example.counterapp.UPDATE_COUNTER")
        ContextCompat.registerReceiver(
            this,
            counterReceiver,
            filter,
            ContextCompat.RECEIVER_EXPORTED
        )

        // Обробка кнопки для збільшення лічильника
        incrementButton.setOnClickListener {
            counter++
            val serviceIntent = Intent(this, CounterService::class.java).apply {
                putExtra("counter", counter)
            }
            startService(serviceIntent)
        }

        // Перехід на SecondActivity
        goToSecondActivityButton.setOnClickListener {
            // Передача значення лічильника в SecondActivity через Intent
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("counter", counter)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(counterReceiver)  // Не забуваємо скинути ресивер
    }
}
