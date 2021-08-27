package com.example.clock

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ClockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(ClockViewModel::class.java)
        initScreen()
        initTime()
        viewModel.runClock()
    }

    private fun initScreen() {
        // hide bars
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        // use landscape orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // keep screen turned on
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun initTime() {
        val tensOfHour = findViewById<TextView>(R.id.tens_of_hour)
        val unitsOfHour = findViewById<TextView>(R.id.units_of_hour)
        viewModel.tensOfHour.observe(this, { tensOfHour.text = it.toString() })
        viewModel.unitsOfHour.observe(this, { unitsOfHour.text = it.toString() })

        val tensOfMinute = findViewById<TextView>(R.id.tens_of_minute)
        val unitsOfMinute = findViewById<TextView>(R.id.units_of_minute)
        viewModel.tensOfMinute.observe(this, { tensOfMinute.text = it.toString() })
        viewModel.unitsOfMinute.observe(this, { unitsOfMinute.text = it.toString() })
    }
}
