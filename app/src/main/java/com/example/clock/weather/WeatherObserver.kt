package com.example.clock.weather

interface WeatherObserver {
    fun updateWeatherValues(weather: Weather?)
}
