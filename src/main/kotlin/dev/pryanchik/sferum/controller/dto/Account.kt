package dev.pryanchik.sferum.controller.dto

data class Account(
        val books: List<BookItem>,
        val balance: Int
    ) {
        data class BookItem(
            val book: Book,
            val amount: Int,
        ) {
            data class Book(
                val name: String,
                val author: String,
            )
        }
    }