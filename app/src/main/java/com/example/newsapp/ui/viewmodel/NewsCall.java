package com.example.newsapp.ui.viewmodel;

import com.example.newsapp.interfaces.NewsApi;
import com.example.newsapp.pojo.News;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsCall {
    private static volatile NewsCall INSTANCE=null;
    final String BASE_URL ="https://newsapi.org/v2/";
    NewsApi api;

    public NewsCall(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(NewsApi.class);
    }
    public static NewsCall getInstance(){
        if (INSTANCE==null){
            synchronized (NewsViewModel.class){
                if (INSTANCE==null){
                    INSTANCE=new NewsCall();
                }
            }
        }
        return INSTANCE;
    }
    public  Observable<News.Root> getNews(String category){
        return api.getNews(category);
    }
    public  Observable<News.Root> getSearchedNews(String category){
        return api.getSearchedNews(category);
    }


}
