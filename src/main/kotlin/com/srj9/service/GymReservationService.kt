package com.srj9.service

import com.srj9.enums.Status
import com.srj9.exception.NumberOfReservationsExceed
import com.srj9.model.GymReservation
import com.srj9.model.User
import com.srj9.repository.GymReservationRepository
import com.srj9.util.LocalizedWeek
import com.srj9.util.toDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*
import java.util.stream.Collectors
import kotlin.collections.ArrayList


@Service
class GymReservationService {

    @Autowired
    lateinit var gymReservationRepository: GymReservationRepository

    @Autowired
    lateinit var emailServiceImpl: EmailServiceImpl

    @Autowired
    lateinit var sportOfficerService: SportOfficerService

    @Autowired
    lateinit var userService: UserService

    var gymReservationsForFirstGym :MutableList<GymReservation> = ArrayList()
    var gymReservationsForSecondGym :MutableList<GymReservation> = ArrayList()
    var timesFrom = arrayOf("21:00","22:00","23:00","00:00")
    var timesUntil = arrayOf("21:59","22:59","23:59","00:59")
    val date = LocalDate.now()

    fun getAllGymReservationsWithoutClosedReservations(): List<GymReservation> {
        return gymReservationRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .filter { gymReservation -> gymReservation.status != Status.CLOSED }
    }

    fun getAllGymReservations(): List<GymReservation> {
        return gymReservationRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
    }

    fun getAllReservationsForSpecificUser(userId: Long): List<GymReservation> {
        return gymReservationRepository.findGymReservationsByUserId(userId)
    }

    fun getSingleGymReservation(reservationId: Long): GymReservation {
        return gymReservationRepository.getOne(reservationId)
    }

    fun createGymReservation(gymReservation: GymReservation): GymReservation {
        return gymReservationRepository.save(gymReservation)
    }

    fun getAllReservationsForCurrentWeek(): List<GymReservation> {
        val localizedWeek = LocalizedWeek()
        localizedWeek.getFirstDay()
        localizedWeek.getLastDay()
        return gymReservationRepository.findGymReservationsBetweenFirstDayAndLastDay(localizedWeek.getFirstDay().toDate(), localizedWeek.getLastDay().toDate())
    }

    fun getAllReservationsBetweenDates(from: LocalDate, to: LocalDate): List<GymReservation> {
        return gymReservationRepository.findGymReservationsBetweenFirstDayAndLastDay(from.toDate(), to.toDate())
    }

    fun updateExistingGymReservation(newGymReservation: GymReservation, reservationId: Long): GymReservation {
        return if (newGymReservation.user != null) {
            if (checkIfReservationIsInFirstOrSecondWeek(newGymReservation.date!!) == 1 && checkIfUserReachedMaximumReservationsForOneWeek(newGymReservation.user!!.id!!, 0)) {
                addUserToExistingReservation(newGymReservation, reservationId)
            } else if (checkIfReservationIsInFirstOrSecondWeek(newGymReservation.date!!) == 2 && checkIfUserReachedMaximumReservationsForOneWeek(newGymReservation.user!!.id!!, 1)) {
                addUserToExistingReservation(newGymReservation, reservationId)
            } else {
                throw NumberOfReservationsExceed()
            }
        } else {
            removeUserFromExistingReservation(newGymReservation,reservationId)
        }
    }

    fun removeUserFromExistingReservation(gymReservation: GymReservation, reservationId: Long): GymReservation {
        assert(gymReservation.id == reservationId)
        return gymReservationRepository.save(gymReservation)
    }

    fun addUserToExistingReservation(gymReservation: GymReservation, reservationId: Long): GymReservation {
        assert(gymReservation.id == reservationId)
        sendConfirmationEmail(gymReservation,gymReservation.user!!)
        return gymReservationRepository.save(gymReservation)
    }

    fun deleteGymReservation(reservationId: Long): ResponseEntity<Void> {
        return gymReservationRepository.findById(reservationId)
                .map { reservation ->
                    gymReservationRepository.delete(reservation)
                    ResponseEntity<Void>(HttpStatus.OK)
                }.orElse(ResponseEntity.notFound().build())
    }

