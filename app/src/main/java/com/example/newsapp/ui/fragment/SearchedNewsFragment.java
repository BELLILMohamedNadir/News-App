package com.example.newsapp.ui.fragment;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.newsapp.ui.MainActivity.dataViewModel;
import static com.example.newsapp.ui.MainActivity.navController;
import static com.example.newsapp.ui.MainActivity.newsViewModel;
import static com.example.newsapp.ui.MainActivity.sp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.RecyclerView.NewsRecyclerView;
import com.example.newsapp.data_base.MyDataBase;
import com.example.newsapp.databinding.FragmentSearchedNewsBinding;
import com.example.newsapp.interfaces.OnClick;
import com.example.newsapp.pojo.News;
import com.example.newsapp.ui.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SearchedNewsFragment extends Fragment {

    FragmentSearchedNewsBinding binding;
    NewsRecyclerView newsRecyclerView;
    List<News.Root.Article> data;
    String category="general";

    public SearchedNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
        category=getArguments().getString("field","general");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSearchedNewsBinding.inflate(inflater, container, false);
        data=new ArrayList<>();
        newsRecyclerView = new NewsRecyclerView(data,"news", new OnClick() {
            @Override
            public void onClickItem(String url) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        binding.rvSearchedNews.setAdapter(newsRecyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.rvSearchedNews.setLayoutManager(layoutManager);
        binding.rvSearchedNews.setHasFixedSize(true);
        binding.btnSearchedNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp.getInt("firstTimeSearch",1)==0)
                navController.navigate(R.id.searchFragment2);
                else
                    navController.navigate(R.id.searchFragment);

            }
        });
        binding.refreshLayoutSearchedNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSearchedNews();
                binding.refreshLayoutSearchedNews.setRefreshing(false);
            }
        });
        getNews();
        getSearchedNews();
        return binding.getRoot();
    }
    private void getNews() {
        newsViewModel.getSearchedNews(category);

        NewsViewModel.liveDataSearchedNews.observe(getActivity(), new Observer<News.Root>() {
            @Override
            public void onChanged(News.Root root) {
                if(root.getArticles().size()!=0){
                    if (sp.getInt("firstTimeSearch",1)==1)
                        dataViewModel.insertSearchedNews(root);
                    else
                        dataViewModel.updateNews(root);
                    newsRecyclerView.setData(root.getArticles());
                }

            }
        });
    }
    void getSearchedNews(){
        MyDataBase.getInstance(getActivity()).newsDao().getSearchedNews().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.Observer<News.Root>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(News.Root root) {
                        if (root.getArticles().size()!=0){
                            binding.searchedNewsShimmer.stopShimmer();
                            binding.searchedNewsShimmer.setVisibility(View.GONE);
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