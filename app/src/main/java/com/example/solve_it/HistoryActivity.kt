package com.example.solve_it
import QueryHistoryAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QueryHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_page)

        recyclerView = findViewById(R.id.historyRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val login = sharedPrefs.getString("user_login", null)

        if (login != null) {
            loadHistory(login)
        } else {
            Toast.makeText(this, "Користувача не знайдено", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadHistory(login: String) {
        lifecycleScope.launch {
            try {
                val users = RetrofitClient.api.getUsers()
                val user = users.find { it.login == login }

                if (user != null) {
                    val historyList = RetrofitClient.api.getQueryHistory()
                        .filter { it.userId == user.id }

                    if (historyList.isEmpty()) {
                        Toast.makeText(this@HistoryActivity, "Історія пуста", Toast.LENGTH_SHORT).show()
                        return@launch
                    }

                    adapter = QueryHistoryAdapter(historyList)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@HistoryActivity, "Користувача не знайдено", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HistoryActivity, "Помилка: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

