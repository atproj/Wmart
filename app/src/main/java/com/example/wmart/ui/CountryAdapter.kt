package com.example.wmart.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wmart.R

class CountryAdapter(var countries: List<CountryUI>): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    inner class CountryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val nameTV: TextView = view.findViewById(R.id.nameTV)
        private val codeTV: TextView = view.findViewById(R.id.codeTV)
        private val capitalTV: TextView = view.findViewById(R.id.capitalTV)

        fun bind(country: CountryUI) {
            nameTV.text = country.displayName
            codeTV.text = country.code
            capitalTV.text = country.capital
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries[position])
    }
}