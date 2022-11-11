package com.example.newsapp.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
public class News {

    @Entity(tableName = "news_table")
    public static class Root{
        @PrimaryKey(autoGenerate = true)
        public int ID;
        private String status;
        private int totalResults;
        private ArrayList<Article> articles;

        public Root() {
        }

        public String getStatus() {
            return status;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public ArrayList<Article> getArticles() {
            return articles;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getID() {
            return ID;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public void setArticles(ArrayList<Article> articles) {
            this.articles = articles;
        }
        @Entity(tableName = "article_table")
        public static class Article{
            @PrimaryKey(autoGenerate = true)
            public int ID;
            private Source source;
            private String author;
            private String title;
            private String description;
            private String url;
            private String urlToImage;
            private String publishedAt;
            private String content;
            private boolean favorite=false;

            public Article() {
            }

            public boolean getFavorite() {
                return favorite;
            }

            public void setFavorite(boolean favorite) {
                this.favorite = favorite;
            }

            public Source getSource() {
                return source;
            }

            public String getAuthor() {
                return author;
            }

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }

            public String getUrl() {
                return url;
            }

            public String getUrlToImage() {
                return urlToImage;
            }

            public String getPublishedAt() {
                return publishedAt;
            }

            public String getContent() {
                return content;
            }

            public void setSource(Source source) {
                this.source = source;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setUrlToImage(String urlToImage) {
                this.urlToImage = urlToImage;
            }

            public void setPublishedAt(String publishedAt) {
                this.publishedAt = publishedAt;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class Source{
            private String id;
            private String name;

            public Source() {
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }




}
