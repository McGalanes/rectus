package fr.mcgalanes.rectus.feature.transactions.domain.mapper

import fr.mcgalanes.rectus.feature.transactions.domain.entity.TransactionEntity
import fr.mcgalanes.rectus.feature.transactions.domain.model.Transaction
import java.time.ZonedDateTime

internal fun TransactionEntity.toModel(): Transaction =
    Transaction(
        thumbUrl = requireNotNull(largeIcon?.url),
        title = requireNotNull(name),
        date = ZonedDateTime.parse(requireNotNull(date)).toLocalDate(),
        priceInDecimal = requireNotNull(amount?.value),
    )