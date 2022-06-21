package fr.mcgalanes.rectus.feature.transactions.data

import fr.mcgalanes.rectus.feature.transactions.data.entity.TransactionsResponse
import retrofit2.http.GET

interface TransactionApiService {

    @GET("gist.githubusercontent.com/Aurazion/365d587f5917d1478bf03bacabdc69f3/raw/3c92b70e1dc808c8be822698f1cbff6c95ba3ad3/transactions.json")
    suspend fun getTransactions(): Result<TransactionsResponse>
}