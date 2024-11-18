package com.example.lesson_2v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var counterReceiver: BroadcastReceiver
    private lateinit var counterText: TextView
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterText = findViewById(R.id.counterText)
        val incrementButton: Button = findViewById(R.id.incrementButton)
        val sortButton: Button = findViewById(R.id.sortButton)
        val goToSecondActivityButton: Button = findViewById(R.id.goToSecondActivityButton)

        // Реєстрація BroadcastReceiver
        counterReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                counter = intent?.getIntExtra("counter", 0) ?: 0
                counterText.text = counter.toString()
            }
        }
        val filter = IntentFilter("com.example.counterapp.UPDATE_COUNTER")
        ContextCompat.registerReceiver(
            this,
            counterReceiver,
            filter,
            ContextCompat.RECEIVER_EXPORTED
        )

        // Інкремент лічильника
        incrementButton.setOnClickListener {
            counter++
            val serviceIntent = Intent(this, CounterService::class.java).apply {
                putExtra("counter", counter)
            }
            startService(serviceIntent)
        }

        // Виконання сортування
        sortButton.setOnClickListener {
            val array = IntArray(10_000) { (0..100_000).random() }
            GlobalScope.launch(Dispatchers.Default) {
                val time = measureTimeMillis {
                    insertionSort(array)
                }
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Сортування виконано за $time мс",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // Перехід до SecondActivity
        goToSecondActivityButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("counter", counter)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(counterReceiver)
    }

    private fun insertionSort(array: IntArray) {
        for (i in 1 until array.size) {
            val key = array[i]
            var j = i - 1
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j]
                j--
            }
            array[j + 1] = key
        }
    }
}
