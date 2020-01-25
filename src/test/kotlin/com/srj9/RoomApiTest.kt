package com.srj9

import com.srj9.enums.RoomBlock
import com.srj9.model.Room
import com.srj9.model.User
import com.srj9.repository.UserRepository
import com.srj9.service.RoomService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertFails
import kotlin.test.assertTrue

@SpringBootTest
@RunWith(SpringRunner::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomApiTest {

    @Autowired
    lateinit var roomService: RoomService

    @Autowired
    lateinit var userRepository: UserRepository

    /**
        Test should failed. Cannot create two rooms with same Block and RoomNumber
     */
    @Test
    fun createTwoRoomsWithSameRoomNumber() {
        val users: MutableList<User> = ArrayList()
        val user1 = User()
        val user2 = User()
        val user3 = User()
        val user4 = User()
        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)
        userRepository.save(user4)
        users.add(user1)
        users.add(user2)
        users.add(user3)
        users.add(user4)

        val room: Room = Room(0L, users, RoomBlock.A, 712, true, 4)
        val room2: Room = Room(0L, users, RoomBlock.A, 712, true, 4)

        val id1 = roomService.createRoom(room)
        assertFails { roomService.createRoom(room2) }


        userRepository.delete(user1)
        userRepository.delete(user2)
        userRepository.delete(user3)
        userRepository.delete(user4)

        roomService.deleteRoom(id1.id!!)
    }

    /**
     * In Room are existing 2 users, 4 more users added. Test should failed. Maximum capacity of room is 4
     */
    @Test
    fun shouldAddMoreUsersToRoom() {
        val users: MutableList<User> = ArrayList()
        val users2: MutableList<User> = ArrayList()

        val user1 = User()
        val user2 = User()
        val user3 = User()
        val user4 = User()
        val user5 = User()
        val user6 = User()

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)
        userRepository.save(user4)
        userRepository.save(user5)
        userRepository.save(user6)

        users.add(user1)
        users.add(user2)

        users2.add(user3)
        users2.add(user4)
        users2.add(user5)
        users2.add(user6)

        val room: Room = Room(0L, users, RoomBlock.A, 712, true, 4)
        val id1 = roomService.createRoom(room)

        assertFails { roomService.addUsersToRoom(users2, id1.id!!) }

        userRepository.delete(user1)
        userRepository.delete(user2)
        userRepository.delete(user3)
        userRepository.delete(user4)
        userRepository.delete(user5)
        userRepository.delete(user6)

        roomService.deleteRoom(id1.id!!)
    }

    /**
     * To the room are added 4 users. Room should be not available
     */
    @Test
    fun shourReturnRealAvailabilityOfRoom() {
        val users: MutableList<User> = ArrayList()

        val user1 = User()
        val user2 = User()
        val user3 = User()
        val user4 = User()

        users.add(user1)
        users.add(user2)
        users.add(user3)
        users.add(user4)

        userRepository.saveAll(users)

        val room: Room = Room(0L, null, RoomBlock.A, 712, true, 4)
        var id1 = roomService.createRoom(room)

        id1 = roomService.addUsersToRoom(users, id1.id!!)


        assertTrue { !roomService.getAvailabilityOfRoom(id1.id!!) }

        userRepository.delete(user1)
        userRepository.delete(user2)
        userRepository.delete(user3)
        userRepository.delete(user4)

        roomService.deleteRoom(id1.id!!)
    }

    /**
     * Should remove single user from room, change availability and capacity of room
     */
    @Test
    fun shouldRemoveUserFromRoom() {
        val users: MutableList<User> = ArrayList()

        val user1 = User()
        val user2 = User()
        val user3 = User()
        val user4 = User()

        users.add(user1)
        users.add(user2)
        users.add(user3)
        users.add(user4)

        userRepository.save(user1)
        userRepository.save(user2)
        userRepository.save(user3)
        val userToDelete = userRepository.save(user4)


        val room: Room = Room(0L, null, RoomBlock.A, 712, true, 4)
        var id1 = roomService.createRoom(room)

        id1 = roomService.addUsersToRoom(users, id1.id!!)

        id1 = roomService.deleteUserFromRoom(id1.id!!, userToDelete.id!!)

        assertTrue { id1.capacity == 1 && id1.isAvailable == true }

        userRepository.delete(user1)
        userRepository.delete(user2)
        userRepository.delete(user3)
        userRepository.delete(user4)

        roomService.deleteRoom(id1.id!!)
    }

    /**
     * Should return 1 available rooms
     */
    @Test
    fun shouldReturnAllAvailableRooms() {
        val users: MutableList<User> = ArrayList()

        val user1 = User()
        val user2 = User()
        val user3 = User()

        users.add(user1)
        users.add(user2)
        users.add(user3)

        userRepository.saveAll(users)

        val room: Room = Room(0L, null, RoomBlock.A, 712, true, 4)
        var id1 = roomService.createRoom(room)

        id1 = roomService.addUsersToRoom(users, id1.id!!)

        assertTrue { roomService.getAllAvailableRooms().size == 1 }

        userRepository.delete(user1)
        userRepository.delete(user2)
        userRepository.delete(user3)

        roomService.deleteRoom(id1.id!!)
    }

    /**
     * Capacity of room should be 3
     */
    @Test
    fun shouldReturnCapacityOfRoom() {
        val users: MutableList<User> = ArrayList()

        val user1 = User()

        users.add(user1)

        userRepository.saveAll(users)

        val room: Room = Room(0L, null, RoomBlock.A, 712, true, 4)
        var id1 = roomService.createRoom(room)

        id1 = roomService.addUsersToRoom(users, id1.id!!)

        assertTrue { roomService.getCapacityOfRoom(id1.id!!) == 3 }

        userRepository.delete(user1)

        roomService.deleteRoom(id1.id!!)
    }

    /**
     * Test should fails. Cannot create room with capacity more than 4
     */
    @Test
    fun shouldNotCreateRoom() {
        val users: MutableList<User> = ArrayList()

        val user1 = User()

        users.add(user1)

        userRepository.saveAll(users)

        val room: Room = Room(0L, null, RoomBlock.A, 712, true, 5)
        assertFails { roomService.createRoom(room) }


        userRepository.delete(user1)

    }
}