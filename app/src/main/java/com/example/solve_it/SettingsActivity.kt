package com.example.solve_it

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.solve_it.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener

class SettingsActivity : AppCompatActivity(), OnItemSelectedListener  {

    private var langs = arrayOf(
        "English", "Ukrainian"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)

        val spin = findViewById<Spinner>(R.id.spinner)

        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_selected_item, langs)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        spin.adapter = mArrayAdapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}