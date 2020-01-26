package com.srj9.dto

import com.srj9.enums.RoomBlock
import com.srj9.model.User

data class RoomDto (
        val id: Long,
        val userId: List<User>,
        val block: RoomBlock,
        val isAvailable: Boolean,
        val capacity: Int
)