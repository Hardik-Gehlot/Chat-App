package com.hardik.chatapp.API;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hardik.chatapp.Utils.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIController {
    private static APIController apiController=null;
    private static Retrofit retrofit=null;

    APIController(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
    public static synchronized APIController getInstance(){
        if(apiController==null)
            apiController = new APIController();
        return apiController;
    }
    public APISet getApi(){
        return retrofit.create(APISet.class);
    }
}
