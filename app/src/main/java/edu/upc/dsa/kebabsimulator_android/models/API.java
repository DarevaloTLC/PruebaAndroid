package edu.upc.dsa.kebabsimulator_android.models;

import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import android.util.Log;

public interface API {
    // Ensure the base URL is correct and ends with a '/'
    String BASE_URL = "http://10.0.2.2:8080/dsaApp/";

    @GET("weapons/getWeapons")  // Ensure there is no leading '/' if the base URL ends with one
    Call<List<Weapon>> weapons();

    // Static initialization block to ensure Retrofit is set up correctly
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
