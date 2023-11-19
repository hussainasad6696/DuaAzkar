package  com.mera.islam.duaazkar.data.local.dao.dua

import androidx.room.Dao
import androidx.room.Query
import  com.mera.islam.duaazkar.data.local.entities.dua.DuaTranslatorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DuaTranslatorDao {
    @Query("SELECT * FROM dua_translators")
    fun getAllTranslators(): Flow<List<DuaTranslatorEntity>>
}