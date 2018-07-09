package com.example.sumitasharma.easyvocabulary.dictionaryutils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */
    @GET("words?md=d&max=1")
    Call<List<Example>> getMyJSON(@Query("sp") String wordId);
}
