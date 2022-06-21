package fr.mcgalanes.rectus.core.data.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.mcgalanes.rectus.core.data.adapter.ResultCallAdapterFactory
import fr.mcgalanes.rectus.feature.transactions.data.TransactionApiService
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object CoreDataModule {

    @Provides
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService =
        retrofit.create(TransactionApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .build()
}