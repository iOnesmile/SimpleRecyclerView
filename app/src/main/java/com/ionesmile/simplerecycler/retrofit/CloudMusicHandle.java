package com.ionesmile.simplerecycler.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by iOnesmile on 2016/9/13 0013.
 */
public interface CloudMusicHandle {

    @FormUrlEncoded
    @POST("api/register")
    Call<ResponseBody> register(@Field("username") String username, @Field("password") String password);

    @GET("api/getMusicList")
    Call<ResponseBody> getMusicList(@Query("albumId") String albumId);

}
