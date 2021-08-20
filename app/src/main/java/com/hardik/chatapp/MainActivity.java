package com.hardik.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Adapters.UsersAdapter;
import com.hardik.chatapp.Models.User;
import com.hardik.chatapp.Models.UsersResponse;
import com.hardik.chatapp.Utils.SaveLastSeen;
import com.hardik.chatapp.Utils.Session;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerview;
UsersAdapter adapter;
UserThread t;
private boolean isStop = false;
LinearLayout shimmer,network;
//AN#a\0qYtMuIpRw0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        getUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchdata(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchdata(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchdata(String query) {
        if(query.isEmpty()){
            isStop = false;
            getUsers();
        }else{
            isStop = false;
            Call<UsersResponse> call = APIController.getInstance().getApi().search(Session.getId(getApplicationContext()),query);
            call.enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    UsersResponse result = response.body();
                    if(result.getStatus() == 1){
                        ArrayList<User> data = result.getData();
                        adapter = new UsersAdapter(data);
                        recyclerview.setAdapter(adapter);
                    }
                }
                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                if(Session.removeUser(getApplicationContext())){
                    SaveLastSeen.saveInstance(getApplicationContext(),System.currentTimeMillis());
                    startActivity(new Intent(MainActivity.this, SignIn.class));
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUsers() {
        if(!isStop) {
            t = new UserThread();
            t.start();
        }
    }

    private void initialize() {
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        shimmer = findViewById(R.id.main_shimmer);
        network = findViewById(R.id.main_network);
    }

    class UserThread extends Thread {
        @Override
        public void run() {
            super.run();
            Call<UsersResponse> usersCall = APIController.getInstance().getApi().getUsers(Session.getId(getApplicationContext()));
            usersCall.enqueue(new Callback<UsersResponse>() {
                @Override
                public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                    UsersResponse result = response.body();
                    if(result.getStatus() == 1){
                        ArrayList<User> data = result.getData();
                        adapter = new UsersAdapter(data);
                        recyclerview.setAdapter(adapter);
                    }
                    network.setVisibility(View.GONE);
                    shimmer.setVisibility(View.GONE);
                    getUsers();
                }
                @Override
                public void onFailure(Call<UsersResponse> call, Throwable t) {
                    shimmer.setVisibility(View.GONE);
                    network.setVisibility(View.VISIBLE);
                    getUsers();
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SaveLastSeen.saveInstance(getApplicationContext(),1);
    }

    @Override
    protected void onStop() {
        SaveLastSeen.saveInstance(getApplicationContext(),System.currentTimeMillis());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        SaveLastSeen.saveInstance(getApplicationContext(),System.currentTimeMillis());
        super.onDestroy();
    }
}