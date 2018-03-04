package com.example.sumitasharma.easyvocabulary.dictionaryutils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.example.sumitasharma.easyvocabulary.util.WordUtil.API_KEY;
import static com.example.sumitasharma.easyvocabulary.util.WordUtil.APP_ID;

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("/api/v1/entries/en/{word_id}")
    @Headers({"app_id: " + APP_ID, "app_key: " + API_KEY})
    Call<Example> getMyJSON(@Path("word_id") String wordId);
}
