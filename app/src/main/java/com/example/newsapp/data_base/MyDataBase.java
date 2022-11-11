package com.example.newsapp.data_base;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.newsapp.pojo.News;


@Database(entities = {News.Root.class,News.Root.Article.class},version = 3)
@TypeConverters(Converters.class)
public abstract class MyDataBase extends RoomDatabase {
    public abstract NewsDao newsDao();
    public static volatile MyDataBase INSTANCE=null;
    public static MyDataBase getInstance(Context context){
        if (INSTANCE==null){
            synchronized (MyDataBase.class){
                if (INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),MyDataBase.class,"DB_NAME")
                           .fallbackToDestructiveMigrationFrom(0,1,2).build(); ///
                }
            }
        }
        return INSTANCE;
    }
}
