package storage.roomdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuotesDAO {
   // get all quotes from database
    @Query("SELECT * FROM QUOTES_TABLE ORDER BY id DESC")
    fun getAllQuotesFromData(): LiveData<List<QuotesEntity>>

    //insert quotes into database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuoteToDatabase(quotes: List<QuotesEntity>)

    //search quotes from database by author and content
    @Query("SELECT * FROM QUOTES_TABLE WHERE author LIKE :search OR content LIKE :search")
    fun searchQuotes(search: String): LiveData<List<QuotesEntity>>

    //delete all quotes from database
    @Query("DELETE FROM QUOTES_TABLE")
    suspend fun deleteAllQuotes()
    @Query("UPDATE QUOTES_TABLE SET quoteType = :quoteT WHERE id = :key ")
    fun updateQuoteType(key: String, quoteT: String)
}
