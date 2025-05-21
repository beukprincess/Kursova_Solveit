package com.example.solve_it

import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File

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
            val imageFile = uriToFile(imageUri)
            if (imageFile != null) {
                imageView2.setImageURI(imageUri)

                // Upload image and wait for result.json
                ImageUploader.upload(this, imageFile) {
                    loadAndDisplaySolution()
                }
            }
        } else {
            // No image URI provided, try loading old result.json anyway
            loadAndDisplaySolution()
        }
    }

    private fun loadAndDisplaySolution() {
        val resultFile = File(filesDir, "result.json")

        if (!resultFile.exists()) {
            showError("result.json not found.")
            return
        }

        try {
            val jsonString = resultFile.readText()
            val cleanedJsonStringFIRST = jsonString.replace("\\", "")
            val cleanedJsonStringSECOND = cleanedJsonStringFIRST.replace("\"\"", "\"")
            val solutionJson = JSONObject("{$cleanedJsonStringSECOND}")
            val solutionSteps = solutionJson.getJSONObject("solution")

            val stepKeys = solutionSteps.keys()
            val sortedKeys = stepKeys.asSequence().sorted().toList()

            for (key in sortedKeys) {
                val stepDescription = solutionSteps.getString(key)
                val stepNumber = key.replace("step", "").toInt()

                val stepTitleTextView = TextView(this).apply {
                    text = "Step ${stepNumber}:"
                    textSize = 35f
                    setTextColor(Color.BLACK)
                    setTypeface(null, Typeface.BOLD)
                    setPadding(0, 16, 0, 8)
                }
                val stepDescriptionTextView = TextView(this).apply {
                    text = "$stepDescription \n\n"
                    textSize = 32f
                    setTextColor(Color.BLACK)
                    setPadding(0, 0, 0, 16)
                }
                solutionContainer.addView(stepTitleTextView)
                solutionContainer.addView(stepDescriptionTextView)
            }

            val answerText = TextView(this).apply {
                text = "\nAnswer: ${solutionJson.getString("answer")} \n\n\n\n\n\n\n"
                textSize = 35f
                setTextColor(Color.BLACK)
                setTypeface(null, Typeface.BOLD)
                setPadding(0, 16, 0, 0)
            }
            solutionContainer.addView(answerText)

        } catch (e: Exception) {
            Log.e("KOLIA", "Error parsing JSON", e)
            showError("Error parsing JSON: ${e.message}")
        }
    }

    private fun showError(message: String) {
        val errorText = TextView(this).apply {
            text = message
            textSize = 16f
            setTextColor(Color.RED)
        }
        solutionContainer.addView(errorText)
    }

    private fun uriToFile(uri: Uri): File? {
        val filePathColumn = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        contentResolver.query(uri, filePathColumn, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                return File(filePath)
            }
        }
        return null
    }
}
