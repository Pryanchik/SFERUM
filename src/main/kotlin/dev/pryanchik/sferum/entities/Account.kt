package dev.pryanchik.sferum.entities

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Account(
    var money: Int,
    @Id
    var id: Long = 0
)