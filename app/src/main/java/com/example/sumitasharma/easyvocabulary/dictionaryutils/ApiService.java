package com.example.sumitasharma.easyvocabulary.dictionaryutils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("api/v2/dictionary/{word_id}?format=json")
    Call<List<Example>> getMyJSON(@Path("word_id") String wordId);
}
