package com.example.clock

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.*

class ClockHelperTest {

    @ParameterizedTest
    @ValueSource(ints = [0, 9, 20, 23])
    fun isHourStoredProperly(hour: Int) {
        // arrange
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)

        // act
        val calendarHelper = CalendarHelper(calendar)
        val actualHour = calendarHelper.getTensOfHour() * 10 + calendarHelper.getUnitsOfHour()

        // assert
        assertEquals(hour, actualHour)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 11, 32, 59])
    fun isMinuteStoredProperly(minute: Int) {
        // arrange
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, minute)

        // act
        val calendarHelper = CalendarHelper(calendar)
        val actualMinute = calendarHelper.getTensOfMinute() * 10 + calendarHelper.getUnitsOfMinute()

        // assert
        assertEquals(minute, actualMinute)
    }
}