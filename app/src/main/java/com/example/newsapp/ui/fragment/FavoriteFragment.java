package com.example.newsapp.ui.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.RecyclerView.NewsRecyclerView;
import com.example.newsapp.data_base.MyDataBase;
import com.example.newsapp.databinding.FragmentFavoriteBinding;
import com.example.newsapp.pojo.News;
import com.example.newsapp.ui.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FavoriteFragment extends Fragment {

    public static FragmentFavoriteBinding binding;
    NewsRecyclerView newsRecyclerView;
    List<News.Root.Article> data,searched;
    NewsViewModel newsViewModel;

    public FavoriteFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFavoriteBinding.inflate(inflater,container,false);
        binding.favoriteShimmer.startShimmer();
        newsViewModel= ViewModelProviders.of(getActivity()).get(NewsViewModel.class);
        data=new ArrayList<>();

        //set the recycler view
        newsRecyclerView = new NewsRecyclerView(data,"favorite", url -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        binding.rvFavorite.setAdapter(newsRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvFavorite.setLayoutManager(layoutManager);
        binding.rvFavorite.setHasFixedSize(true);

        binding.searchFavorite.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //customize the data
                searched=new ArrayList<>();
                for (int i=0;i<data.size();i++){
                    if (data.get(i).getTitle().contains(query))
                        searched.add(data.get(i));
                }
                if(searched.size()==0){
                    //show the empty image
                    binding.imgEmpty.setVisibility(View.VISIBLE);
                    binding.txtEmpty.setVisibility(View.VISIBLE);
                    //hide the other views
                    binding.searchFavorite.setVisibility(View.GONE);
                    stopShimmer();
                    binding.rvFavorite.setVisibility(View.GONE);

                }else{
                    //hide the image empty and facebookShimmer effect if it is exist
                    binding.imgEmpty.setVisibility(View.GONE);
                    binding.txtEmpty.setVisibility(View.GONE);
                    stopShimmer();
                    //show the other views
                    binding.searchFavorite.setVisibility(View.VISIBLE);
                    binding.rvFavorite.setVisibility(View.VISIBLE);
                    newsRecyclerView.setData(searched);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.refreshLayoutFavoriteNews.setOnRefreshListener(() -> {
            getNews();
            binding.refreshLayoutFavoriteNews.setRefreshing(false);
        });
        getNews();
        return binding.getRoot();
    }

    private void stopShimmer() {
        if (binding.favoriteShimmer.isShimmerVisible()){
            binding.favoriteShimmer.setVisibility(View.GONE);
            if(binding.favoriteShimmer.isShimmerStarted())
                binding.favoriteShimmer.stopShimmer();
        }
    }

    void getNews(){
        MyDataBase.getInstance(getActivity()).newsDao().getFavoriteNews().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<News.Root.Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<News.Root.Article> articles) {
                        if (articles.size()!=0) {
                            data=articles;
                            stopShimmer();
                            newsRecyclerView.setData(articles);
                        }else{
                            binding.imgEmpty.setVisibility(View.VISIBLE);
                            binding.txtEmpty.setVisibility(View.VISIBLE);
                            binding.searchFavorite.setVisibility(View.GONE);
                            stopShimmer();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }
                });
    }
}