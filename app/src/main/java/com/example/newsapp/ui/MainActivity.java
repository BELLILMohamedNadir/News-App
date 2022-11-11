package com.example.newsapp.ui;

import static com.example.newsapp.ui.fragment.NewsFragment.newsRecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;


import com.example.newsapp.R;
import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.pojo.News;
import com.example.newsapp.ui.viewmodel.DataViewModel;
import com.example.newsapp.ui.viewmodel.NewsViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static NewsViewModel newsViewModel;
    public static DataViewModel dataViewModel;
    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    public static NavController navController;
    public static final String API_KEY="YOUR API KEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        editor=sp.edit();
        getNews();
        initialiseNavigation();

    }
    // getting news from the api
    private void getNews() {
        newsViewModel= ViewModelProviders.of(MainActivity.this).get(NewsViewModel.class);
        dataViewModel=ViewModelProviders.of(MainActivity.this).get(DataViewModel.class);
        newsViewModel.getNews("general");
        NewsViewModel.liveDataNews.observe(this, new Observer<News.Root>() {
            @Override
            public void onChanged(News.Root root) {
                if(root.getArticles().size()!=0){
                    // if first time 1 --> first time that means we will insert the news for the first time
                    if (sp.getInt("firstTime",1)==1)
                    dataViewModel.insertNews(root);
                    // update the data base
                    else
                        dataViewModel.updateNews(root);
                    newsRecyclerView.setData(root.getArticles());
                }

            }
        });
    }
    void initialiseNavigation(){
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment!=null)
            navController = navHostFragment.getNavController();
        /* in the first time the searchFragment will show because we don't have data in db
         but when we made the first search we're gonna change the navGraph to nav_graph1 and the menu
         for showing the SearchedNewsFragment*/
        if (sp.getInt("firstTimeSearch",1)==0){
            binding.navMain.getMenu().clear();
            binding.navMain.inflateMenu(R.menu.menu1);
            navController.setGraph(R.navigation.nav_graph1);
        }

        NavigationUI.setupWithNavController(binding.navMain,navController);
    }
}