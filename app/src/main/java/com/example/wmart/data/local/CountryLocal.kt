package com.example.wmart.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

@Entity(tableName = "country")
data class CountryLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val region: String,
    val code: String,
    val capital: String
)

@Dao
interface CountryDao {
    @Query("SELECT * FROM Country")
    suspend fun getCountries(): List<CountryLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg countries: CountryLocal)
}

@Database(entities = [CountryLocal::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
}

class CountryLocalDataSource(appContext: Context) {
    private val countryDao: CountryDao

    init {
        val db = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
        countryDao = db.countryDao()
    }

    suspend fun getCountries(): List<CountryLocal> {
        return countryDao.getCountries()
    }

    suspend fun insertCountries(countries: List<CountryLocal>) {
        countryDao.insertAll(*countries.toTypedArray())
    }
}