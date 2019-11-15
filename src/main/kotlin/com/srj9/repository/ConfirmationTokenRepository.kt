package com.srj9.repository

import com.srj9.model.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfirmationTokenRepository: JpaRepository<ConfirmationToken, Long> {
    fun findByConfirmationToken(confirmationToken: String): ConfirmationToken
}