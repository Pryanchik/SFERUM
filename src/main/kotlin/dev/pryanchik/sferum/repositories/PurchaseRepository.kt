package dev.pryanchik.sferum.repositories

import dev.pryanchik.sferum.entities.Account
import dev.pryanchik.sferum.entities.Purchase
import org.springframework.data.jpa.repository.JpaRepository

interface PurchaseRepository : JpaRepository<Purchase, Long> {
    fun findAllByAccount(account: Account): List<Purchase>
}