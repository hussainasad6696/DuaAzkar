package com.mera.islam.duaazkar.domain.repo.asmaUlHusna

import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import kotlinx.coroutines.flow.Flow

interface AsmaulHusnaRepo {
    fun getAllAsmaulHusna(): Flow<List<AsmaulHusnaModel>>
}