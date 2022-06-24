package fr.mcgalanes.rectus.feature.transactions.data.injection

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import fr.mcgalanes.rectus.feature.transactions.data.repository.TransactionRepositoryImpl
import fr.mcgalanes.rectus.feature.transactions.domain.repository.TransactionsRepository

@InstallIn(ViewModelComponent::class)
@Module
interface TransactionRepositoryModule {

    @Binds
    fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionsRepository
}