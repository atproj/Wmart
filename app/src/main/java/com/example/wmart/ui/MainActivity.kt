package com.example.wmart.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wmart.R

class MainActivity : AppCompatActivity() {

    private val vm by viewModels<MainViewModel> { ViewModelFactory(applicationContext) }
    private lateinit var rv: RecyclerView
    private lateinit var adapter: CountryAdapter
    private lateinit var pb: ProgressBar
    private lateinit var retry: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.addItemDecoration(
            DividerItemDecoration(this, RecyclerView.VERTICAL)
        )
        adapter = CountryAdapter(listOf())
        rv.adapter = adapter
        pb = findViewById(R.id.pb)
        retry = findViewById(R.id.retry_container)

        vm.countries.observe(this) { uiState ->
            when (uiState) {
                is ResultState.Loading -> {
                    showLoading()
                }
                is ResultState.Success.Empty -> {
                    showEmpty()
                }

                is ResultState.Success.NonEmpty -> {
                    showNonEmpty(uiState.value)
                }

                is ResultState.Failure -> {
                    showFailure()
                }
            }
        }
    }

    private fun showLoading() {
        Log.d("TRACE", "showLoading")
        rv.visibility = View.GONE
        retry.visibility = View.GONE
        pb.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        pb.visibility = View.GONE
        rv.visibility = View.GONE
        showRetry(
            getString(R.string.empty_msg)
        )
    }

    private fun showNonEmpty(countries: List<CountryUI>) {
        Log.d("TRACE", "show nonEmpty")
        pb.visibility = View.GONE
        retry.visibility = View.GONE
        rv.visibility = View.VISIBLE
        adapter.countries = countries
        adapter.notifyDataSetChanged()
    }

    private fun showFailure() {
        Log.d("TRACE", "showFailure")
        pb.visibility = View.GONE
        rv.visibility = View.GONE
        showRetry(
            getString(R.string.error_msg)
        )
    }

    private fun showRetry(msg: String) {
        retry.apply {
            visibility = View.VISIBLE
            findViewById<TextView>(R.id.msgTV).text = msg
            findViewById<Button>(R.id.retryBtn).setOnClickListener {
                vm.fetchCountries()
            }
        }
    }
}