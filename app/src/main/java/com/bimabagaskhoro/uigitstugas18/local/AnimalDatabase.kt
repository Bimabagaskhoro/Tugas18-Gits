//package com.bimabagaskhoro.uigitstugas18.local
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.bimabagaskhoro.uigitstugas18.model.animal.AnimalModel
//
//@Database(entities = [AnimalModel::class], version = 1, exportSchema = false)
//abstract class AnimalDatabase: RoomDatabase() {
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AnimalDatabase? = null
//
//        fun getDatabase(context: Context): AnimalDatabase {
//            return INSTANCE ?: synchronized(this) {
//                // Create database here
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AnimalDatabase::class.java,
//                    "note_db"
//                )
//                    .allowMainThreadQueries() //allows Room to executing task in main thread
//                    .fallbackToDestructiveMigration() //allows Room to recreate table if no migrations found
//                    .build()
//
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//
//    abstract fun getAnimalDao() : AnimalDao
//}