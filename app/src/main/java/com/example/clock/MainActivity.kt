package com.example.clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.BatteryManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: ClockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initScreen()
        initTime()
        initWeather()
        initBatteryBroadcast()
        initOutOfDateWeatherWarning()
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
        val date = findViewById<TextView>(R.id.date)
        viewModel.date.observe(this, { date.text = it })

        val tensOfHour = findViewById<TextView>(R.id.tens_of_hour)
        val unitsOfHour = findViewById<TextView>(R.id.units_of_hour)
        viewModel.tensOfHour.observe(this, { tensOfHour.text = it.toString() })
        viewModel.unitsOfHour.observe(this, { unitsOfHour.text = it.toString() })

        val tensOfMinute = findViewById<TextView>(R.id.tens_of_minute)
        val unitsOfMinute = findViewById<TextView>(R.id.units_of_minute)
        viewModel.tensOfMinute.observe(this, { tensOfMinute.text = it.toString() })
        viewModel.unitsOfMinute.observe(this, { unitsOfMinute.text = it.toString() })
    }

    private fun initWeather() {
        val temperature = findViewById<TextView>(R.id.temperature)
        val feelTemperature = findViewById<TextView>(R.id.feelTemperature)
        viewModel.temperature.observe(this, { temperature.text = it.toString() })
        viewModel.feelTemperature.observe(this, { feelTemperature.text = it.toString() })

        val icon = findViewById<ImageView>(R.id.weatherIcon)
        viewModel.weatherIcon.observe(this, {
            var id = resources.getIdentifier(it.toString(), "drawable", packageName)
            if (id == 0) id = R.drawable.no_icon
            icon.setImageResource(id)
        })
    }

    private fun initBatteryBroadcast() {
        val batteryStatus = findViewById<TextView>(R.id.battery_warning)

        val broadcast = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)?.let {
                    batteryStatus.text = getString(R.string.battery, it.toString())
                    batteryStatus.visibility = if (it < 40) View.VISIBLE else View.INVISIBLE
                }
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(broadcast, filter)
    }

    private fun initOutOfDateWeatherWarning() {
        viewModel.isWeatherUpToDate.observe(this, { syncWeatherWarning(it) })
    }

    private fun syncWeatherWarning(isWeatherUpToDate: Boolean) {
        findViewById<TextView>(R.id.last_sync_warning).apply {
            if (viewModel.lastSyncDate.isNotEmpty()) {
                text = getString(R.string.sync_weather, viewModel.lastSyncDate)
            }
            visibility = if (isWeatherUpToDate) View.INVISIBLE else View.VISIBLE
        }
    }
}
