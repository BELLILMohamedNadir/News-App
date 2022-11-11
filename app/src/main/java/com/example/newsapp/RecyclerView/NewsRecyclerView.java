package com.example.newsapp.RecyclerView;

import static com.example.newsapp.ui.fragment.FavoriteFragment.binding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.interfaces.OnClick;
import com.example.newsapp.pojo.News;
import com.example.newsapp.ui.MainActivity;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewsRecyclerView extends RecyclerView.Adapter<NewsRecyclerView.NewsViewHolder> {

    List<News.Root.Article> data;
    OnClick listener;
    static int index=0;
     String source;

    public NewsRecyclerView(List<News.Root.Article> data,String source, OnClick listener) {
        this.data = data;
        this.source=source;
        this.listener=listener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news,null,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
            holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<News.Root.Article> roots) {
        this.data=roots;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView img_news;
        CircleImageView img_favorite;
        TextView txt_news,txt_source;
        View view;
        String url;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            img_news=itemView.findViewById(R.id.img_news);
            img_favorite=itemView.findViewById(R.id.img_favorite);
            txt_news=itemView.findViewById(R.id.txt_news);
            txt_source=itemView.findViewById(R.id.txt_source);

            img_favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //if we're coming from the FavoriteFragment
                    if (source.equals("favorite")) {
                        MainActivity.dataViewModel.deleteFavoriteNews(data.get(getAdapterPosition()));
                        data.remove(getAdapterPosition());
                        if (data.size()!=0)
                        notifyDataSetChanged();
                        else{
                            binding.imgEmpty.setVisibility(View.VISIBLE);
                            binding.txtEmpty.setVisibility(View.VISIBLE);
                            binding.searchFavorite.setVisibility(View.GONE);
                            binding.favoriteShimmer.stopShimmer();
                            binding.favoriteShimmer.setVisibility(View.GONE);
                        }

                    }else {
                        //we're coming from the NewsFragment
                        // to manage the clicking
                        if (index == 0) {
                            img_favorite.setImageResource(R.drawable.ic_favorite_fill);
                            data.get(getAdapterPosition()).setFavorite(true);
                            ++index;
                            MainActivity.dataViewModel.insertFavoriteNews(data.get(getAdapterPosition()));
                        } else {
                            img_favorite.setImageResource(R.drawable.ic_favorite);
                            data.get(getAdapterPosition()).setFavorite(false);
                            --index;
                        }
                    }

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    url=data.get(getAdapterPosition()).getUrl();
                    // add the https to the url
                    if (!url.contains("https") && url.contains("http"))
                        url.replace("http","https");
                    else
                        if (!url.contains("https"))
                            url="https://"+url;
                        //send the url to the fragment
                    listener.onClickItem(url);
                }
            });
        }
        // put the data inside the layout
        void onBind(News.Root.Article article){
            if (source.equals("favorite"))
                img_favorite.setImageResource(R.drawable.ic_favorite_fill);
            txt_news.setText(article.getTitle());
            Glide.with(view).load(article.getUrlToImage()).into(img_news);
            txt_source.setText(article.getSource().getName());
            if (data.get(getAdapterPosition()).getFavorite())
                img_favorite.setImageResource(R.drawable.ic_favorite_fill);
            else
                img_favorite.setImageResource(R.drawable.ic_favorite);


        }
    }
}
