package com.cb.rickandmortyapi.Model.clases;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataEpisode {
    @SerializedName("info")
    private Info info;
    @SerializedName("results")
    private ArrayList<Episode> results;

    public DataEpisode(Info info, ArrayList<Episode> results) {
        this.info = info;
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public ArrayList<Episode> getResults() {
        return results;
    }

    public void setResults(ArrayList<Episode> results) {
        this.results = results;
    }
}
