package com.android.wazzabysama.domain.usecase.problematic

import com.android.wazzabysama.data.model.dataRoom.ProblematicRoom
import com.android.wazzabysama.domain.repository.ProblematicRepository

class SaveProblematicUseCase(private val problematicRepository: ProblematicRepository) {
    suspend fun execute(problematic: ProblematicRoom) = problematicRepository.saveProblematic(problematic)
}