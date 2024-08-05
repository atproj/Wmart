package com.example.wmart

import com.example.wmart.data.CountryRepository
import com.example.wmart.data.local.CountryLocal
import com.example.wmart.data.local.CountryLocalDataSource
import com.example.wmart.data.remote.CountryRemote
import com.example.wmart.data.remote.CountryRemoteDataSource
import com.example.wmart.domain.Country
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryRepositoryTest {
    private val remote = mockk<CountryRemoteDataSource>()
    private val local = mockk<CountryLocalDataSource>()
    private lateinit var repo: CountryRepository

    @Before
    fun setup() {
        repo = CountryRepository(remote, local, UnconfinedTestDispatcher())
    }

    @Test
    fun `repo fetches, persists, and returns a transformation`() = runTest {
        val remoteCountries = listOf(
            CountryRemote("", "", "", "")
        )
        val localCountries = listOf(
            CountryLocal(0, "", "", "", "")
        )
        val domainCountries = listOf(
            Country("", "", "", "")
        )
        coEvery { remote.getCountries() } returns remoteCountries
        coEvery { local.insertCountries(localCountries) } just Runs
        coEvery { local.getCountries() } returns localCountries

        val countries = repo.getCountries()

        coVerify { remote.getCountries() }
        coVerify { local.insertCountries(localCountries) }
        coVerify { local.getCountries() }
        Assert.assertEquals(domainCountries, countries)
    }

    @Test
    fun `repo encounters an exception and returns the local value`() = runTest {
        coEvery { remote.getCountries() } throws Exception("")
        val localCountries = listOf(
            CountryLocal(0, "", "", "", "")
        )
        coEvery { local.getCountries() } returns localCountries
        val domainCountries = listOf(
            Country("", "", "", "")
        )

        val countries = repo.getCountries()

        coVerify { remote.getCountries() }
        coVerify { local.getCountries() }
        Assert.assertEquals(domainCountries, countries)
    }

    @Test(expected = Exception::class)
    fun `repo encounters an exception and throws an exception`() = runTest {
        coEvery { remote.getCountries() } throws Exception("")
        coEvery { local.getCountries() } returns listOf()

        repo.getCountries()

        coVerify { remote.getCountries() }
        coVerify { local.getCountries() }
    }
}