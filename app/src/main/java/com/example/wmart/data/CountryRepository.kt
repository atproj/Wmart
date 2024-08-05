package com.example.wmart.data

import com.example.wmart.data.local.CountryLocalDataSource
import com.example.wmart.data.remote.CountryRemoteDataSource
import com.example.wmart.domain.Country
import com.example.wmart.domain.toDomain
import com.example.wmart.domain.toLocal
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


/**
 * Gateway to the model layer.  Using the database as the source of truth.
 *
 * @author  Alex Tam
 */
class CountryRepository(
    private val remote: CountryRemoteDataSource,
    private val local: CountryLocalDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun getCountries(): List<Country> {
        return withContext(dispatcher) {
            try {
                val fromRemote = remote.getCountries().map { it.toDomain() }
                local.insertCountries(fromRemote.map { it.toLocal() })
                local.getCountries().map { it.toDomain() }
            } catch (ex: Exception) {
                val fromLocal = local.getCountries().map { it.toDomain() }
                if (fromLocal.isEmpty()) {
                    throw Exception("error and nothing in db!")
                }
                fromLocal
            }
        }
    }
}