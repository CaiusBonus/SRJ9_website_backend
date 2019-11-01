package com.srj9.service

import com.srj9.enums.Status
import com.srj9.model.GymReservation
import com.srj9.model.User
import com.srj9.repository.GymReservationRepository
import com.srj9.util.LocalizedWeek
import com.srj9.util.toDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject


@Service
class GymReservationService {

    @Autowired
    lateinit var gymReservationRepository: GymReservationRepository

    @Autowired
    lateinit var emailService: EmailService

    var gymReservationsForFirstGym :MutableList<GymReservation> = ArrayList()
    var gymReservationsForSecondGym :MutableList<GymReservation> = ArrayList()
    var times_from = arrayOf("21:00","22:00","23:00","00:00")
    var times_until = arrayOf("21:59","22:59","23:59","00:59")
    val date = LocalDate.now()

    fun getAllGymReservations(): List<GymReservation> {
        createGymReservationsForFirstGym()
        createGymReservationsForSecondGym()
        return gymReservationRepository.findAll()
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

    fun updateExistingGymReservation(newGymReservation: GymReservation, reservationId: Long): ResponseEntity<GymReservation> {
        return gymReservationRepository.findById(reservationId)
                .map {
                    existingGymReservation ->
                    val updatedReservation: GymReservation = existingGymReservation
                            .copy(reservation_number =  newGymReservation.reservation_number,
                                    date = newGymReservation.date,
                                    time_from = newGymReservation.time_from,
                                    time_until = newGymReservation.time_until,
                                    status = newGymReservation.status,
                                    gym_number = newGymReservation.gym_number,
                                    user = newGymReservation.user)
                    ResponseEntity.ok().body(gymReservationRepository.save(updatedReservation))
                }.orElse(ResponseEntity.notFound().build())
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
                for (j in 0..3) { gymReservationsForFirstGym.add(GymReservation().copy(date = firstTuesdayInMonth.toDate(), status = Status.FREE, gym_number = 1, time_from = times_from[j], time_until = times_until[j])) }
            }

            if (checkIfIsIteratorInSpecificRange(arrayOf(3,10,17,24,31,38).toList(),i)) {
                for (j in 0..3) { gymReservationsForFirstGym.add(GymReservation().copy(date = firstThurstDayInMonth.toDate(), status = Status.FREE, gym_number = 1, time_from = times_from[j], time_until = times_until[j])) }
            }

            if (checkIfIsIteratorInSpecificRange(arrayOf(6,13,20,27,34,41).toList(),i)) {
                for (j in 0..3) { gymReservationsForFirstGym.add(GymReservation().copy(date = firstSundayInMonth.toDate(), status = Status.FREE, gym_number = 1, time_from = times_from[j], time_until = times_until[j])) }
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
                for (j in 0..3) { gymReservationsForSecondGym.add(GymReservation().copy(date = firstTuesdayInMonth.toDate(), status = Status.FREE, gym_number = 2, time_from = times_from[j], time_until = times_until[j])) }
            }

            if (checkIfIsIteratorInSpecificRange(arrayOf(3,10,17,24,31,38).toList(),i)) {
                for (j in 0..3) { gymReservationsForSecondGym.add(GymReservation().copy(date = firstThurstDayInMonth.toDate(), status = Status.FREE, gym_number = 2, time_from = times_from[j], time_until = times_until[j])) }
            }
        }
        gymReservationsForSecondGym.forEach { reservation -> gymReservationRepository.save(reservation) }
    }

    fun checkIfIsIteratorInSpecificRange(array: List<Number>, num: Number): Boolean {
        return array.stream().anyMatch { number -> number == num }
    }

    fun sendConfirmationEmail(gymReservation: GymReservation, user: User) {
        val text = emailService.createMessage("Nova Rezervacia", "", "", "")
        emailService.sendConfirmationMessageToReceiver(user.email!!, "Nova rezervacka", text)
    }
}
