package com.example.newsapp.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.newsapp.pojo.News;

public class DataViewModel extends AndroidViewModel {
    Repository repository;
    public DataViewModel(@NonNull Application application) {
        super(application);
        repository=Repository.getInstance(application);
    }

    public void insertNews(News.Root news){
         repository.insertNews(news);
    }
    public void insertFavoriteNews(News.Root.Article news){
        repository.insertFavoriteNews(news);
    }
    public void insertSearchedNews(News.Root news){
        repository.insertSearchedNews(news);
    }

    public void deleteFavoriteNews(News.Root.Article article){repository.deleteFavoriteNews(article);}
    public void updateNews(News.Root news){repository.updateNews(news);}




}
