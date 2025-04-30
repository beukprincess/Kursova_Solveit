package com.example.solve_it

import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader

class SolutionActivity : AppCompatActivity() {

    lateinit var imageView2: ImageView
    lateinit var solutionContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solution_page)

        imageView2 = findViewById(R.id.image_save2)
        solutionContainer = findViewById(R.id.solutionContainer)

        // Show the image
        val imageUriString = intent.getStringExtra("imageUri")
        if (!imageUriString.isNullOrEmpty()) {
            val imageUri = Uri.parse(imageUriString)
            imageView2.setImageURI(imageUri)
        }

        // Load JSON from assets
        val jsonString = assets.open("solution.json").bufferedReader().use(BufferedReader::readText)

        try {
            // Parse the JSON string
            val solutionJson = JSONObject(jsonString)

            // Get the solution steps as a JSONObject
            val solutionSteps = solutionJson.getJSONObject("solution")

            // Iterate through the steps
            val stepKeys = solutionSteps.keys() // Get the keys (step numbers)
            val sortedKeys = stepKeys.asSequence().sorted().toList() // Sort the keys

            for (key in sortedKeys) {
                val stepDescription = solutionSteps.getString(key) // Get description for each step
                val stepNumber = key.replace("step", "").toInt()

                val stepTitleTextView = TextView(this).apply {
                    text = "Step ${stepNumber}:"
                    textSize = 35f
                    setTextColor(Color.BLACK)
                    setTypeface(null, Typeface.BOLD) // Make "Step N:" bold
                    setPadding(0, 16, 0, 8)
                }
                val stepDescriptionTextView = TextView(this).apply {
                    text = "${stepDescription} \n\n"
                    textSize = 32f // Increased size for thicker appearance
                    setTextColor(Color.BLACK)
                    setPadding(0, 0, 0, 16)
                }
                solutionContainer.addView(stepTitleTextView)
                solutionContainer.addView(stepDescriptionTextView)
            }

            // Display the answer
            val answerText = TextView(this).apply {
                text = "\nAnswer: ${solutionJson.getString("answer")} \n\n\n\n\n\n\n"
                textSize = 35f
                setTextColor(Color.BLACK)
                setTypeface(null, Typeface.BOLD) // Make "Answer" bold as well.
                setPadding(0, 16, 0, 0)
            }
            solutionContainer.addView(answerText)

        } catch (e: Exception) {
            // Handle JSON parsing errors
            val errorText = TextView(this).apply {
                text = "Error parsing JSON: ${e.message}"
                textSize = 16f
                setTextColor(Color.RED)
            }
            solutionContainer.addView(errorText)
        }
    }
}
