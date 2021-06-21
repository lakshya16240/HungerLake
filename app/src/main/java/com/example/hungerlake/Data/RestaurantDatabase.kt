package com.example.hungerlake.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hungerlake.DAOs.RestaurantDao
import com.example.hungerlake.models.ReviewEntity
import com.example.hungerlake.models.Venue

private const val DATABASE = "Restaurant"

@Database(
        entities = [Venue::class,ReviewEntity::class],
        version = 6,
        exportSchema = false
)
//@TypeConverters(RestaurantDataConverter::class)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun roomNoteDao(): RestaurantDao

    //code below courtesy of https://github.com/googlesamples/android-sunflower; it     is open
    //source just like this application.
    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: RestaurantDatabase? = null

        fun getInstance(context: Context): RestaurantDatabase {
            return instance ?: synchronized(this) {
                instance
                        ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RestaurantDatabase {
            return Room.databaseBuilder(context, RestaurantDatabase::class.java, DATABASE)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}