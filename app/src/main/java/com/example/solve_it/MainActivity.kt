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

    private lateinit var viewFlipper: ViewFlipper
    private lateinit var alreadyHaveAccountTextView: TextView
    private lateinit var haventAccountTextView: TextView

    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    private lateinit var emailField: EditText
    private lateinit var regLoginField: EditText
    private lateinit var regPasswordField: EditText

    private lateinit var loginField: EditText
    private lateinit var loginPasswordField: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(2000)
        installSplashScreen()
        setContentView(R.layout.start_page)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedLogin = sharedPref.getString("user_login", null)
        if (savedLogin != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
            return
        }

        viewFlipper = findViewById(R.id.viewFlipper)
        alreadyHaveAccountTextView = findViewById(R.id.alreadyHaveAccountTextView)
        haventAccountTextView = findViewById(R.id.haventAccountTextView)

        registerButton = findViewById(R.id.register_button)
        loginButton = findViewById(R.id.log_in_button)

        emailField = findViewById(R.id.email)
        regLoginField = findViewById(R.id.login)
        regPasswordField = findViewById(R.id.password)

        loginField = findViewById(R.id.login_field)
        loginPasswordField = findViewById(R.id.password_field)

        alreadyHaveAccountTextView.setOnClickListener { flipToLogin() }
        haventAccountTextView.setOnClickListener { flipToRegister() }

        registerButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val login = regLoginField.text.toString().trim()
            val password = regPasswordField.text.toString().trim()

            if (email.isNotEmpty() && login.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val users = RetrofitClient.api.getUsers()
                        val userExists = users.any { it.login == login || it.email == email }

                        if (userExists) {
                            Toast.makeText(this@MainActivity, "Користувач вже існує!", Toast.LENGTH_SHORT).show()
                        } else {
                            val newUser = User(email = email, login = login, password = password)
                            val response = RetrofitClient.api.createUser(newUser)

                            if (response.isSuccessful) {
                                sharedPref.edit().putString("user_login", login).apply()
                                Toast.makeText(this@MainActivity, "Успішна реєстрація!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                                finish()
                            } else {
                                val errorBody = response.errorBody()?.string()
                                Toast.makeText(this@MainActivity, "Помилка реєстрації: $errorBody", Toast.LENGTH_LONG).show()
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Помилка мережі: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Заповніть всі поля", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            val login = loginField.text.toString().trim()
            val password = loginPasswordField.text.toString().trim()

            if (login.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val users = RetrofitClient.api.getUsers()
                        val matchedUser = users.find { it.login == login && it.password == password }

                        if (matchedUser != null) {
                            sharedPref.edit().putString("user_login", login).apply()
                            Toast.makeText(this@MainActivity, "Успішний вхід!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@MainActivity, "Неправильний логін або пароль", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@MainActivity, "Помилка входу: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Введіть логін і пароль", Toast.LENGTH_SHORT).show()
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
