package com.cb.rickandmortyapi.Model.clases;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Personaje {
    @SerializedName("info")
    private Info info;
    @SerializedName("results")
    private ArrayList<Results> results;

    public Personaje(Info info, ArrayList<Results> results) {
        this.info = info;
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }
}
