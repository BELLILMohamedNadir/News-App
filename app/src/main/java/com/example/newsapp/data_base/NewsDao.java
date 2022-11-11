package com.example.newsapp.data_base;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newsapp.pojo.News;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface NewsDao {
    @Insert
    Completable insertNews(News.Root news);
    @Query("SELECT * FROM news_table")
    Observable<News.Root> getNews();
    @Insert
    Completable insertFavoriteNews(News.Root.Article news);
    @Query("SELECT * FROM article_table")
    Single<List<News.Root.Article>> getFavoriteNews();
    @Delete
    Completable deleteFavoriteNews(News.Root.Article article);
    @Update
    Completable updateNews(News.Root news);
    @Query("SELECT * FROM news_table where ID!=0 and ID!=1")
    Observable<News.Root> getSearchedNews();


}
