package fr.mcgalanes.rectus.feature.transactions.domain.mapper

import fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import java.time.ZonedDateTime

internal fun TransactionEntity.toModel(): Transaction =
    Transaction(
        thumbUrl = largeIcon?.url,
        title = requireNotNull(name),
        date = ZonedDateTime.parse(requireNotNull(date)).toLocalDate(),
        priceInDecimal = requireNotNull(amount?.value),
    )