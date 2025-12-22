package com.example.testreactions.data

import androidx.room.Entity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val playerName: String?,
    val timeMs: Long?
)

@Dao
interface PlayerDao {
    @Query("""
        SELECT * FROM players 
        ORDER BY timeMs ASC 
        LIMIT 10
    """)
    suspend fun getTop10(): List<PlayerEntity>

    // прописать про конкретного пользователя

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: PlayerEntity)


    @Query("DELETE FROM players")
    suspend fun deleteAll()
}