package com.mera.islam.duaazkar.domain.models

import com.mera.islam.duaazkar.core.enums.TasbihType

data class TasbihModel(
    var count: Int,
    val totalCount: Int,
    val key: Int,
    val tasbihType: TasbihType,
    val id: Int
)
