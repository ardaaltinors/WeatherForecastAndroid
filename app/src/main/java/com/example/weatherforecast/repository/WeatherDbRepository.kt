package com.example.weatherforecast.repository

import com.example.weatherforecast.data.WeatherDao
import com.example.weatherforecast.model.City
import com.example.weatherforecast.model.Favorite
import com.example.weatherforecast.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// https://prnt.sc/6-qSGjL8ktfm
class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    // Favorites tbl
    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()

    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)

    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)

    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()

    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)

    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)

    // Settings tbl
    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)




}