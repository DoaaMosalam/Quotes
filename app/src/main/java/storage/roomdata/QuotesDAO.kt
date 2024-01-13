package storage.roomdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pojo.Quotes

@Dao
interface QuotesDAO {
    @Query("SELECT * FROM QUOTES_TABLE ORDER BY id DESC")
    suspend fun getAllQuotesFromData(): LiveData<List<QuotesEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuoteToData(quotesEntity: QuotesEntity)

    @Query("UPDATE QUOTES_TABLE SET quoteType = :quoteT WHERE id = :key ")
    fun updateQuoteType(key: String, quoteT: String)
    @Delete
    suspend fun deleteQuote(quotesEntity: QuotesEntity)
}
