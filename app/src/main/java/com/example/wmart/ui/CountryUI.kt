package com.example.wmart.ui

import com.example.wmart.domain.Country

data class CountryUI(val displayName: String, val code: String, val capital: String)

fun Country.toUI() = CountryUI(
    displayName = if (this.name.isNotBlank() && this.region.isNotBlank())
        "${this.name}, ${this.region}" else if (this.name.isNotBlank()) this.name else this.region,
    code = this.code,
    capital = this.capital
)