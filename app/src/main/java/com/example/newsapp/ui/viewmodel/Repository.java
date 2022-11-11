package com.example.newsapp.ui.viewmodel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.newsapp.ui.MainActivity.editor;

import android.app.Application;
import android.util.Log;

import com.example.newsapp.data_base.MyDataBase;
import com.example.newsapp.data_base.NewsDao;
import com.example.newsapp.pojo.News;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class Repository {

    private NewsDao newsDao;
    public static volatile Repository INSTANCE=null;
    public Repository(Application application){
        MyDataBase dataBase=MyDataBase.getInstance(application.getApplicationContext());
        newsDao=dataBase.newsDao();
    }
    public static Repository getInstance(Application application){
        if (INSTANCE==null){
            synchronized (DataViewModel.class){
                if (INSTANCE==null){
                    INSTANCE=new Repository(application);
                }
            }
        }
        return INSTANCE;
    }
    public void insertNews(News.Root news){
        newsDao.insertNews(news).subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: news ");
                        // that mean we're gonna just update the data base table after we made the first search operation successfully
                        editor.putInt("firstTime",0).commit();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: news "+e.getMessage());

                    }
                });
    }
    public void insertSearchedNews(News.Root news){
        newsDao.insertNews(news).subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        //that mean we gonna change the navGraph because we made the first search operation successfully
                        editor.putInt("firstTimeSearch",0).commit();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }
                });
    }
    public void insertFavoriteNews(News.Root.Article news){
        newsDao.insertFavoriteNews(news).subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete favorite: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: favorite "+e.getMessage());
                    }
                });
    }
    public void deleteFavoriteNews(News.Root.Article article){
        newsDao.deleteFavoriteNews(article).subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: delete ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: favorite "+e.getMessage());
            }
        });
    }
    public void updateNews(News.Root news){
        newsDao.updateNews(news).subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: update ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: update "+e.getMessage());
                    }
                });
    }


}
