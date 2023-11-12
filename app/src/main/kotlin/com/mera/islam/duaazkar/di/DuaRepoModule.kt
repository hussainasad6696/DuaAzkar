package  com.mera.islam.duaazkar.di

import  com.mera.islam.duaazkar.core.AppDatabase
import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslatorEntity
import  com.mera.islam.duaazkar.data.repo.DuaRepoImpl
import  com.mera.islam.duaazkar.data.repo.DuaTranslatorRepoImpl
import  com.mera.islam.duaazkar.domain.models.DuaModel
import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.repo.DuaRepo
import  com.mera.islam.duaazkar.domain.repo.DuaTranslatorRepo
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
}