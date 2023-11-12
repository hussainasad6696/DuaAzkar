package  com.mera.islam.duaazkar.di

import  com.mera.islam.duaazkar.core.EntityModelMapper
import  com.mera.islam.duaazkar.data.local.entities.DuaAudioEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslatorEntity
import  com.mera.islam.duaazkar.data.local.entities.relationalEntities.DuaWithTranslationRelationalEntity
import  com.mera.islam.duaazkar.data.mappers.DuaAudioEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.DuaEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.DuaTranslationEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.DuaTranslatorEntityToModel
import  com.mera.islam.duaazkar.data.mappers.DuaWithTranslationRelationalEntityToModelMapper
import  com.mera.islam.duaazkar.domain.models.DuaAudioModel
import  com.mera.islam.duaazkar.domain.models.DuaModel
import  com.mera.islam.duaazkar.domain.models.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.models.relationalModels.DuaWithTranslationRelationalModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DuaMapperModule {
    @Binds
    abstract fun duaEntityToModelMapper(duaEntityToModelMapper: DuaEntityToModelMapper): EntityModelMapper<DuaEntity, DuaModel>

    @Binds
    abstract fun duaAudioEntityToModelMapper(duaAudioEntityToModelMapper: DuaAudioEntityToModelMapper): EntityModelMapper<DuaAudioEntity, DuaAudioModel>

    @Binds
    abstract fun duaTranslationEntityToModelMapper(duaTranslationEntityToModelMapper: DuaTranslationEntityToModelMapper): EntityModelMapper<DuaTranslationEntity, DuaTranslationModel>

    @Binds
    abstract fun duaTranslatorEntityToModelMapper(duaTranslatorEntityToModel: DuaTranslatorEntityToModel): EntityModelMapper<DuaTranslatorEntity, DuaTranslatorModel>

    @Binds
    abstract fun duaWithTranslationEntityToModelMapper(
        duaWithTranslationRelationalEntityToModelMapper: DuaWithTranslationRelationalEntityToModelMapper
    ): EntityModelMapper<DuaWithTranslationRelationalEntity, DuaWithTranslationRelationalModel>
}