package com.example.testreactions

object RecordsRepository {

    private val results = mutableListOf<RecordItem>()

    // Добавить новый результат
    fun addResult(playerName: String, timeMs: Long) {
        results.add(RecordItem(playerName, timeMs))
        results.sortBy { it.timeMs }

        if (results.size > 10) {
            results.removeAt(results.lastIndex)

        }
    }

    fun getTopResults(): List<RecordItem> = results
}