package com.android.wazzabysama.domain.usecase.problematic

import com.android.wazzabysama.domain.repository.ProblematicRepository


class GetSavedProblematicUseCase(private val problematicRepository: ProblematicRepository) {
    suspend fun execute(userId: Int) = problematicRepository.getSavedProblematic(userId)
}