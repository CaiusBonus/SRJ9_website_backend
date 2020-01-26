package com.srj9.model

import com.srj9.enums.RoomBlock
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
@Table(name = "ROOM", uniqueConstraints = [
        UniqueConstraint(columnNames = ["block", "room_number"])
])
class Room {

        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        var id: Long? = null

        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        @JoinColumn(name = "room_id", nullable = true)
        @OnDelete(action = OnDeleteAction.CASCADE)
        var users: MutableList<User>? = mutableListOf()

        @Column(name = "block")
        @Enumerated(EnumType.STRING)
        var block: RoomBlock? = RoomBlock.A

        @Column(name = "room_number")
        var roomNumber: Int? = 100

        @Column(name = "is_available", columnDefinition = "true")
        var isAvailable: Boolean? = true

        @Column(name = "available_capacity", columnDefinition = "4")
        var capacity: Int? = 4
}