package com.example.newsapp.ui.viewmodel;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.newsapp.ui.MainActivity.editor;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsapp.data_base.MyDataBase;
import com.example.newsapp.pojo.News;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsViewModel extends ViewModel {
    public static MutableLiveData<News.Root> liveDataNews=new MutableLiveData<>();
    public static MutableLiveData<News.Root> liveDataSearchedNews=new MutableLiveData<>();

    public void getNews(String category){
       NewsCall.getInstance().getNews(category)
                .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News.Root>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(News.Root root) {
                        if (root.getArticles().size() != 0){
                            liveDataNews.setValue(root);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
    public void getSearchedNews(String category){
        NewsCall.getInstance().getSearchedNews(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News.Root>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(News.Root root) {
                        if (root.getArticles().size() != 0){
                            liveDataSearchedNews.setValue(root);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
