package com.example.solve_it

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.solve_it.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import androidx.core.graphics.toColorInt
import com.example.solve_it.MainActivity
import com.example.solve_it.HistoryActivity

class SettingsActivity : AppCompatActivity(), OnItemSelectedListener  {

    private var langs = arrayOf(
        "English", "Ukrainian"
    )
    private var isOn = false
    lateinit var history_button : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_page)

        val spin = findViewById<Spinner>(R.id.spinner)
        history_button = findViewById<Button>(R.id.history_but)

        val mArrayAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_selected_item, langs)
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list)

        // Set the adapter to the Spinner
        spin.adapter = mArrayAdapter

        val switchLayout = findViewById<ConstraintLayout>(R.id.customSwitch)
        val thumb = findViewById<View>(R.id.switchThumb)

        val backgroundDrawable = ContextCompat.getDrawable(this, R.drawable.switch_background_off)
        switchLayout.background = backgroundDrawable?.mutate()

        thumb.elevation = 8f

        switchLayout.setOnClickListener {
            isOn = !isOn

            val thumbPosition = if (isOn) {
                switchLayout.width - thumb.width - 2  // right side
            } else {
                2  // left side
            }


            thumb.animate()
                .x(thumbPosition.toFloat())
                .setDuration(200)
                .start()

            val colorFrom = if (isOn) "#DADADA".toColorInt() else "#4CAF50".toColorInt()
            val colorTo = if (isOn) "#4CAF50".toColorInt() else "#DADADA".toColorInt()

            val colorAnim = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
            colorAnim.duration = 200
            colorAnim.addUpdateListener {
                switchLayout.background.setTint(it.animatedValue as Int)
            }
            colorAnim.start()
        }

        history_button.setOnClickListener {
            startActivity(Intent(this@SettingsActivity, HistoryActivity::class.java))
            finish()
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

}




















