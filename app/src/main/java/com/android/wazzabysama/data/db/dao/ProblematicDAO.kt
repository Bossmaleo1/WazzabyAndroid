package com.android.wazzabysama.data.db.dao

import androidx.room.*
import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface ProblematicDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(problematic: ProblematicRoom)

    @Query("SELECT * FROM problematic_data_table")
    fun getAllProblematics(): Flow<List<ProblematicRoom>>

    @Query(""" SELECT * FROM problematic_data_table
        INNER JOIN user_data_table on problematic_data_table.problematic_id = user_data_table.user_problematic_id
        WHERE user_data_table.user_id = :userId
    """)
    fun getProblematicForUser(userId: Int) : Flow<ProblematicRoom>

    @Delete()
    suspend fun deleteProblematic(problematic: ProblematicRoom)
}