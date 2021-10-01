package com.example.clock.weather

import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class WeatherParser(private val data: String) {

    fun getResult(): Weather? {
        return try {
            println(data)
             JSONObject(data).let {
                val temp = it
                    .getJSONObject("main")
                    .getDouble("temp")
                    .toFloat()
                val fTemp = it
                    .getJSONObject("main")
                    .getDouble("feels_like")
                    .toFloat()
                val icon = it
                    .getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("icon")

                Weather(roundFloat(temp), roundFloat(fTemp), "icon_$icon")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    private fun roundFloat(value: Float): Float {
        return (value * 10).roundToInt().toFloat() / 10
    }
}
