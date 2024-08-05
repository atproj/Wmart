package com.example.wmart.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wmart.data.CountryRepository
import com.example.wmart.data.local.CountryLocalDataSource
import com.example.wmart.data.remote.CountryRemoteDataSource


class ViewModelFactory(private val appContext: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                CountryRepository(
                    CountryRemoteDataSource(),
                    CountryLocalDataSource(appContext)
                )
            ) as T
        }
        throw IllegalArgumentException("$modelClass is an unrecognized viewmodel type!")
    }
}