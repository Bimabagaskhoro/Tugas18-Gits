//package com.bimabagaskhoro.uigitstugas18.local
//
//import androidx.room.*
//import com.bimabagaskhoro.uigitstugas18.model.animal.AnimalModel
//
//@Dao
//interface AnimalDao {
//    @Insert
//    fun insert(animal: AnimalModel)
//
//    @Update
//    fun update(animal: AnimalModel)
//
//    @Delete
//    fun delete(animal: AnimalModel)
//
//    @Query("SELECT * FROM animal")
//    fun getAll() : List<AnimalModel>
//
//    @Query("SELECT * FROM animal WHERE id = :id")
//    fun getById(id: Int) : List<AnimalModel>
//}