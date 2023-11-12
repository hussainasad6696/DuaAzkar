package  com.mera.islam.duaazkar.data.local.entities.relationalEntities

import androidx.room.Embedded
import androidx.room.Relation
import  com.mera.islam.duaazkar.data.local.entities.DuaEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslationEntity
import  com.mera.islam.duaazkar.data.local.entities.DuaTranslatorEntity

data class DuaWithTranslationRelationalEntity(
    @Embedded val duaTranslationEntity: DuaTranslationEntity,

    @Relation(
        parentColumn = "dua_id",
        entityColumn = "id"
    ) val duaEntity: DuaEntity,

    @Relation(
        parentColumn = "translator_id",
        entityColumn = "id"
    )
    val duaTranslatorEntity: DuaTranslatorEntity
)
