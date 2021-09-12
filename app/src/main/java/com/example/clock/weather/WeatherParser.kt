package com.example.clock.weather

import org.json.JSONException
import org.json.JSONObject
import kotlin.math.roundToInt

class WeatherParser(private val data: String) {

    fun getResult() : Weather? {
        try {
            val json = JSONObject(data)

            val temp = json
                .getJSONObject("main")
                .getDouble("temp")
                .toFloat()
            val fTemp = json
                .getJSONObject("main")
                .getDouble("feels_like")
                .toFloat()
            val icon = json
                .getJSONArray("weather")
                .getJSONObject(0)
                .getString("icon")

            return Weather(roundFloat(temp), roundFloat(fTemp), "icon_$icon")
        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }
    }

    private fun roundFloat(value: Float) : Float {
        return (value * 10).roundToInt().toFloat() / 10
    }
}
