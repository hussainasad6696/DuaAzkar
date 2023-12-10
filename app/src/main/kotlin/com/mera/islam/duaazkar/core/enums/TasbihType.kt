package com.mera.islam.duaazkar.core.enums

enum class TasbihType(val type: Int) {
    ASMA_UL_HUSNA(0),
    Dua(1);


    companion object {
        fun toTasbihType(type: Int): TasbihType {
            for (item in entries)
                if (type == item.type) return item

            return Dua
        }
    }
}