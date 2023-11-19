package  com.mera.islam.duaazkar.di

import  com.mera.islam.duaazkar.core.AppDatabase
import  com.mera.islam.duaazkar.core.EntityModelMapper
import com.mera.islam.duaazkar.data.local.entities.asmaUlHusna.AsmaulHusnaEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslatorEntity
import com.mera.islam.duaazkar.data.mappers.asmaUlHusna.AsmaulHusnaEntityToModelMapper
import com.mera.islam.duaazkar.data.repo.asmaUlHusna.AsmaulHusnaRepoImpl
import  com.mera.islam.duaazkar.data.repo.dua.DuaRepoImpl
import  com.mera.islam.duaazkar.data.repo.dua.DuaTranslatorRepoImpl
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel
import com.mera.islam.duaazkar.domain.repo.asmaUlHusna.AsmaulHusnaRepo
import  com.mera.islam.duaazkar.domain.repo.dua.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.dua.DuaTranslatorRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DuaRepoModule {

    @Provides
    @ViewModelScoped
    fun duaRepo(
        appDatabase: AppDatabase,
        duaEntityToModelMapper: EntityModelMapper<DuaEntity, DuaModel>
    ): DuaRepo = DuaRepoImpl(
        duaDao = appDatabase.duaDao(),
        duaEntityToModelMapper = duaEntityToModelMapper
    )

    @Provides
    @ViewModelScoped
    fun duaTranslatorRepo(
        appDatabase: AppDatabase,
        duaTranslatorEntityToModelMapper: EntityModelMapper<DuaTranslatorEntity, DuaTranslatorModel>
    ): DuaTranslatorRepo = DuaTranslatorRepoImpl(
        duaTranslatorDao = appDatabase.duaTranslatorDao(),
        duaTranslatorMapper = duaTranslatorEntityToModelMapper
    )

    @Provides
    @ViewModelScoped
    fun asmaulHusna(
        appDatabase: AppDatabase,
        asmaulHusnaEntityToModelMapper: EntityModelMapper<AsmaulHusnaEntity, AsmaulHusnaModel>
    ): AsmaulHusnaRepo = AsmaulHusnaRepoImpl(
        asmaulHusnaDao = appDatabase.asmaulHusnaDao(),
        asmaulHusnaEntityToModelMapper = asmaulHusnaEntityToModelMapper
    )
}