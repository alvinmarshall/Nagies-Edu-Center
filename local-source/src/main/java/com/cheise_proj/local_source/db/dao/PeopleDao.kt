package com.cheise_proj.local_source.db.dao

import androidx.room.*
import com.cheise_proj.local_source.model.people.PeopleLocal
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePeople(peopleLocalList: List<PeopleLocal>)

    @Query("SELECT * FROM people")
    fun getPeopleList(): Observable<List<PeopleLocal>>

    @Query("SELECT * FROM people WHERE id = :identifier")
    fun getPeople(identifier: String): Single<PeopleLocal>

    @Query("DELETE FROM people")
    fun deletePeople()

    @Transaction
    fun clearAndInsertPeople(peopleLocalList: List<PeopleLocal>) {
        deletePeople()
        savePeople(peopleLocalList)
    }
}