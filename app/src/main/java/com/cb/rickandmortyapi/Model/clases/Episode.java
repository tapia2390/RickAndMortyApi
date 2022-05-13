package com.cb.rickandmortyapi.Model.clases;

import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("id") private Integer id;
    @SerializedName("name") private String name;
    @SerializedName("air_date") private String air_date;
    @SerializedName("episode") private String episode;
    @SerializedName("characters") private String[] characters;
    @SerializedName("url") private String url;
    @SerializedName("created") private String created;

    public Episode() {

    }

    public Episode(Integer id, String name, String air_date, String episode, String[] characters, String url, String created) {
        this.id = id;
        this.name = name;
        this.air_date = air_date;
        this.episode = episode;
        this.characters = characters;
        this.url = url;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String[] getCharacters() {
        return characters;
    }

    public void setCharacters(String[] characters) {
        this.characters = characters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
