package com.mera.islam.duaazkar.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mera.islam.duaazkar.core.enums.TasbihType

@Entity("tasbih")
data class TasbihEntity(
    @ColumnInfo("counted")
    val count: Int,
    @ColumnInfo("total_count")
    val totalCount: Int,
    @ColumnInfo("dua_or_asma_id")
    val key: Int,
    @ColumnInfo("tasbih_type")
    val tasbihType: TasbihType
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
