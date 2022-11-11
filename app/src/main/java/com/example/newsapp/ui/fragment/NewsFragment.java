package com.example.newsapp.ui.fragment;



import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp.RecyclerView.NewsRecyclerView;
import com.example.newsapp.data_base.MyDataBase;
import com.example.newsapp.databinding.FragmentNewsBinding;
import com.example.newsapp.pojo.News;
import com.example.newsapp.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class NewsFragment extends Fragment {

    FragmentNewsBinding binding;
    public static NewsRecyclerView newsRecyclerView;
    List<News.Root.Article> data;

    public NewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentNewsBinding.inflate(inflater,container,false);
        binding.newsShimmer.startShimmer();
        data=new ArrayList<>();
        newsRecyclerView = new NewsRecyclerView(data,"news", url -> startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))));
        binding.rvNews.setAdapter(newsRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvNews.setLayoutManager(layoutManager);
        binding.rvNews.setHasFixedSize(true);
        binding.searchNews.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.searchNews.clearFocus();
                Toast.makeText(getActivity(), "wait please", Toast.LENGTH_SHORT).show();
                MainActivity.newsViewModel.getNews(query.trim());
                getNews();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        binding.refreshLayout.setOnRefreshListener(() -> {
            getNews();
            binding.refreshLayout.setRefreshing(false);
        });
        getNews();
        return binding.getRoot();
    }
    void getNews(){
        MyDataBase.getInstance(getActivity()).newsDao().getNews().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News.Root>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(News.Root root) {
                        if (root.getArticles().size()!=0){
                            binding.newsShimmer.stopShimmer();
                            binding.newsShimmer.setVisibility(View.GONE);
                            newsRecyclerView.setData(root.getArticles());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}