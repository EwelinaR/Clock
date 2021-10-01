package com.example.clock.weather

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
class WeatherParserTest {

    private val json = """
        {"weather":[{"icon":"02d"}],"main":{"temp":17.01,"feels_like":16.56}}
        """

    @Test
    fun getResult() = runBlockingTest {
        // assign
        val parser = WeatherParser(json)

        // act
        val weather = parser.getResult()

        // assert
        assertNotNull(weather)
        assertEquals(17.0f, weather!!.temperature)
        assertEquals(16.6f, weather.feelTemperature)
        assertEquals("icon_02d", weather.icon)
    }
}
