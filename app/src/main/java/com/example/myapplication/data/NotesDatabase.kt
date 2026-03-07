package com.example.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.NAME).build()
    }

//    @Provides
//    fun provideExpenseDao(db: AppDatabase): NotesDao = db.expenseDao()
}


//@Database(
//    entities = [NotesEntity::class],
//    version = 1,
//    exportSchema = false
//)
abstract class AppDatabase : RoomDatabase() {

//    abstract fun expenseDao(): NotesDao

    companion object {
        const val NAME = "app_db"
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
// interface RepositoryModule {
//    @Binds
//    @Singleton
//    fun bindExpenseRepository(
//        impl: NotesRepositoryImpl
//    ): NotesRepository
//}