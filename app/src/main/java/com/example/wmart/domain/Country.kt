package com.example.wmart.domain

import com.example.wmart.data.local.CountryLocal
import com.example.wmart.data.remote.CountryRemote

/**
 * A com.example.wmart.domain model that can be used to transform attributes to be used by business logic.
 * For example, region and code could be enums to enforce type safety, though that is omitted
 * for the sake of this exercise.
 */
data class Country(
    val name: String,
    val region: String,
    val code: String,
    val capital: String
)

fun CountryRemote.toDomain() = Country(name, region, code, capital)

fun Country.toLocal() = CountryLocal(
    name = name,
    region = region,
    code = code,
    capital = capital
)

fun CountryLocal.toDomain() = Country(name, region, code, capital)