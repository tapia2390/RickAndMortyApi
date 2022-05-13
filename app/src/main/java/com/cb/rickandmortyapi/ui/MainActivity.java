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
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.cb.rickandmortyapi.Model.Utility.Utility;
import com.cb.rickandmortyapi.Model.clases.Modelo;
import com.cb.rickandmortyapi.Model.clases.Personaje;
import com.cb.rickandmortyapi.Model.clases.Results;
import com.cb.rickandmortyapi.Model.retrofit.MyApiRetrofit;
import com.cb.rickandmortyapi.R;
import com.cb.rickandmortyapi.SplashScreen;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Modelo modelo = Modelo.getInstance();
    SweetAlertDialog pDialog;
    Utility utility;
    ResultsAdapter resultsAdapter;
    List<Results> dataList = new ArrayList<>();
    RecyclerView recyclerView;
    SearchView search2;
    LinearLayout layout_next_back;
    Button btn_back;
    Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            Intent i = new Intent(getApplicationContext(), SplashScreen.class);
            startActivity(i);
            finish();
            return;
        }

        utility = new Utility();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        search2 = (SearchView) findViewById(R.id.search_view);
        layout_next_back = (LinearLayout) findViewById(R.id.layout_next_back);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_next = (Button) findViewById(R.id.btn_next);


        if (utility.estado(getApplicationContext())) {
            loadswet(getString(R.string.text_validating_information));
            getcharacter();
        } else {
            showAlertWithoutInternet();
            return;
        }


        search2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryString) {


                resultsAdapter.getFilter().filter(queryString);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String queryString) {

                resultsAdapter.getFilter().filter(queryString);

                return false;
            }
        });

    }

    private void getcharacter() {

        try {

            final Call<Personaje> obj = MyApiRetrofit.getApiService().getcharacter();
            obj.enqueue(new Callback<Personaje>() {
                @Override
                public void onResponse(Call<Personaje> call, Response<Personaje> response) {

                    modelo.listResults = response.body().getResults();
                    modelo.info = response.body().getInfo();
                    Log.v("arary", "size" + response.body().getResults().toString());

                    layout_next_back.setVisibility(View.VISIBLE);
                    if (modelo.info.getPrev() != null) {
                        btn_back.setVisibility(View.VISIBLE);
                    } else {
                        btn_back.setVisibility(View.GONE);
                    }

                    mostarDatos();
                }

                @Override
                public void onFailure(Call<Personaje> call, Throwable t) {
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

        resultsAdapter = new ResultsAdapter(getApplicationContext(), modelo.listResults);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(resultsAdapter);
        resultsAdapter.notifyDataSetChanged();
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


            final Call<Personaje> obj = MyApiRetrofit.getApiService().getcharacterNextBack(_page);
            obj.enqueue(new Callback<Personaje>() {
                @Override
                public void onResponse(Call<Personaje> call, Response<Personaje> response) {
                    modelo.listResults.clear();
                    modelo.info = null;
                    modelo.listResults = response.body().getResults();
                    modelo.info = response.body().getInfo();
                    Log.v("arary", "size" + response.body().getResults().toString());

                    layout_next_back.setVisibility(View.VISIBLE);
                    if (modelo.info.getPrev() != null) {
                        btn_back.setVisibility(View.VISIBLE);
                    } else {
                        btn_back.setVisibility(View.GONE);
                    }


                    resultsAdapter.notifyDataSetChanged();

                    mostarDatos();
                }

                @Override
                public void onFailure(Call<Personaje> call, Throwable t) {
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