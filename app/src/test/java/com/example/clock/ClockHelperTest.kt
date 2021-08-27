package com.example.clock

import com.example.clock.calendar.CalendarUtilityMock
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class ClockHelperTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 9, 20, 23])
    fun isHourStoredProperly(hour: Int) {
        // arrange
        val calendarHelper = CalendarHelper(CalendarUtilityMock(hour, 0))

        // act
        val actualHour = calendarHelper.getTensOfHour() * 10 + calendarHelper.getUnitsOfHour()

        // assert
        assertEquals(hour, actualHour)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 11, 32, 59])
    fun isMinuteStoredProperly(minute: Int) {
        // arrange
        val calendarHelper = CalendarHelper(CalendarUtilityMock(0, minute))

        // act
        val actualMinute = calendarHelper.getTensOfMinute() * 10 + calendarHelper.getUnitsOfMinute()

        // assert
        assertEquals(minute, actualMinute)
    }

    @Test
    fun increaseUnitsOfMinute() {
        // arrange
        val minute = 7
        val hour = 12
        val calendarMock = CalendarUtilityMock(hour, minute)
        val calendarHelper = CalendarHelper(calendarMock)

        // act
        calendarMock.setMinute(minute + 1)
        val isOverloaded = calendarHelper.isUnitsOfMinuteOverload()

        // assert
        assertEquals(false, isOverloaded)
        assertEquals(minute + 1, calendarHelper.getMinute())
        assertEquals(hour, calendarHelper.getHour())
    }

    @Test
    fun increaseTensOfMinute() {
        // arrange
        val minute = 19
        val hour = 8
        val calendarMock = CalendarUtilityMock(hour, minute)
        val calendarHelper = CalendarHelper(calendarMock)

        // act
        calendarMock.setMinute(minute + 1)
        val isUnitsOverloaded = calendarHelper.isUnitsOfMinuteOverload()
        val isTensOverloaded = calendarHelper.isTensOfMinuteOverload()

        // assert
        assertEquals(true, isUnitsOverloaded)
        assertEquals(false, isTensOverloaded)
        assertEquals(minute + 1, calendarHelper.getMinute())
        assertEquals(hour, calendarHelper.getHour())
    }

    @Test
    fun increaseUnitsOfHour() {
        // arrange
        val minute = 59
        val hour = 12
        val calendarMock = CalendarUtilityMock(hour, minute)
        val calendarHelper = CalendarHelper(calendarMock)

        // act
        calendarMock.setMinute(0)
        calendarMock.setHour(hour + 1)
        val isUnitsOfMinuteOverloaded = calendarHelper.isUnitsOfMinuteOverload()
        val isTensOfMinuteOverloaded = calendarHelper.isTensOfMinuteOverload()
        val isUnitsOfHourOverloaded = calendarHelper.isUnitsOfHourOverload()

        // assert
        assertEquals(true, isUnitsOfMinuteOverloaded)
        assertEquals(true, isTensOfMinuteOverloaded)
        assertEquals(false, isUnitsOfHourOverloaded)
        assertEquals(0, calendarHelper.getMinute())
        assertEquals(hour + 1, calendarHelper.getHour())
    }

    @Test
    fun increaseTensOfHour() {
        // arrange
        val minute = 59
        val hour = 9
        val calendarMock = CalendarUtilityMock(hour, minute)
        val calendarHelper = CalendarHelper(calendarMock)

        // act
        calendarMock.setMinute(0)
        calendarMock.setHour(hour + 1)
        val isUnitsOfMinuteOverloaded = calendarHelper.isUnitsOfMinuteOverload()
        val isTensOfMinuteOverloaded = calendarHelper.isTensOfMinuteOverload()
        val isUnitsOfHourOverloaded = calendarHelper.isUnitsOfHourOverload()
        val isTensOfHourOverloaded = calendarHelper.isTensOfHourOverload()

        // assert
        assertEquals(true, isUnitsOfMinuteOverloaded)
        assertEquals(true, isTensOfMinuteOverloaded)
        assertEquals(true, isUnitsOfHourOverloaded)
        assertEquals(false, isTensOfHourOverloaded)
        assertEquals(0, calendarHelper.getMinute())
        assertEquals(hour + 1, calendarHelper.getHour())
    }

    @Test
    fun newDay() {
        // arrange
        val minute = 59
        val hour = 23
        val calendarMock = CalendarUtilityMock(hour, minute)
        val calendarHelper = CalendarHelper(calendarMock)

        // act
        calendarMock.setMinute(0)
        calendarMock.setHour(0)
        val isUnitsOfMinuteOverloaded = calendarHelper.isUnitsOfMinuteOverload()
        val isTensOfMinuteOverloaded = calendarHelper.isTensOfMinuteOverload()
        val isUnitsOfHourOverloaded = calendarHelper.isUnitsOfHourOverload()
        val isTensOfHourOverloaded = calendarHelper.isTensOfHourOverload()

        // assert
        assertEquals(true, isUnitsOfMinuteOverloaded)
        assertEquals(true, isTensOfMinuteOverloaded)
        assertEquals(true, isUnitsOfHourOverloaded)
        assertEquals(true, isTensOfHourOverloaded)
        assertEquals(0, calendarHelper.getMinute())
        assertEquals(0, calendarHelper.getHour())
    }
}
