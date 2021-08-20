package com.hardik.chatapp.API;

import com.hardik.chatapp.Models.APIResponse;
import com.hardik.chatapp.Models.MessageResponse;
import com.hardik.chatapp.Models.UsersResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APISet {
    @FormUrlEncoded
    @POST("login.php")
    Call<APIResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("get_message.php")
    Call<MessageResponse> getMessages(
            @Field("sender") int sender,
            @Field("reciever") int reciever,
            @Field("mid") int mid,
            @Field("order") String order
    );

    @FormUrlEncoded
    @POST("send_message.php")
    Call<APIResponse> sendMessage(
            @Field("sender") int sender,
            @Field("reciever") int reciever,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<APIResponse> register(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("profile_img") String profile_img
    );

    @FormUrlEncoded
    @POST("get_users.php")
    Call<UsersResponse> getUsers(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("save_last_seen.php")
    Call<APIResponse> saveLastSeen(
            @Field("id") int id,
            @Field("time") long time
    );

    @FormUrlEncoded
    @POST("search.php")
    Call<UsersResponse> search(
            @Field("id") int id,
            @Field("search") String search
    );

    @FormUrlEncoded
    @POST("recovery.php")
    Call<APIResponse> recover(
            @Field("email") String email
    );
}
