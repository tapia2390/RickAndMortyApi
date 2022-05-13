package com.cb.rickandmortyapi.Model.retrofit;

import com.cb.rickandmortyapi.Model.clases.DataEpisode;
import com.cb.rickandmortyapi.Model.clases.Episode;
import com.cb.rickandmortyapi.Model.clases.Personaje;
import com.cb.rickandmortyapi.Model.clases.Results;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MyApiService {


    @GET("character/")
    Call<Personaje> getcharacter();


    @GET("character/")
    Call<Personaje> getcharacterNextBack(
            @Query("page") int page
    );


    @GET("episode/")
    Call<DataEpisode> getListEpisode();

    @GET("episode/")
    Call<DataEpisode> getespisodeNextBack(
            @Query("page") int page
    );

    @GET("episode/{id}")
    Call<Episode>
    getEpisode(
            @Path("id") int Number);


}
