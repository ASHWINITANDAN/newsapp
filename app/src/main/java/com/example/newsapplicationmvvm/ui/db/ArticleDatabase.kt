package com.example.newsapplicationmvvm.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapplicationmvvm.ui.models.Article


@Database(
    entities = [Article::class],
    version = 1
)
// Database class comes with abstract keyword?
// It allow you to skip implementing some default methods of the parent class which is not needed in child class, which makes this class more flexible.

@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object{

        @Volatile   // Other thread can immediately see when this thread changes this instance
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()    //To ensure that at a time we have only one database instance


        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context): ArticleDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
        }
    }

}