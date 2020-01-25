package com.srj9.controller

import com.srj9.model.Room
import com.srj9.model.User
import com.srj9.service.RoomService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/members")
@Api(value = "rooms", description = "Rooms to reserve")
class RoomRestController {

    @Autowired
    lateinit var roomService: RoomService

    @GetMapping
    fun getAllRooms(): ResponseEntity<List<Room>> {
        return ResponseEntity.ok(roomService.getAllRooms())
    }

    @GetMapping("/available_rooms")
    fun getAllAvailableRooms(): ResponseEntity<List<Room>> {
        return ResponseEntity.ok(roomService.getAllAvailableRooms())
    }

    @GetMapping("/{roomId}")
    fun getSingleRoom(@PathVariable roomId: Long): ResponseEntity<Room> {
        return ResponseEntity.ok(roomService.getSingleRoom(roomId))
    }

    @PostMapping
    fun createRoom(@RequestBody @Valid room: Room): ResponseEntity<Room> {
        return ResponseEntity.ok(roomService.createRoom(room))
    }

    @PutMapping("/{roomId}")
    fun updateRoom(@RequestBody @Valid room: Room, @PathVariable roomId: Long): ResponseEntity<Room> {
        return ResponseEntity.ok(roomService.updateRoom(room, roomId))
    }

    @DeleteMapping("/{roomId}")
    fun deleteRoom(@PathVariable roomId: Long): ResponseEntity<Unit> {
        return ResponseEntity.ok(roomService.deleteRoom(roomId))
    }

    @GetMapping("/capacity/{roomId}")
    fun getCapacityOfSingleRoom(@PathVariable roomId: Long): ResponseEntity<Int> {
        return ResponseEntity.ok(roomService.getCapacityOfRoom(roomId))
    }

    @GetMapping("/availability/{roomId}")
    fun getAvailabilityOfRoom(@PathVariable roomId: Long): ResponseEntity<Boolean> {
        return ResponseEntity.ok(roomService.getAvailabilityOfRoom(roomId))
    }

    @PostMapping("/add_users/{roomId}")
    fun addUsersToRoom(@RequestBody users: List<User>, @PathVariable roomId: Long): ResponseEntity<Room> {
        return ResponseEntity.ok(roomService.addUsersToRoom(users, roomId))
    }

    @PostMapping("/remove_user/room/{roomId}/user/{userId}")
    fun removeUserFromRoom(@PathVariable roomId: Long, @PathVariable userId: Long): ResponseEntity<Room> {
        return ResponseEntity.ok(roomService.deleteUserFromRoom(roomId, userId))
    }

    @GetMapping("/get_users/{roomId}")
    fun getAllUsersOfSingleRoom(@PathVariable roomId: Long): ResponseEntity<List<User>> {
        return ResponseEntity.ok(roomService.getUsersOfRoom(roomId))
    }


}