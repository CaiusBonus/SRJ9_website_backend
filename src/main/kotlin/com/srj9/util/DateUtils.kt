package com.srj9.util

import java.time.LocalDate
import java.time.LocalTime
import java.util.stream.Collectors
import java.time.temporal.ChronoUnit
import java.util.stream.IntStream


class DateUtils {
    companion object {
        @JvmStatic
        fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
            val numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate)
            return IntStream.iterate(0) { i -> i + 1 }
                    .limit(numOfDaysBetween)
                    .mapToObj { i -> startDate.plusDays(i.toLong()) }
                    .collect(Collectors.toList())
        }

        @JvmStatic
        fun getTimesBetween(startTime: LocalTime, endTime: LocalTime, gapInMinutes: Int): List<LocalTime> {
            var times = ArrayList<LocalTime>()
            var time = startTime
            while (time < endTime) {
                times.add(time)
                time = time.plusMinutes(gapInMinutes.toLong())
            }
            return times
        }
    }
}