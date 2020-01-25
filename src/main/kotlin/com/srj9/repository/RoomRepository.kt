package com.srj9.repository

import com.srj9.model.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository: JpaRepository<Room, Long> {

    fun findAllByIsAvailableEquals(isAvailable: Boolean): List<Room>
}