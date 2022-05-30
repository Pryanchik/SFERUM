package dev.pryanchik.sferum.repositories

import dev.pryanchik.sferum.entities.Book
import org.springframework.data.jpa.repository.JpaRepository


interface BookRepository : JpaRepository<Book, Long> {
    fun findAllByAmountGreaterThan(amount: Int): List<Book>
}