package com.hardik.chatapp.Utils;

import android.content.Context;
import android.widget.Toast;

import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Models.APIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveLastSeen {
    public static void saveInstance(Context context,long time){
        Call<APIResponse> call = APIController.getInstance().getApi().saveLastSeen(Session.getId(context),time);
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
            }
            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
            }
        });
    }
}
