package com.mera.islam.duaazkar.data.local.entities.asmaUlHusna

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asma_ul_husna")
data class AsmaulHusnaEntity(
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("transliteration")
    val transliteration: String,
    @ColumnInfo("found")
    val found: String,
    @ColumnInfo("en_meaning")
    val enMeaning: String,
    @ColumnInfo("en_desc")
    val enDesc: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}