package com.example.daltdd.Activity.network;

import com.example.daltdd.Activity.base.PathAPI;
import com.example.daltdd.Activity.base.RetrofitClient;

public class HomeApi {

    public static HomeApiService getHomeApiProvider(){
        return RetrofitClient.getClient(PathAPI.linkAPI).create(HomeApiService.class);
    }
}
