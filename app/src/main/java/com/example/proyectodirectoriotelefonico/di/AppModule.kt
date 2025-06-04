package com.example.proyectodirectoriotelefonico.di

import android.content.Context
import androidx.room.Room
import com.example.proyectodirectoriotelefonico.room.DatosDatabase
import com.example.proyectodirectoriotelefonico.room.DatosDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDatosDao(datosDatabase: DatosDatabase): DatosDatabaseDao{
        return datosDatabase.datosDao()
    }

    @Singleton
    @Provides
    fun provideDatosDatabase(@ApplicationContext context: Context): DatosDatabase{
        return Room.databaseBuilder(
            context,
            DatosDatabase::class.java, name = "datos_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}