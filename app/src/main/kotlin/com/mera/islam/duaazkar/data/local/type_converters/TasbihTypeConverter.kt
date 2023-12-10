package com.mera.islam.duaazkar.data.local.type_converters

import androidx.room.TypeConverter
import com.mera.islam.duaazkar.core.enums.TasbihType

class TasbihTypeConverter {
    @TypeConverter
    fun fromIntType(type: Int): TasbihType = TasbihType.toTasbihType(type = type)
    @TypeConverter
    fun fromTasbihType(tasbihType: TasbihType): Int = tasbihType.type
}