package dev.pryanchik.sferum.controller

import dev.pryanchik.sferum.controller.dto.Account
import dev.pryanchik.sferum.controller.dto.DealParams
import dev.pryanchik.sferum.controller.dto.Market
import dev.pryanchik.sferum.entities.Purchase
import dev.pryanchik.sferum.repositories.AccountRepository
import dev.pryanchik.sferum.repositories.BookRepository
import dev.pryanchik.sferum.repositories.PurchaseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.transaction.Transactional

@RestController
internal class HtmlController(
    private val bookRepository: BookRepository,
    private val accountRepository: AccountRepository,
    private val purchaseRepository: PurchaseRepository,

) {
    @GetMapping("/market")
    fun getMarket(): Market {
        val books = bookRepository.findAllByAmountGreaterThan(0)
        return Market(
            products = books.map { book ->
                Market.Product(
                    id = book.id,
                    book = Market.Product.Book(
                        name = book.name,
                        author = book.author
                    ),
                    price = book.price,
                    amount = book.amount
                )
            }
        )
    }
    @GetMapping("/account")
    fun getAccount(): Account {
        val account = accountRepository.getReferenceById(0)
        val purchases = purchaseRepository.findAllByAccount(account).groupBy { it.book }
        return Account(
            books = purchases.map { (book, purchases) ->
                Account.BookItem(
                    book = Account.BookItem.Book(
                        name = book.name,
                        author = book.author
                    ),
                    amount = purchases.sumOf { it.amount }
                )
            },
            balance = account.money
        )
    }
    @Transactional
    @PostMapping("/market/deal")
    fun postDeal(@RequestBody deal: DealParams): ResponseEntity<Any?> {
        val book = bookRepository.findByIdOrNull(deal.id)
            ?: return ResponseEntity.badRequest().build()
        if(book.amount < deal.amount)
            return ResponseEntity.badRequest().build()
        val account = accountRepository.getReferenceById(0)
        val totalCost =  deal.amount * book.price
        if(account.money < totalCost)
            return ResponseEntity.badRequest().build()
        account.money -= totalCost
        book.amount -= deal.amount
        purchaseRepository.save(
            Purchase(
                account = account,
                book = book,
                amount = deal.amount,
            )
        )
        return ResponseEntity.ok().build()
    }
}