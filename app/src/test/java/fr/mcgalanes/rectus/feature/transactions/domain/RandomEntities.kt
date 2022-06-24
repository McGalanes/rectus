package fr.mcgalanes.rectus.feature.transactions.domain

import fr.mcgalanes.rectus.core.common.nextString
import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionEntity.AmountEntity
import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionEntity.AmountEntity.CurrencyEntity
import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionEntity.IconEntity
import kotlin.random.Random

fun Random.nextTransactionEntityList(size: Int): List<TransactionEntity> =
    mutableListOf<TransactionEntity>().apply {
        repeat(times = size) { add(nextTransactionEntity()) }
    }

fun Random.nextTransactionEntity(): TransactionEntity =
    TransactionEntity(
        name = nextString(),
        type = nextString(),
        date = "2021-03-07T14:04:45.000+01:00",
        message = nextString(),
        amount = nextAmountEntity(),
        smallIcon = nextIconEntity(),
        largeIcon = nextIconEntity(),
    )

fun Random.nextAmountEntity(): AmountEntity =
    AmountEntity(
        value = nextDouble(),
        currency = nextCurrencyEntity(),
    )

fun Random.nextCurrencyEntity(): CurrencyEntity =
    CurrencyEntity(
        iso = nextString(),
        symbol = nextString(),
        title = nextString(),
    )

fun Random.nextIconEntity(): IconEntity =
    IconEntity(
        url = nextString(),
        category = nextString(),
    )