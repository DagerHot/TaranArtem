package com.example.lesson_2v2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import java.util.Locale

class CounterFragment : Fragment(R.layout.fragment_counter) {

    private lateinit var counterReceiver: BroadcastReceiver
    private lateinit var counterText: TextView
    private var counter = 0

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        counterText = view.findViewById(R.id.counterText)
        val incrementButton: Button = view.findViewById(R.id.incrementButton)

        // Зареєструвати BroadcastReceiver для оновлення лічильника
        counterReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                counter = intent?.getIntExtra("counter", 0) ?: 0
                counterText.text = String.format(Locale.getDefault(), "%d", counter)
            }
        }

        val filter = IntentFilter("com.example.counterapp.UPDATE_COUNTER")
        requireContext().registerReceiver(counterReceiver, filter, Context.RECEIVER_NOT_EXPORTED)


        incrementButton.setOnClickListener {
            counter++
            val serviceIntent = Intent(requireActivity(), CounterService::class.java).apply {
                putExtra("counter", counter)
            }
            requireActivity().startService(serviceIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireContext().unregisterReceiver(counterReceiver) // Не забуваємо відмінити реєстрацію
    }
}
