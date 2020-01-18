package com.srj9.repository

import com.srj9.model.WashingMachine
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WashingMachineRepository: JpaRepository<WashingMachine, Long> {
}