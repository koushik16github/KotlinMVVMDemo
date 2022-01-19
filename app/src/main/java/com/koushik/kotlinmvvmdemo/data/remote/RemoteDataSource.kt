package com.koushik.kotlinmvvmdemo.data.remote

import com.koushik.kotlinmvvmdemo.data.remote.dto.CoinDetailDto
import com.koushik.kotlinmvvmdemo.data.remote.dto.CoinDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val api: CoinPaprikaApi
) {

    suspend fun getCoins(): List<CoinDto> {
        return api.getCoins()
    }

    suspend fun getCoinById(coinId: String): CoinDetailDto {
        return api.getCoinById(coinId)
    }
}