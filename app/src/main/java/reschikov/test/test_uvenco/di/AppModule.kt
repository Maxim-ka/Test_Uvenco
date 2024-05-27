package reschikov.test.test_uvenco.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import reschikov.test.test_uvenco.data.ICounted
import reschikov.test.test_uvenco.data.IDatabase
import reschikov.test.test_uvenco.data.Repo
import reschikov.test.test_uvenco.data.database.DataBase
import reschikov.test.test_uvenco.data.database.DataBaseProvider
import reschikov.test.test_uvenco.data.database.FirstLoad
import reschikov.test.test_uvenco.data.temperature.ShowTemperature
import reschikov.test.test_uvenco.data.time.ShowTime
import reschikov.test.test_uvenco.ui.IRepo
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    companion object {

        private const val NAME_DATABASE = "uvenco_coffees"

        @Singleton
        @Provides
        fun provideAppDatabase(@ApplicationContext context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, NAME_DATABASE)
                .addCallback(FirstLoad())
                .build()
        }
    }

    @Singleton
    @Binds
    abstract fun bindIDatabase(dataBaseProvider: DataBaseProvider) : IDatabase

    @Time
    @Singleton
    @Binds
    abstract fun bindTime(showTime: ShowTime) : ICounted<String>

    @Temperature
    @Singleton
    @Binds
    abstract fun bindTemperature(shoeTemperature: ShowTemperature) : ICounted<String>

    @Singleton
    @Binds
    abstract fun bindIRepo(repo: Repo) : IRepo

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Temperature


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Time