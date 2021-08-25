package com.hardik.chatapp.Settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Models.APIResponse;
import com.hardik.chatapp.R;
import com.hardik.chatapp.SettingActivity;
import com.hardik.chatapp.Utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
EditText oldPass,newPass,newPassCon;
Button change;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initialize();
        changePassword();
    }

    private void changePassword() {
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old = oldPass.getText().toString().trim();
                String new1 = newPass.getText().toString().trim();
                String new2 = newPassCon.getText().toString().trim();
                if(old.isEmpty() || new1.isEmpty() || new2.isEmpty()){
                    Toast.makeText(ChangePassword.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
                    if(new1.contentEquals(new2)){
                        Call<APIResponse> call = APIController.getInstance().getApi().changePassword(Session.getId(getApplicationContext()),old,new1);
                        call.enqueue(new Callback<APIResponse>() {
                            @Override
                            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.body().getStatus() == 1){
                                    startActivity(new Intent(ChangePassword.this, SettingActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<APIResponse> call, Throwable t) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ChangePassword.this, "Network Problem", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        newPassCon.setError("Password not match");
                    }
                }
            }
        });
    }

    private void initialize() {
        oldPass = findViewById(R.id.old_password);
        newPass = findViewById(R.id.new_password);
        newPassCon =findViewById(R.id.new_confirm_password);
        change = findViewById(R.id.change_password_button);
        progressBar = findViewById(R.id.change_password_progress);
    }
}