package com.example.pelisomdb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText titulo_buscar;
    private TextView titulo,year,director,plot;

    private ProgressDialog pDialog;
    private TareaConsultaAPI tarea;
    private String respJSON = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo=findViewById(R.id.titulo);
        year=findViewById(R.id.year);
        director=findViewById(R.id.director);
        plot=findViewById(R.id.trama);
        titulo_buscar=findViewById(R.id.peli);
    }

    public void buscar_en_omdb(View v){
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setMessage("Procesando...");
        pDialog.setCancelable(true);

        tarea = new TareaConsultaAPI();
        tarea.execute(titulo_buscar.getText().toString());
    }

    private class TareaConsultaAPI extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try
            {
                String url = getString(R.string.apipath)+params[0]+getString(R.string.apikey);
                Log.e(TAG,url);
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        respJSON = s;
                    }
                }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,"VolleyError!", volleyError);
                    }
                });

                RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                rQueue.add(request);

            }
            catch(Exception ex)
            {
                Log.e(TAG,"Error!", ex);
            }
            return respJSON;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0].intValue();
            pDialog.setProgress(progreso);
        }

        @Override
        protected void onPreExecute() {
            pDialog.setMax(100);
            pDialog.setProgress(0);
        }

        @Override
        protected void onPostExecute(String result) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (!result.isEmpty()){
                Gson gs = new Gson();
                loadPelicula(gs.fromJson(result,Pelicula.class));
            }

        }

        @Override
        protected void onCancelled() {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }

    private void loadPelicula(Pelicula p){
        titulo.setText(p.getTitle());
        year.setText(p.getYear());
        director.setText(p.getDirector());
        plot.setText(p.getPlot());
    }
}
