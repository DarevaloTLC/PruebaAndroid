package edu.upc.dsa.kebabsimulator_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText usernameField;
    private EditText passwordField;
    private Button loginButton;

    private final OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loginUser(usernameField.getText().toString(), passwordField.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loginUser(String username, String password) throws Exception {
        String url = "http://10.0.2.2:8080/dsaApp/users/login"; // Cambia esto por la URL de tu servidor
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("userName", username);
            json.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Request request = realizarConsultaAPI(JSON, json, url);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Trata el caso de error
                e.printStackTrace();
                Log.d("error", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Mostrar un diálogo de éxito
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Login Successful")
                                    .setMessage("You have been successfully logged in.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User clicked OK button
                                            // Aquí puedes realizar acciones adicionales como cambiar de actividad si es necesario
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                } else {
                    // También considera manejar la respuesta no exitosa adecuadamente aquí
                    Log.e("HTTP Code", "HTTP " + response.code());
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Login failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

        });
    }

    public Request realizarConsultaAPI(MediaType JSON, JSONObject json, String url){
        RequestBody body = RequestBody.create(json.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }
}
