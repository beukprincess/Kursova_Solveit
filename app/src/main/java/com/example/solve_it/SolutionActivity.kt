package com.example.solve_it

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SolutionActivity : AppCompatActivity(){

    lateinit var imageView2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.solution_page)

        imageView2 = findViewById(R.id.image_save2)

        // Retrieve the image URI from the intent
        val imageUriString = intent.getStringExtra("imageUri")

        if (!imageUriString.isNullOrEmpty()) {
            val imageUri = Uri.parse(imageUriString)
            imageView2.setImageURI(imageUri) // Load the image using the URI
        } else {
            // Handle the case where no URI was passed (optional)
            // For example, display a placeholder image or a message.
        }
    }
}