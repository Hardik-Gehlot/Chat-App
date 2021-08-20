package com.hardik.chatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hardik.chatapp.API.APIController;
import com.hardik.chatapp.Models.APIResponse;
import com.hardik.chatapp.Models.User;
import com.hardik.chatapp.Utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private EditText email,password;
    private Button Login;
    private TextView goToSignin,forgot;
    private ProgressBar progressBar;
    private String email1,password1;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializing();
        getLogIn();
        goToSignin();
        goToForgotPassword();
    }
    private void goToForgotPassword() {
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,ForgotPassword.class));
            }
        });
    }
    private void goToSignin() {
        goToSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignIn.class));
                finish();
            }
        });
    }
    private void getLogIn() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                email1 =email.getText().toString().trim();
                password1 = password.getText().toString().trim();
                if(email1 != "" && password1 != ""){
                    Call<APIResponse> call = APIController.getInstance().getApi().login(email1,password1);
                    call.enqueue(new Callback<APIResponse>() {
                        @Override
                        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            APIResponse data = response.body();
                            if(data.getStatus() == 1){
                                user = data.getData();
                                if(Session.setUser(getApplicationContext(),user)){
                                    Intent intent = new Intent(com.hardik.chatapp.Login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<APIResponse> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void initializing() {
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        Login = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.login_progress);
        goToSignin = findViewById(R.id.go_to_signin);
        forgot = findViewById(R.id.go_to_forgot_password);
    }
}