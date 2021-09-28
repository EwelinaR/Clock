package com.example.clock

import com.example.clock.calendar.CalendarUtility
import com.example.clock.calendar.CalendarUtilityMock
import com.example.clock.weather.Weather
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class ClockViewModelTest {

    @Test
    fun increaseUnitsOfMinute() {
        // arrange
        val minute = 7
        val hour = 12
        val calendarMock = CalendarUtilityMock(hour, minute)
        val clock = ClockViewModel(calendarMock)

        // act
        calendarMock.setMinute(minute + 1)
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        Assertions.assertEquals(minute + 1, actualMinute)
        Assertions.assertEquals(hour, actualHour)
    }


    @Test
    fun increaseTensOfMinute() {
        // arrange
        val hour = 8
        val minute = 19
        val calendarMock = CalendarUtilityMock(hour, minute)
        val clock = ClockViewModel(calendarMock)

        // act
        calendarMock.setMinute(minute + 1)
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        Assertions.assertEquals(minute + 1, actualMinute)
        Assertions.assertEquals(hour, actualHour)
    }

    @Test
    fun increaseUnitsOfHour() {
        // arrange
        val hour = 12
        val minute = 59
        val calendarMock = CalendarUtilityMock(hour, minute)
        val clock = ClockViewModel(calendarMock)

        // act
        calendarMock.setMinute(0)
        calendarMock.setHour(hour + 1)
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        Assertions.assertEquals(0, actualMinute)
        Assertions.assertEquals(hour + 1, actualHour)
    }

    @Test
    fun increaseTensOfHour() {
        // arrange
        val hour = 9
        val minute = 59
        val calendarMock = CalendarUtilityMock(hour, minute)
        val clock = ClockViewModel(calendarMock)

        // act
        calendarMock.setMinute(0)
        calendarMock.setHour(hour + 1)
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        Assertions.assertEquals(0, actualMinute)
        Assertions.assertEquals(hour + 1, actualHour)
    }

    @Test
    fun newDay() {
        // arrange
        val hour = 23
        val minute = 59
        val calendarMock = CalendarUtilityMock(hour, minute)
        val clock = ClockViewModel(calendarMock)

        // act
        calendarMock.setMinute(0)
        calendarMock.setHour(0)
        clock.updateTime()
        val actualMinute = clock.tensOfMinute.value!! * 10 + clock.unitsOfMinute.value!!
        val actualHour = clock.tensOfHour.value!! * 10 + clock.unitsOfHour.value!!

        // assert
        Assertions.assertEquals(0, actualMinute)
        Assertions.assertEquals(0, actualHour)
    }

    @Test
    fun updateToNewData() {
        // arrange
        val calendarMock = CalendarUtilityMock(0, 0, "first")
        val clock = ClockViewModel(calendarMock)

        // act
        calendarMock.setDate("second")
        clock.updateTime()

        // assert
        Assertions.assertEquals("second", clock.date.value)
    }

    @Test
    fun updateWeather() {
        // arrange
        val calendarMock = CalendarUtilityMock(10, 10)
        val clock = ClockViewModel(calendarMock)

        // act
        clock.updateWeatherValues(Weather(1.0f, 2.0f, "icon"))
        val date = CalendarUtility().getFormattedFullDate()

        // assert
        Assertions.assertEquals(true, clock.isWeatherUpToDate.value)
        Assertions.assertEquals(date, clock.lastSyncDate)
    }

    @Test
    fun failToGetWeatherInFirstTry() {
        // arrange
        val calendarMock = CalendarUtilityMock(0, 0)
        val clock = ClockViewModel(calendarMock)

        // act
        clock.updateWeatherValues(null)

        // assert
        Assertions.assertEquals(false, clock.isWeatherUpToDate.value)
        Assertions.assertEquals("", clock.lastSyncDate)
    }

    @Test
    fun failToGetWeatherInSecondTry() {
        // arrange
        val calendarMock = CalendarUtilityMock(0, 0)
        val clock = ClockViewModel(calendarMock)

        // act
        clock.updateWeatherValues(null)
        clock.updateWeatherValues(Weather(1.0f, 2.0f, "icon"))
        val date = CalendarUtility().getFormattedFullDate()

        // assert
        Assertions.assertEquals(true, clock.isWeatherUpToDate.value)
        Assertions.assertEquals(date, clock.lastSyncDate)
    }
}
