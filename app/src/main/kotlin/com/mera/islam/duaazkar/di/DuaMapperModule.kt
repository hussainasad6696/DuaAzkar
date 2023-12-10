package  com.mera.islam.duaazkar.di

import  com.mera.islam.duaazkar.core.EntityModelMapper
import com.mera.islam.duaazkar.data.local.entities.TasbihEntity
import com.mera.islam.duaazkar.data.local.entities.asmaUlHusna.AsmaulHusnaEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaAudioEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslatorEntity
import  com.mera.islam.duaazkar.data.local.entities.dua.relationalEntities.DuaWithTranslationRelationalEntity
import com.mera.islam.duaazkar.data.mappers.TasbihEntityToModelMapper
import com.mera.islam.duaazkar.data.mappers.asmaUlHusna.AsmaulHusnaEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.dua.DuaAudioEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.dua.DuaEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.dua.DuaTranslationEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.dua.DuaTranslatorEntityToModelMapper
import  com.mera.islam.duaazkar.data.mappers.dua.DuaWithTranslationRelationalEntityToModelMapper
import com.mera.islam.duaazkar.domain.models.TasbihModel
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaAudioModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel
import  com.mera.islam.duaazkar.domain.models.dua.relationalModels.DuaWithTranslationRelationalModel
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
    abstract fun duaTranslatorEntityToModelMapper(duaTranslatorEntityToModelMapper: DuaTranslatorEntityToModelMapper): EntityModelMapper<DuaTranslatorEntity, DuaTranslatorModel>

    @Binds
    abstract fun tasbihEntityToModelMapper(tasbihEntityToModelMapper: TasbihEntityToModelMapper): EntityModelMapper<TasbihEntity,TasbihModel>

    @Binds
    abstract fun duaWithTranslationEntityToModelMapper(
        duaWithTranslationRelationalEntityToModelMapper: DuaWithTranslationRelationalEntityToModelMapper
    ): EntityModelMapper<DuaWithTranslationRelationalEntity, DuaWithTranslationRelationalModel>

    @Binds
    abstract fun asmaulHusnaEntityToModelMapper(
        asmaulHusnaEntityToModelMapper: AsmaulHusnaEntityToModelMapper
    ): EntityModelMapper<AsmaulHusnaEntity, AsmaulHusnaModel>
}