package com.srj9.service

import com.srj9.model.LaundryReservation
import com.srj9.repository.LaundryReservationRepository
import com.srj9.util.DateUtils
import com.srj9.util.LocalizedWeek
import com.srj9.util.toDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.*
import java.util.stream.Collectors
import kotlin.streams.toList

@Service
class LaundryReservationService {

    @Autowired
    lateinit var laundryReservationRepository: LaundryReservationRepository

    fun getAllLaundryReservations(): List<LaundryReservation> {
        return laundryReservationRepository.findAll()
    }

    fun getSingleLaundryReservation(laundryId: Long): LaundryReservation {
        return laundryReservationRepository.getOne(laundryId)
    }

    fun createLaundryReservation(laundryReservation: LaundryReservation): LaundryReservation {
        return laundryReservationRepository.save(laundryReservation)
    }

    fun getAllReservationsBetweenDates(from: LocalDate, to: LocalDate): Map<LocalDate, Map<LocalTime, Boolean>> {
        val laundryReservations = laundryReservationRepository.findLaundryReservationsBetweenFirstDayAndLastDay(from.toDate(), to.toDate())
        val laundryTimes = DateUtils.getDatesBetween(from, to)
                .map { it to DateUtils.getTimesBetween(LocalTime.of(6, 0), LocalTime.of(20, 30), 30).map { it to true }.toMap().toMutableMap() }.toMap()
        markReservedTimes(laundryReservations, laundryTimes)
        return laundryTimes
    }

    fun getAllReservationsForCurrentWeek(): Map<LocalDate, Map<LocalTime, Boolean>> {
        val localizedWeek = LocalizedWeek()
        val laundryReservations = laundryReservationRepository.findLaundryReservationsBetweenFirstDayAndLastDay(localizedWeek.getFirstDay().toDate(), localizedWeek.getLastDay().toDate())
        val laundryTimes = DateUtils.getDatesBetween(localizedWeek.getFirstDay(), localizedWeek.getLastDay())
                .map { it to DateUtils.getTimesBetween(LocalTime.of(6, 0), LocalTime.of(20, 30), 30).map { it to true }.toMap().toMutableMap() }.toMap()
        markReservedTimes(laundryReservations, laundryTimes)
        return laundryTimes
    }

    private fun markReservedTimes(laundryReservations: List<LaundryReservation>, laundryTimes: Map<LocalDate, MutableMap<LocalTime, Boolean>>) {
        laundryTimes.forEach { (date, times) ->
            val reservationsForDay = laundryReservations.stream().filter {
                laundryReservation ->
                    convertToLocalDate(laundryReservation.date!!) == (date) }.toList()
                reservationsForDay.forEach { reservationForDay ->
                    val timeFrom = reservationForDay.time_from!!.toLocalDateTime().toLocalTime()
                    val timeUntil = reservationForDay.time_until!!.toLocalDateTime().toLocalTime()
                    for (time in times.keys) {
                        if (time >= timeFrom && time <= timeUntil && checkIfAllWashingMachinesAreTaken(reservationsForDay, timeFrom, timeUntil)) {
                            times[time] = false
                        }
                    }
            }
        }
    }

    private fun checkIfAllWashingMachinesAreTaken(laundryReservations: List<LaundryReservation>, timeFrom: LocalTime, timeUntil: LocalTime): Boolean {
        val filteredReservations = laundryReservations.stream().filter { laundryReservation ->
            laundryReservation.time_from!!.toLocalDateTime().toLocalTime() == timeFrom
                    && laundryReservation.time_until!!.toLocalDateTime().toLocalTime() == timeUntil
        }.collect(Collectors.toList())

        if (filteredReservations.size == 12) {
            return true
        }

        return false
    }

    private fun convertToLocalDate(dateToConvert: Date): LocalDate {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
    }

    fun updateExistingLaundryReservation(newLaundryReservation: LaundryReservation, reservationId: Long): LaundryReservation {
        assert(newLaundryReservation.id == reservationId)
        return laundryReservationRepository.save(newLaundryReservation)
    }

    fun deleteLaundryReservation(reservationId: Long): ResponseEntity<Void> {
        return laundryReservationRepository.findById(reservationId)
                .map { reservation ->
                    laundryReservationRepository.delete(reservation)
                    ResponseEntity<Void>(HttpStatus.OK)
                }.orElse(ResponseEntity.notFound().build())
    }
}
