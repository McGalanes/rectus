package fr.mcgalanes.rectus.feature.transactions.domain

import fr.mcgalanes.rectus.core.common.nextString
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import java.time.LocalDate
import kotlin.random.Random

fun Random.nextTransactionList(size: Int): List<Transaction> =
    mutableListOf<Transaction>().apply {
        repeat(times = size) { add(nextTransaction()) }
    }

fun Random.nextTransaction(): Transaction =
    Transaction(
        thumbUrl = nextString(),
        title = nextString(),
        date = LocalDate.now(),
        priceInDecimal = nextDouble(),
    )