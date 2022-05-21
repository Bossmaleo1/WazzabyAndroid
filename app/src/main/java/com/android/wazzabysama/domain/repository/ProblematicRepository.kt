package com.android.wazzabysama.domain.repository

import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProblematicRepository {
    fun getSavedProblematic(userId: Int):Flow<ProblematicRoom>
    suspend fun saveProblematic(problematic: ProblematicRoom)
}