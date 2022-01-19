package com.koushik.kotlinmvvmdemo.ui.coindetail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.koushik.kotlinmvvmdemo.data.model.Coin
import com.koushik.kotlinmvvmdemo.data.model.CoinDetail
import com.koushik.kotlinmvvmdemo.data.repository.Repository
import com.koushik.kotlinmvvmdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<Resource<CoinDetail>> = MutableLiveData()
    val response: LiveData<Resource<CoinDetail>> = _response

    fun getCoinById(coinId: String) = viewModelScope.launch {
        repository.getCoinById(coinId).collect { values ->
            _response.value = values
        }
    }
}