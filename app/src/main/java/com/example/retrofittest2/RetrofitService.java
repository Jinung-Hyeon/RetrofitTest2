package com.example.retrofittest2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("v2/list")
    Call<List<ItemModel>> getItem(@Query("page") int page, @Query("limit") int limit);
}
