package com.cb.rickandmortyapi.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.cb.rickandmortyapi.Model.Utility.Utility;
import com.cb.rickandmortyapi.Model.clases.DataEpisode;
import com.cb.rickandmortyapi.Model.clases.Episode;
import com.cb.rickandmortyapi.Model.clases.Modelo;
import com.cb.rickandmortyapi.Model.clases.Episode;
import com.cb.rickandmortyapi.Model.clases.Personaje;
import com.cb.rickandmortyapi.Model.clases.Results;
import com.cb.rickandmortyapi.Model.retrofit.MyApiRetrofit;
import com.cb.rickandmortyapi.R;
import com.cb.rickandmortyapi.SplashScreen;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodioActivity extends AppCompatActivity {

    private ImageView rickAndMortyImage;
    private TextView nameTextView;
    private TextView statusTextView;
    private TextView lastLocationTextView;
    private TextView originTextView;
    private TextView speciesTextView;
    private ImageView statusImage;


    Modelo modelo = Modelo.getInstance();
    SweetAlertDialog pDialog;
    Utility utility;
    ResultEpisodeAdapter resultEpisodeAdapter;
    List<Episode> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    SearchView search2;
    LinearLayout layout_next_back;
    Button btn_back;
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodio);


        if (savedInstanceState != null) {
            Intent i = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(i);
            finish();
            return;
        }

        utility = new Utility();


        rickAndMortyImage = (ImageView) findViewById(R.id.rickAndMortyImage);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        lastLocationTextView = (TextView) findViewById(R.id.lastLocationTextView);
        originTextView = (TextView) findViewById(R.id.originTextView);
        speciesTextView = (TextView) findViewById(R.id.speciesTextView);
        statusImage = (ImageView) findViewById(R.id.statusImage);
        layout_next_back = (LinearLayout) findViewById(R.id.layout_next_back);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        Picasso.with(getApplicationContext())
                .load(modelo.results.getImage())
                .placeholder(R.drawable.rick_nad_morty_background)
                .error(R.drawable.rick_nad_morty_background)
                .into(rickAndMortyImage);

        nameTextView.setText("Nombre: " + modelo.results.getName());
        statusTextView.setText("" + modelo.results.getStatus());
        speciesTextView.setText("" + modelo.results.getSpecies());
        lastLocationTextView.setText("Ubicaión: " + modelo.results.getLocation().getName());
        originTextView.setText("Origen: " + modelo.results.getOrigin().getName());


        if (modelo.results.getStatus().equals("unknown")) {
            statusImage.setBackgroundResource(R.drawable.ic_baseline_gray);
        } else {
            statusImage.setBackgroundResource(R.drawable.ic_baseline_brightness);
        }

        if (utility.estado(getApplicationContext())) {
            loadswet(getString(R.string.text_validating_information));
            getEpisode();
        } else {
            showAlertWithoutInternet();
            return;
        }
    }


    private void getEpisode() {

        try {

            final Call<DataEpisode> obj = MyApiRetrofit.getApiService().getListEpisode();
            obj.enqueue(new Callback<DataEpisode>() {
                @Override
                public void onResponse(Call<DataEpisode> call, Response<DataEpisode> response) {

                    modelo.listEpisode = response.body().getResults();
                    modelo.info = response.body().getInfo();
                    Log.v("arary", "size" + response.body().getResults().toString());


                    if(modelo.listEpisode.size()>0){
                        layout_next_back.setVisibility(View.VISIBLE);
                        if (modelo.info.getPrev() != null) {
                            btn_back.setVisibility(View.VISIBLE);
                        } else {
                            btn_back.setVisibility(View.GONE);
                        }

                        mostarDatos();
                    }

                }

                @Override
                public void onFailure(Call<DataEpisode> call, Throwable t) {
                    Log.v("error", t.getMessage() + "");
                    //  Toast.makeText(getContext(), ":X " + t.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            });


        } catch (Exception e) {
            Log.v("Error", e.getMessage());
            hideDialog();
        }
    }

    private void mostarDatos() {

        resultEpisodeAdapter = new ResultEpisodeAdapter(getApplicationContext(), modelo.listEpisode);
        resultEpisodeAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultEpisodeAdapter);
        resultEpisodeAdapter.notifyDataSetChanged();
        hideDialog();
    }

    public void next_back(View v) {
        loadswet(getString(R.string.text_validating_information));
        try {
            if (v.getId() == R.id.btn_next) {
                modelo.url = modelo.info.getNext().toString();
            } else {
                modelo.url = modelo.info.getPrev().toString();
            }

            String currentString = modelo.url;
            String[] separated = currentString.split("=");
            String page= separated[1].trim();
            Integer _page = Integer.parseInt(page);


            final Call<DataEpisode> obj = MyApiRetrofit.getApiService().getespisodeNextBack(_page);
            obj.enqueue(new Callback<DataEpisode>() {
                @Override
                public void onResponse(Call<DataEpisode> call, Response<DataEpisode> response) {
                    modelo.listResults.clear();
                    modelo.info = null;
                    modelo.listEpisode = response.body().getResults();
                    modelo.info = response.body().getInfo();
                    Log.v("arary", "size" + response.body().getResults().toString());

                    layout_next_back.setVisibility(View.VISIBLE);
                    if (modelo.info.getPrev() != null) {
                        btn_back.setVisibility(View.VISIBLE);
                    } else {
                        btn_back.setVisibility(View.GONE);
                    }


                    resultEpisodeAdapter.notifyDataSetChanged();

                    mostarDatos();
                }

                @Override
                public void onFailure(Call<DataEpisode> call, Throwable t) {
                    Log.v("error", t.getMessage() + "");
                    //  Toast.makeText(getContext(), ":X " + t.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            });


        } catch (Exception e) {
            Log.v("Error", e.getMessage());
            hideDialog();
        }
    }

    public void showAlertWithoutInternet() {


        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(getString(R.string.text_without_internet))
                .setContentText(getString(R.string.text_without_internet))
                .setConfirmText(getString(R.string.text_retry))
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        showAlertWithoutInternet();

                    }
                })

                .show();

    }


    public void loadswet(String text) {

        try {
            pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#445e6f"));
            pDialog.setTitleText(text);
            pDialog.setCancelable(false);
            pDialog.show();

        } catch (Exception e) {

        }

    }


    //oculatomos el dialog
    private void hideDialog() {
        if (pDialog != null)
            pDialog.dismiss();
    }
}