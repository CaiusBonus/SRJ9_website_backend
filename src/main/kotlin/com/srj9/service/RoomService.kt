package com.srj9.service

import com.srj9.model.Room
import com.srj9.model.User
import com.srj9.repository.RoomRepository
import com.srj9.repository.UserRepository
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RoomService {

    @Autowired
    lateinit var roomRepository: RoomRepository

    @Autowired
    lateinit var userRepository: UserRepository

    fun getAllRooms(): List<Room> {
        return roomRepository.findAll()
    }

    fun getSingleRoom(roomId: Long): Room {
        return roomRepository.getOne(roomId)
    }

    fun getAllAvailableRooms(): List<Room> {
        return roomRepository.findAllByIsAvailableEquals(true)
    }

    // TODO: Upravit UPDATE na mapovanie
    fun updateRoom(room: Room, roomId: Long): Room {
        return roomRepository.save(room)
    }

    // TODO: Pridat patch

    fun createRoom(room: Room): Room {
        if (room.users != null) {
           room.capacity = (room.capacity!! - room.users!!.count())
            if (room.capacity == 0) {
                room.isAvailable = false
            }
        }

        if (room.capacity!! > 4) {
            throw java.lang.RuntimeException()
        }

        return roomRepository.save(room)
    }

    fun deleteRoom(roomId: Long) {
        val room = roomRepository.getOne(roomId)
        roomRepository.delete(room)
    }

    fun getCapacityOfRoom(roomId: Long): Int {
        val room = roomRepository.getOne(roomId)
        return room.capacity!!
    }

    fun getAvailabilityOfRoom(roomId: Long): Boolean {
        val room = roomRepository.getOne(roomId)
        return room.isAvailable!!
    }

    fun addUsersToRoom(users: List<User>, roomId: Long): Room {
        val room = roomRepository.getOne(roomId)
        val usersToAdd: MutableList<User> = ArrayList()
        users.forEach{ user ->
            usersToAdd.add(userRepository.getOne(user.id!!))
        }

        Hibernate.initialize(room.users)

        usersToAdd.forEach { user ->
            if (room.capacity!! > 0 && room.isAvailable!!) {
                room.users!!.add(user)
                room.capacity = room.capacity!!.minus(1)
                if (room.capacity == 0) {
                    room.isAvailable = false
                }
            } else {
                throw RuntimeException()
            }
        }

        return roomRepository.save(room)
    }

    fun deleteUserFromRoom(roomId: Long, userId: Long): Room {
        val room = roomRepository.getOne(roomId)
        val user = userRepository.getOne(userId)

        room.users!!.remove(user)
        room.capacity = room.capacity!!.plus(1)
        if (room.capacity!! > 0) {
            room.isAvailable = true
        }
        return roomRepository.save(room)
    }

    fun getUsersOfRoom(roomId: Long): List<User> {
        return if (roomRepository.getOne(roomId).users != null) {
            roomRepository.getOne(roomId).users!!.toList()
        } else {
            ArrayList()
        }
    }


}