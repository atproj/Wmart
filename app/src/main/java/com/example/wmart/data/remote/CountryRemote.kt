package com.example.wmart.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class CountryRemote(
    val name: String,
    val region: String,
    val code: String,
    val capital: String
)

interface CountryApi {

    @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getCountries(): List<CountryRemote>

}

class CountryRemoteDataSource {

    companion object {
        private const val BASE_URL = "https://gist.githubusercontent.com/"
    }

    private val countryApi: CountryApi

    init {
        val countryRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        countryApi = countryRetrofit.create(CountryApi::class.java)
    }

    suspend fun getCountries(): List<CountryRemote> {
        return countryApi.getCountries()
    }
}