package com.koushik.kotlinmvvmdemo.data.repository

import com.koushik.kotlinmvvmdemo.data.model.Coin
import com.koushik.kotlinmvvmdemo.data.model.CoinDetail
import com.koushik.kotlinmvvmdemo.data.remote.RemoteDataSource
import com.koushik.kotlinmvvmdemo.data.remote.dto.toCoin
import com.koushik.kotlinmvvmdemo.data.remote.dto.toCoinDetail
import com.koushik.kotlinmvvmdemo.utils.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiRepository() {

    suspend fun getCoins(): Flow<Resource<List<Coin>>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getCoins().map { it.toCoin() } })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getCoinById(coinId: String): Flow<Resource<CoinDetail>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getCoinById(coinId).toCoinDetail() })
        }.flowOn(Dispatchers.IO)
    }
}