package dev.pryanchik.sferum.entities

import javax.persistence.*

@Entity
class Purchase(
    @ManyToOne
    var account: Account,
    @ManyToOne
    var book: Book,
    var amount: Int,
    @Id
    @GeneratedValue
    var id: Long = 0,
)