package dev.pryanchik.sferum.entities

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id


@Entity
data class Book(
    val author: String,
    val name: String,
    val price: Int,
    var amount: Int,
    @Id
    @GeneratedValue
    var id: Long = 0,
)