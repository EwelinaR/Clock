package com.example.clock

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
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
    }

    private fun initScreen() {
        // hide bars
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        // use landscape orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    private fun initTime() {
        val tensOfHour = findViewById<TextView>(R.id.tens_of_hour)
        val unitsOfHour = findViewById<TextView>(R.id.units_of_hour)
        viewModel.tensOfHour.observe(this, { tensOfHour.text = it.toString() })
        viewModel.unitsOfHour.observe(this, { unitsOfHour.text = it.toString() })

        val tensOfMinutes = findViewById<TextView>(R.id.tens_of_minutes)
        val unitsOfMinutes = findViewById<TextView>(R.id.units_of_minutes)
        viewModel.tensOfMinutes.observe(this, { tensOfMinutes.text = it.toString() })
        viewModel.unitsOfMinutes.observe(this, { unitsOfMinutes.text = it.toString() })
    }
}