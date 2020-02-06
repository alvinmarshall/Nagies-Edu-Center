package com.cheise_proj.local_source.db.dao

import androidx.room.*
import com.cheise_proj.local_source.model.files.CircularLocal
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FilesDao {
    //region CIRCULAR
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCircular(circularLocalList: List<CircularLocal>)

    @Query("SELECT * FROM circular ORDER BY id DESC")
    fun getCirculars(): Observable<List<CircularLocal>>

    @Query("SELECT * FROM circular WHERE id = :identifier")
    fun getCircular(identifier: String): Single<CircularLocal>

    @Query("DELETE FROM circular")
    fun deleteCircular()

    @Transaction
    fun clearAndInsertCircular(circularLocalList: List<CircularLocal>) {
        deleteCircular()
        saveCircular(circularLocalList)
    }
    //endregion
}