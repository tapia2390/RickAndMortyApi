package com.cb.rickandmortyapi.Model.clases;

import java.util.ArrayList;

public class Modelo {
    private static final Modelo ourInstance = new Modelo();



    public static Modelo getInstance() {
        return ourInstance;
    }

    public Modelo() {
    }


    public ArrayList<Results> listResults = new ArrayList<Results>();
    public ArrayList<Episode> listEpisode = new ArrayList<Episode>();
    public Results results = new Results();
    public Info info = new Info();
    public Episode episode = new Episode();
    public String url ="";

}