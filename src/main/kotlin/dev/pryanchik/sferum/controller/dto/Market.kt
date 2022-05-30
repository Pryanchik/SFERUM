package dev.pryanchik.sferum.controller.dto

data class Market(
    val products: List<Product>
) {
    data class Product(
        val id: Long,
        val book: Book,
        val price: Int,
        val amount: Int,
    ) {
        data class Book(
            val name: String,
            val author: String,
        )
    }
}
