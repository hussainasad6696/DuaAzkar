package com.mera.islam.duaazkar.data.repo.asmaUlHusna

import com.mera.islam.duaazkar.core.EntityModelMapper
import com.mera.islam.duaazkar.data.local.dao.asmaUlHusna.AsmaulHusnaDao
import com.mera.islam.duaazkar.data.local.entities.asmaUlHusna.AsmaulHusnaEntity
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import com.mera.islam.duaazkar.domain.repo.asmaUlHusna.AsmaulHusnaRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AsmaulHusnaRepoImpl(
    private val asmaulHusnaDao: AsmaulHusnaDao,
    private val asmaulHusnaEntityToModelMapper: EntityModelMapper<AsmaulHusnaEntity, AsmaulHusnaModel>
) : AsmaulHusnaRepo {
    override fun getAllAsmaulHusna(): Flow<List<AsmaulHusnaModel>> {
        return asmaulHusnaDao.getAllAsmaulHusna()
            .map { it.map { data -> asmaulHusnaEntityToModelMapper.entityToModelMapper(data) } }
    }
}