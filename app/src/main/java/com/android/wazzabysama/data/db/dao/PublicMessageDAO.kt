package com.android.wazzabysama.data.db.dao

import androidx.room.*
import com.android.wazzabysama.data.model.dataRoom.PublicMessageRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface PublicMessageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(publicMessage: PublicMessageRoom)

    @Query("SELECT * FROM public_message_data_table WHERE problematic = :publicMessageProblematic")
    fun getAllPublicMessages(publicMessageProblematic: String): Flow<List<PublicMessageRoom>>

    @Delete()
    suspend fun deletePublicMessage(publicMessage: PublicMessageRoom)

    @Query("DELETE  FROM public_message_data_table")
    suspend fun deleteTablePublicMessage();
}