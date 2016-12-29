package com.ionesmile.simplerecycler.retrofit;

import retrofit2.Retrofit;

/**
 * Created by iOnesmile on 2016/9/13 0013.
 */
public class NetManager {

    private static final String BASE_URL = "http://192.168.100.100:8080/";
    private CloudMusicHandle netHelper;

    public CloudMusicHandle getNetHelper(){
        return netHelper;
    }

    public static NetManager getInstance(){
        if (mInstance == null){
            mInstance = new NetManager();
        }
        return mInstance;
    }

    private static NetManager mInstance;

    private NetManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build();
        netHelper = retrofit.create(CloudMusicHandle.class);
    }
}
