package com.example.daltdd.Activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;

public class ListVideoInfo {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("results")
    @Expose
    private List<VideoInfo> results = null;



    public List<VideoInfo> getResults() {
        return results;
    }

    public void setResults(List<VideoInfo> results) {
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
