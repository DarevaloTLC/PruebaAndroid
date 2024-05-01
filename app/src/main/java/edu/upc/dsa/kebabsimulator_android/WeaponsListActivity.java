package edu.upc.dsa.kebabsimulator_android;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import edu.upc.dsa.kebabsimulator_android.models.API;
import edu.upc.dsa.kebabsimulator_android.models.Weapon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeaponsListActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private WeaponsListAdapter adapter;

    private final String TAG = WeaponsListActivity.class.getSimpleName();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weapons_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = (RecyclerView) findViewById(R.id.myRecylcerView);


        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager

        // Set the adapter
        adapter = new WeaponsListAdapter();
        recyclerView.setAdapter(adapter);
        try {
            doApiCall();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void doApiCall() throws Exception {
        API apiService = API.retrofit.create(API.class);
        Call<List<Weapon>> call = apiService.weapons();

        call.enqueue(new Callback<List<Weapon>>() {
            @Override
            public void onResponse(Call<List<Weapon>> call, Response<List<Weapon>> response) {
                // set the results to the adapter
                if(response.body() == null){
                    Log.w("WARNING", "Respuesta vacía");
                }
                else{
                    adapter.setData(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Weapon>> call, Throwable t) {
                String msg = "Error in retrofit: "+t.toString();
                Log.d(TAG,msg);
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
            }
        });
    }


}