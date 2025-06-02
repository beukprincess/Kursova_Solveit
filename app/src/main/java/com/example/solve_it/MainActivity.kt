package com.example.solve_it

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.solve_it.models.User
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // ViewFlipper and TextView for flipping views
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var haventAccountTextView: TextView

    // Buttons
    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    // Registration fields
    private lateinit var emailField: EditText
    private lateinit var regLoginField: EditText
    private lateinit var regPasswordField: EditText

    // Login fields
    private lateinit var loginField: EditText
    private lateinit var loginPasswordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000) // Optional splash delay
        installSplashScreen()
        setContentView(R.layout.start_page)

        // Hide the action bar
        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        // Initialize UI components
        viewFlipper = findViewById(R.id.viewFlipper)
        alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccountTextView)
        haventAccountTextView = findViewById(R.id.haventAccountTextView)

        registerButton = findViewById(R.id.register_button)
        loginButton = findViewById(R.id.log_in_button)

        // Registration fields
        emailField = findViewById(R.id.email)
        regLoginField = findViewById(R.id.login)
        regPasswordField = findViewById(R.id.password)

        // Login fields
        loginField = findViewById(R.id.login_field)
        loginPasswordField = findViewById(R.id.password_field)

        // Flip between login/registration
        alreadyHaveAccountTextView.setOnClickListener { flipToLogin() }
        haventAccountTextView.setOnClickListener { flipToRegister() }

        // Handle registration
        registerButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val login = regLoginField.text.toString().trim()
            val password = regPasswordField.text.toString().trim()

            if (email.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty()) {
                val user = User(email = email, login = login, password = password, history = listOf("registered"))

                lifecycleScope.launch {
                    try {
                        val response = RetrofitClient.api.createUser(user)
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, "Registration successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Toast.makeText(this@MainActivity, "Registration failed: $errorBody", Toast.LENGTH_LONG).show()
                            println("Registration failed: $errorBody")
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle login
        loginButton.setOnClickListener {
            val login = loginField.text.toString().trim()
            val password = loginPasswordField.text.toString().trim()

            if (login.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val users = RetrofitClient.api.getUsers()
                        val matchedUser = users.find { it.login == login && it.password == password }

                        if (matchedUser != null) {
                            Toast.makeText(this@MainActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@MainActivity, "Invalid login or password", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Login error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter login and password", Toast.LENGTH_SHORT).show()
            }
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
