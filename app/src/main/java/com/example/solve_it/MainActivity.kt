package com.example.solve_it

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var haventAccountTextView: TextView
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        setContentView(R.layout.start_page)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        viewFlipper = findViewById(R.id.viewFlipper)
        alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccountTextView)
        haventAccountTextView = findViewById(R.id.haventAccountTextView)
        registerButton = findViewById(R.id.register_button)
        loginButton = findViewById(R.id.log_in_button)

        alreadyHaveAccountTextView.setOnClickListener {
            flipToLogin()
        }

        haventAccountTextView.setOnClickListener {
            flipToRegister()
        }

        registerButton.setOnClickListener {
             val intent = Intent(this, HomeActivity::class.java)
             startActivity(intent)
             finish()
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun flipToLogin() {
        viewFlipper.inAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_left)
        viewFlipper.showNext()
    }

    private fun flipToRegister() {
        viewFlipper.inAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        viewFlipper.showPrevious()
    }
}