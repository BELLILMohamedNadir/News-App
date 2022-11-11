package com.example.newsapp.ui.fragment;

import static com.example.newsapp.ui.MainActivity.navController;
import static com.example.newsapp.ui.MainActivity.sp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.RecyclerView.SearchRecyclerView;
import com.example.newsapp.databinding.FragmentSearchBinding;
import com.example.newsapp.interfaces.OnClick;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {

   FragmentSearchBinding binding;
   List<String> data;
   SearchRecyclerView searchRecyclerView;

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(inflater, container, false);
            data=new ArrayList<>();
            data.add("business");
            data.add("entertainment");
            data.add("health");
            data.add("science");
            data.add("sports");
            data.add("technology");
        Bundle bundle=new Bundle();

        searchRecyclerView = new SearchRecyclerView(data, new OnClick() {
            @Override
            public void onClickItem(String url) {

                if (!url.equals("")) {
                    bundle.putString("field", url);
                    if (sp.getInt("firstTimeSearch",1)==1)
                    navController.navigate(R.id.searchedNewsFragment,bundle);
                    else
                        navController.navigate(R.id.searchedNewsFragment2,bundle);
                }
            }
        });
        binding.rvField.setAdapter(searchRecyclerView);
        LinearLayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        binding.rvField.setLayoutManager(layoutManager);
        binding.rvField.setHasFixedSize(true);

        return binding.getRoot();
    }
}