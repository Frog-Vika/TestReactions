package com.example.testreactions.data

import android.content.Context
import com.example.testreactions.database.AppDatabase

class RecordsRepository(context: Context) {

    private val results = AppDatabase.Companion.getInstance(context).recordsDao()

    // Добавить новый результат
//    fun addResult(playerName: String?, timeMs: Long?) {
//        results.add(PlayerEntity(playerName, timeMs))
//        results.sortBy { it.timeMs }
//
//        if (results.size > 10) {
//            results.removeAt(results.lastIndex)
//
//        }
//    }

    suspend fun addResult(playerName: String, timeMs: Long) {
        results.insert(PlayerEntity(playerName = playerName, timeMs = timeMs))
    }

    suspend fun getTop10(): List<PlayerEntity> {
        return results.getTop10()
    }

    companion object

}