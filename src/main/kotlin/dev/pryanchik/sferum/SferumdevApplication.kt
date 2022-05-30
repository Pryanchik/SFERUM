package dev.pryanchik.sferum

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dev.pryanchik.sferum.entities.Account
import dev.pryanchik.sferum.entities.Book
import dev.pryanchik.sferum.repositories.AccountRepository
import dev.pryanchik.sferum.repositories.BookRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.File

@SpringBootApplication
class SferumdevApplication(
    private val bookRepository: BookRepository,
    private val accountRepository: AccountRepository,
): ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        val data = jacksonObjectMapper().readValue(File(args.sourceArgs[0]).readText(), Data::class.java)
        val bookEntities = data.books.map { book ->
            bookRepository.save(Book(
                author = book.author,
                name = book.name,
                price = book.price,
                amount = book.amount
            ))
        }
        bookRepository.saveAll(bookEntities)
        accountRepository.save(Account(
            money = data.account.money
        ))
    }

}

fun main(args: Array<String>) {
    runApplication<SferumdevApplication>(*args)
}
data class Data(
    val account: Account,
    var books: List<Book>,
) {
    data class Account(
        var money: Int,
    )
    data class Book(
        val author: String,
        val name: String,
        val price: Int,
        var amount: Int,
    )
}
