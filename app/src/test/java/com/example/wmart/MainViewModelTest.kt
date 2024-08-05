package com.example.wmart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.wmart.data.CountryRepository
import com.example.wmart.domain.Country
import com.example.wmart.ui.CountryUI
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.wmart.ui.MainViewModel
import com.example.wmart.ui.ResultState
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.junit.Assert

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val repo = mockk<CountryRepository>()
    private lateinit var vm: MainViewModel

    @get:Rule
    val mainRule = MainCoroutineRule()
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        vm = MainViewModel(repo)
    }

    @Test
    fun `fetch countries results in nonempty success state`() = runTest {
        val list = listOf(Country("", "", "", ""))
        val uiList = listOf(CountryUI("", "", ""))
        coEvery { repo.getCountries() } returns list
        val success = ResultState.Success.NonEmpty(uiList)

        vm.fetchCountries()

        coVerify { repo.getCountries() }
        Assert.assertEquals(success, vm.countries.value)
    }

    @Test
    fun `fetch countries results in empty success state`() = runTest {
        coEvery { repo.getCountries() } returns emptyList()
        val success = ResultState.Success.Empty

        vm.fetchCountries()

        coVerify { repo.getCountries() }
        Assert.assertEquals(success, vm.countries.value)
    }

    @Test
    fun `fail to fetch results in failure state`() = runTest {
        val ex = mockk<Exception>()
        coEvery { repo.getCountries() } throws ex
        val failure = ResultState.Failure(ex)

        vm.fetchCountries()

        coVerify { repo.getCountries() }
        Assert.assertEquals(failure, vm.countries.value)
    }

}