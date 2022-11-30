package com.example.restorancepatsaji.Listener;

import com.example.restorancepatsaji.Model.NewsHeadline;

import java.util.List;

public interface OnFetchDataListenenr<NewsApiResponse> {
    void onFetchData(List<NewsHeadline> list, String message);
    void onError(String message);
}
