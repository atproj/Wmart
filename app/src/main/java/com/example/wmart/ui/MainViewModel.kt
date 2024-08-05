package com.example.wmart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wmart.data.CountryRepository
import kotlinx.coroutines.launch

/**
 * Wraps the state to a UI state for observation.
 * A UseCase was not used as it would be boilerplate in this small example
 *
 * @author  Alex Tam
 */
class MainViewModel(private val repo: CountryRepository): ViewModel() {

    private val _countries = MutableLiveData<ResultState<List<CountryUI>>>()
    val countries: LiveData<ResultState<List<CountryUI>>> = _countries

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        _countries.value = ResultState.Loading
        viewModelScope.launch {
            try {
                val countries = repo.getCountries()
                _countries.value = if (countries.isEmpty()) ResultState.Success.Empty
                else ResultState.Success.NonEmpty(countries.map { it.toUI() })
            } catch (ex: Exception) {
                _countries.value = ResultState.Failure(ex)
            }
        }
    }
}