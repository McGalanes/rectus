package fr.mcgalanes.rectus.feature.transactions.data.datasource.remote.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionEntity(
    @Json(name = "name") val name: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "date") val date: String?,
    @Json(name = "message") val message: String?,
    @Json(name = "amount") val amount: AmountEntity?,
    @Json(name = "small_icon") val smallIcon: IconEntity?,
    @Json(name = "large_icon") val largeIcon: IconEntity?,
) {
    @JsonClass(generateAdapter = true)
    data class AmountEntity(
        @Json(name = "value") val value: Double?,
        @Json(name = "currency") val currency: CurrencyEntity?,
    ) {
        @JsonClass(generateAdapter = true)
        data class CurrencyEntity(
            @Json(name = "iso_3") val iso: String?,
            @Json(name = "symbol") val symbol: String?,
            @Json(name = "title") val title: String?,
        )
    }

    @JsonClass(generateAdapter = true)
    data class IconEntity(
        @Json(name = "url") val url: String?,
        @Json(name = "category") val category: String?,
    )
}
