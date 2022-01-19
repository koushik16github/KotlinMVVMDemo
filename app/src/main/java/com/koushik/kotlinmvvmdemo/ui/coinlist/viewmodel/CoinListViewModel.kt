package com.koushik.kotlinmvvmdemo.ui.coinlist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.koushik.kotlinmvvmdemo.data.model.Coin
import com.koushik.kotlinmvvmdemo.data.repository.Repository
import com.koushik.kotlinmvvmdemo.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor
    (
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _response: MutableLiveData<Resource<List<Coin>>> = MutableLiveData()
    val response: LiveData<Resource<List<Coin>>> = _response

    fun getCoins() = viewModelScope.launch {
        repository.getCoins().collect { values ->
            _response.value = values
        }
    }
}