package com.example.restorancepatsaji.Model;

import java.io.Serializable;
import java.util.List;

public class NewsApiResponse implements Serializable {

    String status;
    int totalResult;
    List<NewsHeadline> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public List<NewsHeadline> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsHeadline> articles) {
        this.articles = articles;
    }
}