    fun createGymReservationsForFirstGym() {
        var firstTuesdayInMonth = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY))
        var firstThurstDayInMonth = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY))
        var firstSundayInMonth = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))

        for (i in 1..41) {
            if (checkIfIsIteratorInSpecificRange(arrayOf(8,15,22,29,36).toList(),i)) firstTuesdayInMonth = firstTuesdayInMonth.plusWeeks(1)
            if (checkIfIsIteratorInSpecificRange(arrayOf(10,17,24,31,38).toList(),i)) firstThurstDayInMonth = firstThurstDayInMonth.plusWeeks(1)
            if(checkIfIsIteratorInSpecificRange(arrayOf(13,20,27,34,41).toList(),i)) firstSundayInMonth = firstSundayInMonth.plusWeeks(1)

            if (checkIfIsIteratorInSpecificRange(arrayOf(1,8,15,22,29,36).toList(),i)) {
                for (j in 0..3) { gymReservationsForFirstGym.add(GymReservation().copy(date = firstTuesdayInMonth.toDate(), status = Status.FREE, gym_number = 1, time_from = timesFrom[j], time_until = timesUntil[j])) }
            }

            if (checkIfIsIteratorInSpecificRange(arrayOf(3,10,17,24,31,38).toList(),i)) {
                for (j in 0..3) { gymReservationsForFirstGym.add(GymReservation().copy(date = firstThurstDayInMonth.toDate(), status = Status.FREE, gym_number = 1, time_from = timesFrom[j], time_until = timesUntil[j])) }
            }

            if (checkIfIsIteratorInSpecificRange(arrayOf(6,13,20,27,34,41).toList(),i)) {
                for (j in 0..3) { gymReservationsForFirstGym.add(GymReservation().copy(date = firstSundayInMonth.toDate(), status = Status.FREE, gym_number = 1, time_from = timesFrom[j], time_until = timesUntil[j])) }
            }
        }

        gymReservationsForFirstGym.forEach { reservation -> gymReservationRepository.save(reservation) }
    }

    fun createGymReservationsForSecondGym() {
        var firstTuesdayInMonth = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.TUESDAY))
        var firstThurstDayInMonth = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY))

        for (i in 1..41) {
            if (checkIfIsIteratorInSpecificRange(arrayOf(8,15,22,29,36).toList(),i)) firstTuesdayInMonth = firstTuesdayInMonth.plusWeeks(1)
            if (checkIfIsIteratorInSpecificRange(arrayOf(10,17,24,31,38).toList(),i)) firstThurstDayInMonth = firstThurstDayInMonth.plusWeeks(1)

            if (checkIfIsIteratorInSpecificRange(arrayOf(1,8,15,22,29,36).toList(),i)) {
                for (j in 0..3) { gymReservationsForSecondGym.add(GymReservation().copy(date = firstTuesdayInMonth.toDate(), status = Status.FREE, gym_number = 2, time_from = timesFrom[j], time_until = timesUntil[j])) }
            }

            if (checkIfIsIteratorInSpecificRange(arrayOf(3,10,17,24,31,38).toList(),i)) {
                for (j in 0..3) { gymReservationsForSecondGym.add(GymReservation().copy(date = firstThurstDayInMonth.toDate(), status = Status.FREE, gym_number = 2, time_from = timesFrom[j], time_until = timesUntil[j])) }
            }
        }
        gymReservationsForSecondGym.forEach { reservation -> gymReservationRepository.save(reservation) }
    }

    fun checkIfIsIteratorInSpecificRange(array: List<Number>, num: Number): Boolean {
        return array.stream().anyMatch { number -> number == num }
    }

    fun sendConfirmationEmail(gymReservation: GymReservation, user: User) {
        val sportOfficer = sportOfficerService.getAllSportOfficers()
        val userEmail = userService.getSingleUser(user.id!!).email
        emailServiceImpl.sendMessageToUser(userEmail!!, gymReservation)
        emailServiceImpl.sendMessageToSportOfficer(sportOfficer[0].email!!, gymReservation)
    }

    fun closeReservationEarlierThanToday() {
        val allReservations: List<GymReservation>
        allReservations = this.getAllGymReservations().stream()
                .filter { reservation ->
                    isDateEarlierThanToday(reservation.date!!, date.toDate())
                }.collect(Collectors.toList())
        allReservations.forEach { gymReservation -> gymReservation.status = Status.CLOSED }
        allReservations.forEach { gymReservation -> gymReservationRepository.save(gymReservation) }
    }

    private fun isDateEarlierThanToday(firstDate: Date, secondDate: Date): Boolean {
        return firstDate < secondDate
    }

    private fun checkIfUserReachedMaximumReservationsForOneWeek(userId: Long, weeksToAdd: Long): Boolean {
        println(LocalizedWeek().getFirstDay().plusWeeks(weeksToAdd))
        val allReservations = gymReservationRepository.
                findGymReservationsBetweenFirstDayAndLastDay(LocalizedWeek().getFirstDay().plusWeeks(weeksToAdd).toDate(), LocalizedWeek().getLastDay().plusWeeks(weeksToAdd).minusDays(1).toDate())
                .filter { gymReservation -> gymReservation.user != null }
                .filter { gymReservation -> gymReservation.user!!.id == userId }
                .filter { gymReservation -> gymReservation.status == Status.RESERVED || gymReservation.status == Status.CLOSED }

        return allReservations.size < 2
    }

    private fun checkIfDateIsBetweenCurrentWeek(date: Date): Boolean {
        return if (date == LocalizedWeek().getFirstDay().toDate()) {
            true
        } else {
            date.after(LocalizedWeek().getFirstDay().toDate()) && date.before(LocalizedWeek().getLastDay().minusDays(1).toDate())
        }
    }

    private fun checkIfDateIsBetweenAnotherWeek(date: Date): Boolean {
        return if (date == LocalizedWeek().getFirstDay().plusWeeks(1).toDate()) {
            true
        } else {
            date.after(LocalizedWeek().getFirstDay().plusWeeks(1).toDate()) && date.before(LocalizedWeek().getLastDay().plusWeeks(1).minusDays(1).toDate())
        }
    }

    private fun checkIfReservationIsInFirstOrSecondWeek(date: Date): Int {
        return when {
            checkIfDateIsBetweenCurrentWeek(date) -> 1
            checkIfDateIsBetweenAnotherWeek(date) -> 2
            else -> 0
        }
    }
}
