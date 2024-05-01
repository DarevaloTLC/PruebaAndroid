package edu.upc.dsa.kebabsimulator_android.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;


public interface API {
    String URL = "http://10.0.2.2:8080/dsaApp/";
    @GET("/weapons/getWeapons")
    Call<List<Weapon>> weapons();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
