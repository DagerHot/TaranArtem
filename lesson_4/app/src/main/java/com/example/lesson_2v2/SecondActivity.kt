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

class SecondActivity : AppCompatActivity() {

    private lateinit var counterReceiver: BroadcastReceiver
    private lateinit var counterText: TextView
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        counterText = findViewById(R.id.counterText)
        val incrementButton: Button = findViewById(R.id.incrementButton)
        val backButton: Button = findViewById(R.id.backButton) // Кнопка для повернення на MainActivity

        // Отримуємо значення лічильника з Intent, якщо передано
        counter = intent.getIntExtra("counter", 0)
        counterText.text = counter.toString()

        // Зареєструвати BroadcastReceiver для оновлення лічильника
        counterReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                counter = intent?.getIntExtra("counter", 0) ?: 0
                counterText.text = counter.toString() // Оновлення UI з новим значенням
            }
        }

        // Фільтр для оновлення лічильника
        val filter = IntentFilter("com.example.counterapp.UPDATE_COUNTER")
        ContextCompat.registerReceiver(
            this,
            counterReceiver,
            filter,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        // Обробка кнопки для збільшення лічильника
        incrementButton.setOnClickListener {
            counter++
            val serviceIntent = Intent(this, CounterService::class.java).apply {
                putExtra("counter", counter)
            }
            startService(serviceIntent) // Оновлення значення лічильника через сервіс
            counterText.text = counter.toString() // Оновлення лічильника на екрані одразу
        }

        // Обробка кнопки для повернення до MainActivity
        backButton.setOnClickListener {
            finish() // Закриває SecondActivity і повертає на MainActivity
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(counterReceiver)  // Не забуваємо скинути ресивер
    }
}
