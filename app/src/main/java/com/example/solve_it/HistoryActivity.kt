package com.example.solve_it

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_page)

        val historyContainer = findViewById<LinearLayout>(R.id.historyContainer)

        val recentRequests = listOf(
            Pair("Square rivnyannya", 7),
            Pair("Linear equation", 5),
            Pair("Factorial calculator", 3),
            Pair("Prime check", 4)
        )

        for ((title, steps) in recentRequests) {
            val itemView = layoutInflater.inflate(R.layout.history_item, historyContainer, false)

            val titleView = itemView.findViewById<TextView>(R.id.textRequestTitle)
            val stepsView = itemView.findViewById<TextView>(R.id.textRequestSteps)

            titleView.text = title
            stepsView.text = "Steps: $steps"

            historyContainer.addView(itemView)
        }
    }
}
