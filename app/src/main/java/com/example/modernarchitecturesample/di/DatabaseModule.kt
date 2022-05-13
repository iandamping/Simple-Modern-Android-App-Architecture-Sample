package com.example.modernarchitecturesample.di

import android.content.Context
import androidx.room.Room
import com.example.modernarchitecturesample.core.datasource.cache.MovieDao
import com.example.modernarchitecturesample.core.datasource.cache.MovieDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): MovieDatabase =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movieDb")
            .fallbackToDestructiveMigration().build()


    @Provides
    fun provideFavDao(db: MovieDatabase):  MovieDao{
        return db.movieDao()
    }


}