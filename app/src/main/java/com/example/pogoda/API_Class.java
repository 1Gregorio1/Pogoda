package com.example.pogoda;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Class {

    @GET("changes/")
    Call<String<Change>> loadChanges(@Query("q") String status);
}
