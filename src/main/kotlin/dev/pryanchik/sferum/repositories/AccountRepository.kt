package dev.pryanchik.sferum.repositories

import dev.pryanchik.sferum.entities.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long>