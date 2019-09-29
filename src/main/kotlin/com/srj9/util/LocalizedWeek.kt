package com.srj9.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.*

class LocalizedWeek {
    private val tz: ZoneId = ZoneId.systemDefault() // TODO Consider if system default is enough or we need to use something like ZoneId.of("Europe/Paris")
    private val locale: Locale = Locale.ENGLISH
    private val firstDayOfWeek: DayOfWeek = WeekFields.of(locale).firstDayOfWeek
    private val lastDayOfWeek: DayOfWeek = DayOfWeek.of(((firstDayOfWeek.value + 5) % DayOfWeek.values().size) + 1)

    fun getFirstDay(): LocalDate {
        return LocalDate.now(tz).with(TemporalAdjusters.previousOrSame(this.firstDayOfWeek))
    }

    fun getLastDay(): LocalDate {
        return LocalDate.now(tz).with(TemporalAdjusters.nextOrSame(this.lastDayOfWeek))
    }
}

fun LocalDate.toDate(): Date = Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
