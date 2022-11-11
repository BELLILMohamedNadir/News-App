package com.example.newsapp.data_base;

import androidx.room.TypeConverter;

import com.example.newsapp.pojo.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromArticleTOString(ArrayList<News.Root.Article> data){
    return new Gson().toJson(data);
    }
    @TypeConverter
    public ArrayList<News.Root.Article> fromStringToArticle(String s){
        Type a= new TypeToken<ArrayList<News.Root.Article>>(){}.getType();
        return new Gson().fromJson(s,a);
    }
    @TypeConverter
    public String fromSourceTOString(News.Root.Source data){
        return new Gson().toJson(data);
    }
    @TypeConverter
    public News.Root.Source fromStringToSource(String s){
        Type a= new TypeToken<News.Root.Source>(){}.getType();
        return new Gson().fromJson(s,a);
    }




}
