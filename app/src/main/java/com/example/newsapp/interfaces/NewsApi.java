package com.example.newsapp.interfaces;

import com.example.newsapp.pojo.News;
import com.example.newsapp.ui.MainActivity;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewsApi {
    @GET("everything?country=us&apiKey="+MainActivity.API_KEY)
    Observable<News.Root> getNews(@Query("q") String q);
    @GET("top-headlines?country=us&apiKey="+MainActivity.API_KEY)
    Observable<News.Root> getSearchedNews(@Query("category") String category);
}
