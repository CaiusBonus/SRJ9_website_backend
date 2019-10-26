package com.srj9

import com.srj9.enums.Status
import com.srj9.model.GymReservation
import com.srj9.service.GymReservationService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertNotNull


@SpringBootTest
@RunWith(SpringRunner::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GymReservationApiTest {

    @Autowired lateinit var gymReservationService: GymReservationService

    @Test
    fun createNewGymReservation() {
        val newGymReservation = gymReservationService.createGymReservation(GymReservation(0L,"3123131", Date(), null,null, Status.RESERVED, 2))
        println(newGymReservation)
        assertNotNull(newGymReservation)
    }

    @Test
    fun getAllGymReservations() {
        gymReservationService.createGymReservation(GymReservation(0L,"3123131", Date(), null,null, Status.RESERVED, 2))
        val reservations = gymReservationService.getAllGymReservations()
        println(reservations.size)
        assertNotNull(reservations)
    }

    @Test
    fun getSingleGymReservation() {
        gymReservationService.createGymReservation(GymReservation(0L,"3123131", Date(), null,null, Status.RESERVED, 2))
        val singleReservation = gymReservationService.getSingleGymReservation(1L)
        println(singleReservation)
        assertNotNull(singleReservation)
    }

    @Test
    fun updateExistingReservation() {
        gymReservationService.createGymReservation(GymReservation(0L,"3123131", Date(), null,null, Status.RESERVED, 2))
        val updatedReservation = gymReservationService.updateExistingGymReservation(GymReservation(0L,"55555", Date(), null,null, Status.RESERVED, 2), 1L)
        println(updatedReservation)
        assert(updatedReservation.statusCode.is2xxSuccessful)
    }

    @Test
    fun deleteExistingReservation() {
        gymReservationService.createGymReservation(GymReservation(0L,"3123131", Date(), null,null, Status.RESERVED, 2))
        val deletedReservation = gymReservationService.deleteGymReservation(1L)
        println(deletedReservation)
        assert(deletedReservation.statusCode.is2xxSuccessful)
    }

    @Test
    fun shouldReceiveReservationsForSpecificWeek() {
        gymReservationService.createGymReservation(GymReservation(0L,"3123131", Date(), Timestamp.valueOf("2019-10-14 19:00:00"),Timestamp.valueOf("2019-10-14 20:00:00"), Status.RESERVED, 2))
        val reservationsForSpecificWeek = gymReservationService.getAllReservationsForCurrentWeek()
        println(reservationsForSpecificWeek)
        assert(reservationsForSpecificWeek.isNotEmpty())
    }

    @Test
    fun shouldReceiveEmptyListWithReservations() {
        gymReservationService.createGymReservation(GymReservation(0L,"3123131", SimpleDateFormat("yyyy-MM-dd").parse("2019-10-24"), Timestamp.valueOf("2019-10-24 19:00:00"),Timestamp.valueOf("2019-10-24 20:00:00"), Status.RESERVED, 2))
        val reservationsForSpecificWeek = gymReservationService.getAllReservationsForCurrentWeek()
        println(reservationsForSpecificWeek)
        assert(reservationsForSpecificWeek.isEmpty())
    }

}
