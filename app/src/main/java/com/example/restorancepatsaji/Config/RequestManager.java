package com.example.restorancepatsaji.Config;

import android.content.Context;
import android.widget.Toast;

import com.example.restorancepatsaji.Listener.OnFetchDataListenenr;
import com.example.restorancepatsaji.Model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create()).build();

    public void getNewsHeadline(OnFetchDataListenenr listenenr, String category, String query){
        CallNewsApi callNewsApi = retrofit.create(CallNewsApi.class);
        Call<NewsApiResponse> call = callNewsApi.callHeadlines("us", category, query, "836fbecc395c4d18a8b061010e3869e0");

        try {
            call.enqueue(new Callback<NewsApiResponse>() {
                @Override
                public void onResponse(Call<NewsApiResponse> call, Response<NewsApiResponse> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                    listenenr.onFetchData(response.body().getArticles(), response.message());

                }

                @Override
                public void onFailure(Call<NewsApiResponse> call, Throwable t) {
                    listenenr.onError("Request Fail!!");

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public RequestManager(Context context) {
        this.context = context;
    }

    public interface CallNewsApi{
        @GET("top-headlines")
        Call<NewsApiResponse> callHeadlines(
                @Query("country")String country,
                @Query("category")String category,
                @Query("q")String query,
                @Query("apiKey")String api_key
        );
    }
}
