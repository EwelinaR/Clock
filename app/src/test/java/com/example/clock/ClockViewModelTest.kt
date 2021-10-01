package com.example.clock

import com.example.clock.calendar.CalendarExtension.getDate
import com.example.clock.calendar.CalendarExtension.getFullDate
import com.example.clock.calendar.CalendarInstance
import com.example.clock.calendar.CalendarUtilityMock
import com.example.clock.weather.Response
import com.example.clock.weather.Weather
import com.example.clock.weather.WeatherApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Calendar

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class ClockViewModelTest {

    private fun createClockInstance(
        weatherApi: WeatherApi = mock(),
        calendar: Calendar = mock()
    ): ClockViewModel {
        val calendarInstance: CalendarInstance = mock {
            on { getCalendarInstance() } doReturn calendar
        }
        return ClockViewModel(calendarInstance, weatherApi)
    }

    @Test
    fun increaseUnitsOfMinute() {
        // arrange
        val calendar = CalendarUtilityMock(12, 7)
        val clock = createClockInstance(calendar = calendar)

        // act
        calendar.minute += + 1
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        assertEquals(calendar.minute, actualMinute)
        assertEquals(calendar.hour, actualHour)
    }

    @Test
    fun increaseTensOfMinute() {
        // arrange
        val calendar = CalendarUtilityMock(8, 19)
        val clock = createClockInstance(calendar = calendar)

        // act
        calendar.minute += 1
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        assertEquals(calendar.minute, actualMinute)
        assertEquals(calendar.hour, actualHour)
    }

    @Test
    fun increaseUnitsOfHour() {
        // arrange
        val calendar = CalendarUtilityMock(12, 59)
        val clock = createClockInstance(calendar = calendar)

        // act
        calendar.minute = 0
        calendar.hour += 1
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        assertEquals(calendar.minute, actualMinute)
        assertEquals(calendar.hour, actualHour)
    }

    @Test
    fun increaseTensOfHour() {
        // arrange
        val calendar = CalendarUtilityMock(9, 59)
        val clock = createClockInstance(calendar = calendar)

        // act
        calendar.minute = 0
        calendar.hour += 1
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        assertEquals(calendar.minute, actualMinute)
        assertEquals(calendar.hour, actualHour)
    }

    @Test
    fun newDay() {
        // arrange
        val calendar = CalendarUtilityMock(23, 59)
        val clock = createClockInstance(calendar = calendar)

        // act
        calendar.minute = 0
        calendar.hour = 0
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        assertEquals(calendar.minute, actualMinute)
        assertEquals(calendar.hour, actualHour)
    }

    @Test
    fun updateToNewData() {
        // arrange
        val calendar = CalendarUtilityMock(0, 0)
        val clock = createClockInstance(calendar = calendar)

        // act
        clock.updateTime()

        // assert
        assertEquals(calendar.getDate(), clock.date.value)
    }

    @Test
    fun setWeatherValues() {
        // arrange
        val calendar = CalendarUtilityMock(10, 10)
        val api: WeatherApi = mock {
            onBlocking { getWeather() } doReturn Response.Success(Weather(1.0f, 2.0f, "icon"))
        }
        val clock = createClockInstance(api, calendar)

        // act
        clock.updateWeather()

        // assert
        assertEquals(true, clock.isWeatherUpToDate.value)
        assertEquals(calendar.getFullDate(), clock.lastSyncDate)
        assertEquals(1.0f, clock.temperature.value)
        assertEquals(2.0f, clock.feelTemperature.value)
        assertEquals("icon", clock.weatherIcon.value)
    }

    @Test
    fun updateWeatherValues() = runBlockingTest {
        // arrange
        val calendar = CalendarUtilityMock(10, 10)
        val api: WeatherApi = mock {
            onBlocking { getWeather() } doReturn Response.Success(Weather(1.0f, 2.0f, "icon"))
        }
        val clock = createClockInstance(api, calendar)

        // act
        clock.updateWeather()
        whenever(api.getWeather()).thenReturn(Response.Success(Weather(10.0f, 20.0f, "icon v2")))
        clock.updateWeather()

        // assert
        assertEquals(true, clock.isWeatherUpToDate.value)
        assertEquals(calendar.getFullDate(), clock.lastSyncDate)
        assertEquals(10.0f, clock.temperature.value)
        assertEquals(20.0f, clock.feelTemperature.value)
        assertEquals("icon v2", clock.weatherIcon.value)
    }

    @Test
    fun failToGetWeatherInFirstTry() {
        // arrange
        val calendar = CalendarUtilityMock(0, 0)
        val api: WeatherApi = mock {
            onBlocking { getWeather() } doReturn Response.Failure("")
        }
        val clock = createClockInstance(api, calendar)

        // act
        clock.updateWeather()

        // assert
        assertEquals(false, clock.isWeatherUpToDate.value)
        assertEquals("", clock.lastSyncDate)
    }

    @Test
    fun getWeatherInSecondTry() = runBlockingTest {
        // arrange
        val calendar = CalendarUtilityMock(0, 0)
        val api: WeatherApi = mock {
            onBlocking { getWeather() } doReturn Response.Failure("")
        }
        val clock = createClockInstance(api, calendar)

        // act
        clock.updateWeather()
        whenever(api.getWeather()).thenReturn(Response.Success(Weather(1.0f, 2.0f, "icon")))
        clock.updateWeather()

        // assert
        assertEquals(true, clock.isWeatherUpToDate.value)
        assertEquals(calendar.getFullDate(), clock.lastSyncDate)
        assertEquals(1.0f, clock.temperature.value)
        assertEquals(2.0f, clock.feelTemperature.value)
        assertEquals("icon", clock.weatherIcon.value)
    }

    @Test
    fun failToGetWeatherInSecondTry() = runBlockingTest {
        // arrange
        val calendar = CalendarUtilityMock(0, 0)
        val api: WeatherApi = mock {
            onBlocking { getWeather() } doReturn Response.Success(Weather(1.0f, 2.0f, "icon"))
        }
        val clock = createClockInstance(api, calendar)

        // act
        clock.updateWeather()
        whenever(api.getWeather()).thenReturn(Response.Failure(""))
        clock.updateWeather()

        // assert
        assertEquals(false, clock.isWeatherUpToDate.value)
        assertEquals(calendar.getFullDate(), clock.lastSyncDate)
    }
}
