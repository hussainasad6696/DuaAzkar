package  com.mera.islam.duaazkar.domain.models.dua.relationalModels

import  com.mera.islam.duaazkar.domain.models.dua.DuaModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.dua.DuaTranslatorModel

data class DuaWithTranslationRelationalModel(
    val duaModel: DuaModel,
    val duaTranslationModel: DuaTranslationModel,
    val duaTranslatorModel: DuaTranslatorModel
)