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

}
