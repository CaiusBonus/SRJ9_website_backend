package com.srj9.config

import com.srj9.service.GymReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class CronConfig {

    @Autowired
    lateinit var gymReservationService: GymReservationService

    // Cron sa spusta kazdy 15-ty den v mesiaci
    @Scheduled(cron = "0 15 10 15 * ?")
    fun scheduleFixedRateTask() {
        println("------------------CRON JOB STARTED: CREATING RESERVATIONS------------------")
        gymReservationService.createGymReservationsForFirstGym()
        gymReservationService.createGymReservationsForSecondGym()
    }

    // Cron sa spusta kazdu noc o 00:10
    @Scheduled(cron = "10 0 * * * ?")
    fun scheduledFixedRateTaskForEveryDay() {
        println("------------------CRON JOB STARTED: CLOSING OLD RESERVATIONS------------------")
        gymReservationService.closeReservationEarlierThanToday()
    }
}