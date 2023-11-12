package  com.mera.islam.duaazkar.domain.models.relationalModels

import  com.mera.islam.duaazkar.domain.models.DuaModel
import  com.mera.islam.duaazkar.domain.models.DuaTranslationModel
import  com.mera.islam.duaazkar.domain.models.DuaTranslatorModel

data class DuaWithTranslationRelationalModel(
    val duaModel: DuaModel,
    val duaTranslationModel: DuaTranslationModel,
    val duaTranslatorModel: DuaTranslatorModel
)